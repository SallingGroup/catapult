package dk.theknights.catapult.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openshift.internal.restclient.model.BuildConfig;
import com.openshift.internal.restclient.model.List;
import com.openshift.internal.restclient.model.Secret;
import com.openshift.internal.restclient.model.ServiceAccount;
import com.openshift.internal.restclient.model.project.OpenshiftProjectRequest;
import com.openshift.internal.restclient.model.template.Template;
import com.openshift.restclient.ClientBuilder;
import com.openshift.restclient.IClient;
import com.openshift.restclient.ResourceKind;
import com.openshift.restclient.capability.CapabilityVisitor;
import com.openshift.restclient.capability.resources.IBuildTriggerable;
import com.openshift.restclient.capability.server.ITemplateProcessing;
import com.openshift.restclient.model.IBuild;
import com.openshift.restclient.model.IProject;
import com.openshift.restclient.model.IResource;
import com.openshift.restclient.model.authorization.IPolicyBinding;
import com.openshift.restclient.model.secret.ISecret;
import com.openshift.restclient.model.template.ITemplate;
import dk.theknights.catapult.CatapultException;
import dk.theknights.catapult.config.CatapultConfig;
import dk.theknights.catapult.config.secret.CatapultSecret;
import dk.theknights.catapult.config.secret.SecretBuilder;
import dk.theknights.catapult.model.UserLogin;
import dk.theknights.catapult.model.openshift.PolicyBinding;
import dk.theknights.catapult.model.openshift.RoleBinding2;
import dk.theknights.catapult.openshift.PolicyBindingTemplate;
import dk.theknights.catapult.utility.EnvironmentUtility;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jboss.dmr.ModelNode;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class wraps all interaction with OpenShift.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class OpenShiftService {

	public static final String SECRET_OPENSHIFT_PATH = "SECRET_OPENSHIFT_PATH";
	public static final String OPENSHIFT_MASTER_ENDPOINT = "OPENSHIFT_MASTER_ENDPOINT";

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	protected final String openshiftSecretPath = EnvironmentUtility.getEnv(SECRET_OPENSHIFT_PATH, "/etc/openshift");
	protected final String openShiftEndpoint = EnvironmentUtility.getEnv(OPENSHIFT_MASTER_ENDPOINT, "https://kubernetes.default");

	private CatapultConfig configuration;

	/**
	 * Get all projects that exist on OpenShift.
	 *
	 * @return Collection of all projects
	 * @throws IOException
	 */
	public Collection<IResource> getAllProjects() throws IOException {
		IClient client = getOpenShiftClient();

		IResource resource = client.execute("GET", "Project", null, null, null, null);
		List projectListResource = (List) resource;

		return projectListResource.getItems();
	}

	/**
	 * Get project with projectName if it exists. If projectName does not exist null is returned.
	 *
	 * @param projectName Name of project to find
	 * @return OpenShift project
	 * @throws IOException
	 */
	public IProject getProject(final String projectName) throws IOException {
		Iterator<IResource> projectIt = getAllProjects().iterator();
		while (projectIt.hasNext()) {
			IProject nextProject = (IProject) projectIt.next();
			if (nextProject.getName().equals(projectName)) {
				return nextProject;
			}
		}
		return null;
	}

	/**
	 * Create project in OpenShift. Project will be created and default policybinding modified.
	 *
	 * @param namespace Name of project to create in OpenShift
	 * @return Newly created project
	 * @throws IOException
	 */
	public IProject createProject(final String namespace) throws IOException {
		IClient client = getOpenShiftClient();
		OpenshiftProjectRequest resourceRequest = client.getResourceFactory().stub(ResourceKind.PROJECT_REQUEST, namespace);

		IProject project = (IProject) client.create(resourceRequest);
		try {
			IPolicyBinding policyBinding = getPolicyBinding(":default", project);

			String json = policyBinding.toJson();

			ObjectMapper objectMapper = new ObjectMapper();
			PolicyBinding policyBinding1 = objectMapper.readValue(json, PolicyBinding.class);

			PolicyBindingTemplate policybindingTemplate = new PolicyBindingTemplate();
			policybindingTemplate.setCatapultConfig(configuration);
			policybindingTemplate.setNamespace(project.getNamespace());
			policybindingTemplate.loadFromLocal();
			RoleBinding2 role = policybindingTemplate.toRoleBinding();

			policyBinding1.getRoleBindings().add(role);

			ModelNode node = ModelNode.fromJSONString(objectMapper.writeValueAsString(policyBinding1));
			Template template = new Template(node, client, null);
			template.setNamespace(project.getNamespace());

			client.update(template);
		} catch (RuntimeException e) {
			logger.error(e.getLocalizedMessage(), e);
		}

		return project;
	}

	/**
	 * Delete specified project from OpenShift.
	 *
	 * @param project Project to delete
	 * @throws IOException
	 */
	public void deleteProject(final IProject project) throws IOException {
		String namespace = project.getNamespace();
		String projectGroups = configuration.getCatapultProperties().getProjectReservedGroups();
		String[] groups = projectGroups.split(",");
		for (String group: groups) {
			if (namespace.equalsIgnoreCase(group)) {
				logger.warn("I am NOT deleting: " + namespace);
				return;
			}
		}
		IClient client = getOpenShiftClient();
		client.delete(project);
	}

	/**
	 * Get policybinding with policybindingName in a namespace from OpenShift.
	 *
	 * @param policyBindingName name of policybinding to find
	 * @param project openshift namespace
	 * @return policybinding with specifiedname from namespace if it exists
	 * @throws IOException
	 */
	public IPolicyBinding getPolicyBinding(final String policyBindingName, final IProject project) throws IOException {
		IClient client = getOpenShiftClient();
		return client.get(ResourceKind.POLICY_BINDING, policyBindingName, project.getNamespace());
	}

	/**
	 * Update OpenShift project.
	 *
	 * @param json template with information that needs to be updated
	 * @param project OpenShift project that need updating
	 * @throws IOException
	 */
	public void update(final String json, final IProject project) throws IOException {
		IClient client = getOpenShiftClient();
		ModelNode node = ModelNode.fromJSONString(json);
		Template template = new Template(node, client, null);
		template.setNamespace(project.getNamespace());
		client.update(template);
	}

	/**
	 * Add a secret to a serviceaccount specified by username.
	 *
	 * @param secret Secret to add to serviceaccount
	 * @param username Name of serviceaccount to modify
	 * @return Modified serviceaccount
	 * @throws CatapultException
	 * @throws IOException
	 */
	public ServiceAccount addSecretToServiceAccount(final Secret secret, final String username) throws CatapultException, IOException {
		IClient client = getOpenShiftClient();
		try {
			ServiceAccount account = client.execute("GET", "ServiceAccount", secret.getNamespace(), username, null, null);
			account.addSecret(secret.getName());
			client.update(account);
			return account;
		} catch (RuntimeException e) {
			try {
				logger.warn("Unable to add secret to service account, retrying ...");
				ServiceAccount account = client.execute("GET", "ServiceAccount", secret.getNamespace(), username, null, null);
				account.addSecret(secret.getName());
				client.update(account);
				return account;
			} catch (RuntimeException  e1) {
				logger.error("Unable to add secret to service account.", e1);
				throw new CatapultException(e1);
			}
		}
	}

	/**
	 * Get all configmaps from OpenShift namespace
	 *
	 * @param namespace Target OpenShift namespace
	 * @return Collection of configmaps
	 * @throws IOException
	 */
	public Collection<IResource> getConfigMaps(final String namespace) throws IOException {
		IClient client = getOpenShiftClient();
		return client.execute("GET", "configmaps", namespace, null, null, null);
	}

	/**
	 * Get all secrets from OpenShift namespace
	 *
	 * @param namespace Target OpenShift namespace
	 * @return Collection of all secrets in target OpenShift namespace
	 * @throws IOException
	 */
	public Collection<IResource> getSecrets(final String namespace) throws IOException {
		IClient client = getOpenShiftClient();
		return client.execute("GET", "secrets", namespace, null, null, null);
	}

	/**
	 * Create a secret in OpenShift namespace with specified name. Secret is built from filesystem.
	 *
	 * @param secretPath path to secret to build
	 * @param secretName secret name
	 * @param namespace OpenShift project that needs new secret
	 * @return Newly created secret
	 * @throws IOException
	 */
	public CatapultSecret createSecret(final String secretPath, final String secretName, final String namespace) throws IOException {
		IClient client = getOpenShiftClient();
		SecretBuilder secretBuilder = getSecretBuilder(client);
		CatapultSecret secret = secretBuilder.build(secretPath, secretName, namespace);
		client.create(secret);
		return secret;
	}

	/**
	 * Create new secret in OpenShift target project.
	 *
	 * @param name Secret name
	 * @param project Target OpenShift project
	 * @param secret Secret to create
	 * @return Newly created secret
	 * @throws IOException
	 */
	public Secret createSecret(final String name, final IProject project, final IResource secret) throws IOException {
		IClient client = getOpenShiftClient();
		ISecret secretRequest = client.getResourceFactory().stub(ResourceKind.SECRET, name, project.getNamespace());
		secretRequest.setType("Opaque");

		JSONObject json = new JSONObject(secret.toJson());
		JSONObject data = json.getJSONObject("data");
		for (Iterator<String> keyIterator = data.keys(); keyIterator.hasNext();) {
			String key = keyIterator.next();
			String value = new String(Base64.getDecoder().decode(data.getString(key)), "UTF-8");
			secretRequest.addData(key, value.getBytes("UTF-8"));
		}

		return (Secret) client.create(secretRequest);
	}

	/**
	 * Create docker secret in target OpenShift namespace.
	 *
	 * @param name Secret name
	 * @param secretFilePath File path from container
	 * @param project Target OpenShift project
	 * @return Newly created secret
	 * @throws IOException
	 */
	public Secret createDockerSecret(final String name, final String secretFilePath, final IProject project) throws IOException {
		return createDockerSecret(name, new File(secretFilePath + "/.dockercfg"), project);
	}

	/**
	 * Add secret as imagepuller to OpenShift service account
	 * @param secret Secret to add as imagepuller
	 * @param username Name of service account to modify
	 * @param project Target OpenShift namespace
	 * @return Modified serviceaccount
	 * @throws IOException
	 */
	public ServiceAccount addImagePullSecretToServiceAccount(final Secret secret, final String username, final IProject project) throws IOException {
		IClient client = getOpenShiftClient();
		ServiceAccount account = client.execute("GET", "ServiceAccount", project.getNamespace(), username, null, null);
		account.addImagePullSecret(secret.getName());
		return client.update(account);
	}

	/**
	 * Trigger OpenShift build
	 *
	 * @param repositoryUrl Repository that triggered build request
	 * @param commitId Commit hash that triggered build
	 * @param project Target OpenShift namespace
	 * @return Build that was triggered
	 * @throws IOException
	 */
	public IBuild triggerBuild(final String repositoryUrl, final String commitId, final IProject project) throws IOException {
		IClient client = getOpenShiftClient();
		IResource resource = client.execute("GET", "BuildConfig", project.getName(), null, null, null);

		Collection<IResource> buildConfigs = ((List) resource).getItems();
		Iterator<IResource> buildConfigsIt = buildConfigs.iterator();
		while (buildConfigsIt.hasNext()) {
			IResource buildConfigResource = buildConfigsIt.next();
			BuildConfig buildconfig = (BuildConfig) buildConfigResource;

			String webhookSource = extractRepositoryLocation(repositoryUrl);
			if (buildconfig != null && buildconfig.getBuildSource() != null) {
				String projectSource = extractRepositoryLocation(buildconfig.getBuildSource().getURI());
				String buildtrigger = buildconfig.getAnnotation("buildtrigger");

				// only trigger build from webhook source
				if (webhookSource.equals(projectSource)
						&& buildtrigger != null
						&& buildtrigger.equalsIgnoreCase("catapult")) {
					CapabilityVisitor<IBuildTriggerable, IBuild> visitor = new CapabilityVisitor<IBuildTriggerable, IBuild>() {
						@Override
						public IBuild visit(final IBuildTriggerable capability) {
							return capability.trigger(commitId);
						}
					};
					return buildconfig.accept(visitor, null);
				}
			} else {
				logger.warn("BuildConfig is null !! Why is that?");
			}
		}
		return null;
	}

	/**
	 * Set catapult configuration, since is does not want to be autowired in this class.
	 *
	 * @param configuration Catapult configuration that this service is to use
	 */
	public void setCatapultConfig(final CatapultConfig configuration) {
		this.configuration = configuration;
	}

	/**
	 * Update OpenShift project requester.
	 *
	 * @param project Target OpenShift project
	 * @param requester Prettified name of user that created project
	 * @throws IOException
	 */
	public void updateProjectRequester(final IProject project, final String requester) throws IOException {
		IClient client = getOpenShiftClient();
		IResource resource = client.getResourceFactory().stub(ResourceKind.PROJECT, project.getNamespace());
		resource.setAnnotation("openshift.io/requester", requester);
		resource.setAnnotation("openshift.io/display-name", project.getDisplayName());
		client.execute("PUT", "namespaces", null, project.getName(), null, resource);
	}

	/**
	 * Update OpenShift namespace displayname.
	 *
	 * @param project Target OpenShift project
	 * @param projectName New display name
	 * @throws IOException
	 */
	public void updateProjectDisplayName(final IProject project, final String projectName) throws IOException {
		IClient client = getOpenShiftClient();
		IResource resource = client.getResourceFactory().stub(ResourceKind.PROJECT, project.getNamespace());
		resource.setAnnotation("openshift.io/requester", project.getAnnotation("openshift.io/requester"));
		resource.setAnnotation("openshift.io/display-name", projectName);
		client.execute("PUT", "namespaces", null, project.getName(), null, resource);
	}

	/**
	 * Process Catapult template.
	 *
	 * @param jsonTemplate json template to process
	 * @param project Target OpenShift project
	 * @param parameters Template parameters that will be used when processing template
	 * @return
	 * @throws IOException
	 */
	@SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
	public Collection<IResource> processTemplate(final String jsonTemplate, final IProject project, final Collection<Pair<String, String>> parameters) throws IOException {
		IClient openshiftClient = getOpenShiftClient();

		ModelNode node = ModelNode.fromJSONString(jsonTemplate);
		Template template = new Template(node, openshiftClient, null);
		template.setNamespace(null);

		final Collection<IResource> results = new ArrayList<IResource>();

		openshiftClient.accept(new CapabilityVisitor<ITemplateProcessing, ITemplate>() {
			@Override
			public ITemplate visit(final ITemplateProcessing capability) {

				if (parameters != null && !parameters.isEmpty()) {
					for (Pair<String, String> p : parameters) {
						template.updateParameter(p.getKey(), p.getValue());
					}
				}
				ITemplate processedTemplate = capability.process(template, project.getName());

				processedTemplate.toJson();
				for (IResource resource : processedTemplate.getObjects()) {
					results.add(openshiftClient.create(resource, project.getName()));
					resource.toJson();
				}

				return  processedTemplate;
			}
		}, null);

		return results;
	}

	private Secret createDockerSecret(final String name, final File secretFile, final IProject project) throws IOException {
		IClient client = getOpenShiftClient();
		ISecret secretRequest = client.getResourceFactory().stub(ResourceKind.SECRET, name, project.getNamespace());
		secretRequest.setType("kubernetes.io/dockercfg");
		try (InputStream secret = new FileInputStream(secretFile)) {
			secretRequest.addData(".dockercfg", secret);
			return (Secret) client.create(secretRequest);
		}
	}

	protected IClient getOpenShiftClient() throws IOException {
		UserLogin login = getLogin(openshiftSecretPath);
		IClient client = new ClientBuilder(openShiftEndpoint)
				.withUserName(login.getUsername())
				.withPassword(login.getPassword())
				.build();

		return client;
	}

	protected SecretBuilder getSecretBuilder(final IClient client) {
		return new SecretBuilder(client);
	}

	@SuppressFBWarnings("DM_DEFAULT_ENCODING")
	protected UserLogin getLogin(final String path) throws IOException {
		String usernameFile = path + "/username";
		String username = IOUtils.toString(new FileReader(usernameFile));

		String passwordFile = path + "/password";
		String password = IOUtils.toString(new FileReader(passwordFile));

		return new UserLogin(username, password);
	}

	/**
	 * This method will extract the repository location from the repositoryUrl, by removing the following:
	 * 'https://'
	 * 'http://'
	 * '@git'
	 * '.git'
	 * and then replace all ':' with '/' so url from webhook and url from OpenShift build config can be compared to detect
	 * if the repository that triggered the catapult has a build config in OpenShift.
	 *
	 * @param repositoryUrl url that needs manipulating
	 * @return url that does not have https://, git@, .git or : in it
	 */
	private String extractRepositoryLocation(final String repositoryUrl) {
		String repositoryLocation = null;
		if (repositoryUrl.startsWith("https://")) {
			repositoryLocation = repositoryUrl.substring("https://".length());
		} else if (repositoryUrl.startsWith("https")) {
			repositoryLocation = repositoryUrl.substring("http://".length());
		} else if (repositoryUrl.startsWith("git@")) {
			repositoryLocation = repositoryUrl.substring("git@".length());
			repositoryLocation = repositoryLocation.replaceAll(":", "/");
		}

		if (repositoryLocation != null) {
			if (repositoryLocation.endsWith("/")) {
				repositoryLocation = repositoryLocation.substring(0, repositoryLocation.length() - 1);
			} else if (repositoryLocation.endsWith(".git")) {
				repositoryLocation = repositoryLocation.substring(0, repositoryLocation.length() - ".git".length());
			}
		}
		return repositoryLocation;
	}

}
