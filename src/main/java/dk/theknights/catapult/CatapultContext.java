package dk.theknights.catapult;

import com.openshift.internal.restclient.model.Secret;
import com.openshift.internal.restclient.model.ServiceAccount;
import com.openshift.restclient.model.IBuild;
import com.openshift.restclient.model.IProject;
import dk.theknights.catapult.config.CatapultConfig;
import dk.theknights.catapult.config.secret.CatapultSecret;
import dk.theknights.catapult.model.webhook.AbstractWebHook;
import dk.theknights.catapult.strategies.adapter.CatapultAdapter;
import dk.theknights.catapult.strategies.adapter.CatapultAdapterFactory;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import dk.theknights.catapult.strategies.state.InvalidCatapultStateException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 *This class is used to store the current state of tasks that run in the Catapult.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class CatapultContext {

	private String uuid = UUID.randomUUID().toString();
	private CatapultStateEnum state = CatapultStateEnum.INITIAL;
	private AbstractWebHook webhook;
	private CatapultConfig configuration;
	private CatapultTemplate catapultTemplate;
	private IProject openshiftProject;
	boolean catapultTemplateChanged = false;
	private List<Secret> secrets = new ArrayList<>();
	private List<Secret> configmaps = new ArrayList<>();
	private List<IBuild> buildsTriggered = new ArrayList<>();
	private List<Secret> secretsAdded = new ArrayList<>();
	private List<ServiceAccount> modifiedServiceAccounts = new ArrayList<>();
	private CatapultAdapterFactory adapterFactory = new CatapultAdapterFactory();
	private boolean newProject = false;

	/**
	 * Get Catapult Context id.
	 *
	 * @return Unique id for this context
	 */
	public String getId() {
		return uuid;
	}

	/**
	 * Identify which repository type that is in use.
	 *
	 * @return RepositoryType enum
	 */
	public RepositoryType getRepositoryType() {
		if (webhook != null) {
			if (webhook.getRepositoryUrl().toLowerCase(Locale.ROOT).indexOf("bitbucket") > -1) {
				return RepositoryType.BITBUCKET;
			}
			if (webhook.getRepositoryUrl().toLowerCase(Locale.ROOT).indexOf("gitlab") > -1) {
				return RepositoryType.GITLAB;
			}
			if (webhook.getRepositoryUrl().toLowerCase(Locale.ROOT).indexOf("github") > -1) {
				return RepositoryType.GITHUB;
			}
		}
		return RepositoryType.UNKNOWN;
	}

	/**
	 * Sets the current webhook.
	 *
	 * @param webhook Webhook that triggered the Catapult
	 */
	public void setWebhook(final AbstractWebHook webhook) {
		this.webhook = webhook;
	}

	/**
	 * Get current webhook
	 *
	 * @return Webhook that has triggered the Catapult
	 */
	public AbstractWebHook getWebhook() {
		return webhook;
	}

	/**
	 * Get Catapult configuration
	 *
	 * @return CatapultConfig the current catatpult configuration
	 */
	public CatapultConfig getCatapultConfig() {
		return configuration;
	}

	/**
	 * Set current Catapult configuration.
	 *
	 * @param configuration new Catapult configuration
	 */
	public void setCatapultConfig(final CatapultConfig configuration) {
		this.configuration = configuration;
	}

	/**
	 * Get Catatpult template
	 *
	 * @return CatapultTemplate from source repository
	 */
	public CatapultTemplate getCatapultTemplate() {
		return catapultTemplate;
	}

	/**
	 * Set Catapult template
	 *
	 * @param catapultTemplate New Catapult template from source repository
	 */
	public void setCatapultTemplate(final CatapultTemplate catapultTemplate) {
		this.catapultTemplate = catapultTemplate;
	}

	/**
	 * Set current Catapult state
	 *
	 * @param state New Catapult state
	 */
	public void setCatapultState(final CatapultStateEnum state) {
		this.state = state;
	}

	/**
	 * Get current Catapult state
	 *
	 * @return Enum CatapultState
	 */
	public CatapultStateEnum getCatapultState() {
		return state;
	}

	/**
	 * Get OpenShift project
	 *
	 * @return OpenShift project
	 */
	public IProject getOpenShiftProject() {
		return openshiftProject;
	}

	/**
	 * Set new OpenShiftProject
	 *
	 * @param openshiftProject New OpenShift project
	 */
	public void setOpenShiftProject(final IProject openshiftProject) {
		this.openshiftProject = openshiftProject;
	}

	/**
	 * Has Catapult template changed.
	 *
	 * @return true if template has changed otherwise false
	 */
	public boolean isCatapultTemplateChanged() {
		return catapultTemplateChanged;
	}

	/**
	 * Set Catatpult template changed.
	 *
	 * @param catapultTemplateChanged Sets the catapultTemplateChanged value indicating if the template has changed
	 */
	public void setCatapultTemplateChanged(final boolean catapultTemplateChanged) {
		this.catapultTemplateChanged = catapultTemplateChanged;
	}

	/**
	 * Reset the secrets list.
	 */
	public void clearSecrets() {
		this.secrets = new ArrayList<>();
	}

	/**
	 * Add new secret to list of secrets
	 *
	 * @param catapultSecret New secret to add
	 */
	public void addSecret(final Secret catapultSecret) {
		secrets.add(catapultSecret);
	}

	/**
	 * Get list of secrets
	 *
	 * @return List of secrets
	 */
	public List<Secret> getSecrets() {
		return secrets;
	}

	/**
	 * Add ConfigMap to list of ConfigMaps
	 *
	 * @param catapultConfigMap New ConfigMap to add
	 */
	public void addConfigMap(final CatapultSecret catapultConfigMap) {
		configmaps.add(catapultConfigMap);
	}

	/**
	 * Get list of ConfigMaps
	 *
	 * @return List of ConfigMaps
	 */
	public List<Secret> getConfigMaps() {
		return configmaps;
	}

	/**
	 * Get Catapult adapter from state within the current catapult context.
	 *
	 * @param context current Catapult context
	 * @return CatapultAdapter for current state
	 * @throws InvalidCatapultStateException
	 */
	public CatapultAdapter getCatapultAdapter(final CatapultContext context) throws InvalidCatapultStateException {
		return getCatapultAdapterFactory().create(context);
	}

	/**
	 * Set current Catapult adapter factory
	 *
	 * @param adapterFactory New Catapult adapter factory
	 */
	public void setCatapultAdapterFactory(final CatapultAdapterFactory adapterFactory) {
		this.adapterFactory = adapterFactory;
	}

	/**
	 * Set list of builds that was triggered.
	 *
	 * @param buildsTriggered New list of builds that was triggered
	 */
	public void setBuildsTriggered(final List<IBuild> buildsTriggered) {
		this.buildsTriggered = buildsTriggered;
	}

	/**
	 * Get List of builds that was triggered.
	 *
	 * @return List with triggered builds
	 */
	public List<IBuild> getBuildsTriggered() {
		return buildsTriggered;
	}

	/**
	 * Set list with secrets that was added.
	 *
	 * @param secretsAdded New list with secrets
	 */
	public void setSecretsAdded(final List<Secret> secretsAdded) {
		this.secretsAdded = secretsAdded;
	}

	/**
	 * Get list of secrets that was added
	 *
	 * @return List with added secrets
	 */
	public List<Secret> getSecretsAdded() {
		return secretsAdded;
	}

	/**
	 * Set new list with service accounts that were modified.
	 *
	 * @param modifiedServiceAccounts Set list with service accounts that where modified.
	 */
	public void setModifiedServiceAccounts(final List<ServiceAccount> modifiedServiceAccounts) {
		this.modifiedServiceAccounts = modifiedServiceAccounts;
	}

	/**
	 * Get list of serviceaccounts that was modified
	 *
	 * @return List with serviceaccounts
	 */
	public List<ServiceAccount> getModifiedServiceAccounts() {
		return modifiedServiceAccounts;
	}

	/**
	 * Mark if project is a new project. If true the context holds a newly created project otherwise the project that this context has is an existing project.
	 *
	 * @param newProject true if project is new and false if not
	 */
	public void setNewProject(final boolean newProject) {
		this.newProject = newProject;
	}

	/**
	 * Is the project in this context a new project or not.
	 *
	 * @return true if project is new and false if it is not
	 */
	public boolean isNewProject() {
		return newProject;
	}

	/**
	 * Get current Catapult adapter factory.
	 *
	 * @return Current Catapult adapter factory
	 */
	public CatapultAdapterFactory getCatapultAdapterFactory() {
		return adapterFactory;
	}

}
