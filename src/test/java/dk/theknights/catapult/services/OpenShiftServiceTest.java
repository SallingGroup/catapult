package dk.theknights.catapult.services;

import com.openshift.internal.restclient.model.Secret;
import com.openshift.internal.restclient.model.ServiceAccount;
import com.openshift.internal.restclient.model.template.Template;
import com.openshift.restclient.IClient;
import com.openshift.restclient.ResourceKind;
import com.openshift.restclient.model.IProject;
import com.openshift.restclient.model.IResource;
import com.openshift.restclient.model.authorization.IPolicyBinding;
import dk.theknights.catapult.CatapultException;
import dk.theknights.catapult.config.secret.SecretBuilder;
import dk.theknights.catapult.model.UserLogin;
import dk.theknights.catapult.strategies.StubbedOpenShiftClient;
import dk.theknights.catapult.strategies.StubbedOpenShiftProject;
import dk.theknights.catapult.strategies.StubbedPolicyBinding;
import dk.theknights.catapult.strategies.StubbedResourceFactory;
import dk.theknights.catapult.strategies.StubbedSecret;
import dk.theknights.catapult.strategies.StubbedServiceAccount;
import org.hamcrest.collection.IsEmptyCollection;
import org.jboss.dmr.ModelNode;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/12/18.
 */
public class OpenShiftServiceTest {

	@Test
	public void testGetAllProjectsEmptyList() throws IOException {
		// Arrange
		OpenShiftService openshiftService = new OpenShiftService();
		UserLogin userlogin = new UserLogin("dummypassword", "dummyuser");

		StubbedOpenShiftClient openshiftClient = Mockito.mock(StubbedOpenShiftClient.class);
		OpenShiftService spyService = Mockito.spy(openshiftService);
		doReturn(userlogin).when(spyService).getLogin(ArgumentMatchers.anyString());
		doReturn(openshiftClient).when(spyService).getOpenShiftClient();
		doReturn(new com.openshift.internal.restclient.model.List(new ModelNode(), null, null)).when(openshiftClient).execute(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any());

		// Act
		Collection<IResource> allprojects = spyService.getAllProjects();

		// Assert
		assertThat(allprojects, IsEmptyCollection.empty());
	}

	@Test
	public void testGetAllProjectsNotEmptyList() throws IOException {
		// Arrange
		OpenShiftService openshiftService = new OpenShiftService();
		UserLogin userlogin = new UserLogin("dummypassword", "dummyuser");

		StubbedOpenShiftClient openshiftClient = Mockito.mock(StubbedOpenShiftClient.class);
		OpenShiftService spyService = Mockito.spy(openshiftService);
		doReturn(userlogin).when(spyService).getLogin(ArgumentMatchers.anyString());
		doReturn(openshiftClient).when(spyService).getOpenShiftClient();

		List<StubbedOpenShiftProject> stubedProjectList = new ArrayList<>();
		stubedProjectList.add(new StubbedOpenShiftProject());
		stubedProjectList.add(new StubbedOpenShiftProject());
		stubedProjectList.add(new StubbedOpenShiftProject());
		doReturn(stubedProjectList).when(spyService).getAllProjects();

		// Act
		Collection<IResource> allprojects = spyService.getAllProjects();

		// Assert
		assertEquals("project list must have 3 projects.", 3, allprojects.size());
	}

	@Test
	public void testGetProjectThatExists() throws IOException {
		// Arrange
		String projectName = "demoTestProject";
		OpenShiftService openshiftService = new OpenShiftService();
		UserLogin userlogin = new UserLogin("dummypassword", "dummyuser");

		StubbedOpenShiftClient openshiftClient = Mockito.mock(StubbedOpenShiftClient.class);
		OpenShiftService spyService = Mockito.spy(openshiftService);
		doReturn(userlogin).when(spyService).getLogin(ArgumentMatchers.anyString());
		doReturn(openshiftClient).when(spyService).getOpenShiftClient();

		List<StubbedOpenShiftProject> stubedProjectList = new ArrayList<>();
		stubedProjectList.add(new StubbedOpenShiftProject("crapProject"));
		stubedProjectList.add(new StubbedOpenShiftProject("demoTestProject"));
		stubedProjectList.add(new StubbedOpenShiftProject("projectSkod"));
		doReturn(stubedProjectList).when(spyService).getAllProjects();

		// Act
		IProject project = spyService.getProject(projectName);

		// Assert
		assertNotNull("project must exist!", project);
		assertEquals("project must have name <" + projectName + ">", project.getName(), projectName);
	}

	@Test
	public void testGetProjectThatDoesNotExists() throws IOException {
		// Arrange
		String projectName = "demoTestProject";
		OpenShiftService openshiftService = new OpenShiftService();
		UserLogin userlogin = new UserLogin("dummypassword", "dummyuser");

		StubbedOpenShiftClient openshiftClient = Mockito.mock(StubbedOpenShiftClient.class);
		OpenShiftService spyService = Mockito.spy(openshiftService);
		List<StubbedOpenShiftProject> stubedProjectList = new ArrayList<>();
		stubedProjectList.add(new StubbedOpenShiftProject("crapProject"));
		stubedProjectList.add(new StubbedOpenShiftProject("demoProjectTest"));
		stubedProjectList.add(new StubbedOpenShiftProject("projectName"));
		doReturn(stubedProjectList).when(spyService).getAllProjects();

		doReturn(userlogin).when(spyService).getLogin(ArgumentMatchers.anyString());
		doReturn(openshiftClient).when(spyService).getOpenShiftClient();

		// Act
		IProject project = spyService.getProject(projectName);

		// Assert
		assertNull("project should not exist!", project);
	}

	/**
	 * Should we test createProject with an invalid name ??
	 * @throws IOException
	 */
	@Test
	public void testCreateProject() throws IOException {
		// Arrange
		String projectName = "demoTestProject";

		StubbedOpenShiftClient openshiftClient = Mockito.mock(StubbedOpenShiftClient.class);
		OpenShiftService openshiftService = new OpenShiftService();
		OpenShiftService spyService = Mockito.spy(openshiftService);
		doReturn(openshiftClient).when(spyService).getOpenShiftClient();
		doReturn(new StubbedOpenShiftProject(projectName)).when(spyService).createProject(ArgumentMatchers.anyString());

		// Act
		IProject project = spyService.createProject(projectName);

		// Assert
		assertNotNull("project must not be null.", project);
	}

	@Test
	public void testGetPolicyBinding() throws IOException {
		// Arrange
		String policyBindingName = "blargh";
		IProject project = new StubbedOpenShiftProject("stubedprojectname");
		OpenShiftService openshiftService = new OpenShiftService();

		OpenShiftService spyService = Mockito.spy(openshiftService);
		StubbedOpenShiftClient openshiftClient = Mockito.mock(StubbedOpenShiftClient.class);
		doReturn(openshiftClient).when(spyService).getOpenShiftClient();
		doReturn(new StubbedPolicyBinding()).when(openshiftClient).get(ArgumentMatchers.matches(ResourceKind.POLICY_BINDING), ArgumentMatchers.anyString(), ArgumentMatchers.anyString());

		// Act
		IPolicyBinding policybinding = spyService.getPolicyBinding(policyBindingName, project);

		// Assert
		assertNotNull("policybinding can not be null.", policybinding);
	}

	@Test
	public void testUpdate() throws IOException {
		// Arrange
		String json = "{\"demoTestProject\":\"testtest\"}";
		StubbedOpenShiftProject project = new StubbedOpenShiftProject();
		OpenShiftService openshiftService = new OpenShiftService();
		StubbedOpenShiftClient openshiftClient = Mockito.mock(StubbedOpenShiftClient.class);
		OpenShiftService spyService = Mockito.spy(openshiftService);
		doReturn(openshiftClient).when(spyService).getOpenShiftClient();

		// Act
		spyService.update(json, project);

		// Assert
		verify(openshiftClient).update(ArgumentMatchers.any(Template.class));
	}

	@Test
	public void testCreateSecret() throws IOException {
		// Arrange
		String secretName = "demoTestSecret";
		Secret stubedsecret = new StubbedSecret(new ModelNode(), null);
		IProject project = new StubbedOpenShiftProject();

		OpenShiftService openshiftService = new OpenShiftService();
		StubbedOpenShiftClient openshiftClient = Mockito.mock(StubbedOpenShiftClient.class);
		OpenShiftService spyService = Mockito.spy(openshiftService);
		doReturn(openshiftClient).when(spyService).getOpenShiftClient();
		doReturn(stubedsecret).when(openshiftClient).create(ArgumentMatchers.any(StubbedSecret.class));

		StubbedResourceFactory stubedResourceFactory = Mockito.mock(StubbedResourceFactory.class);
		doReturn(stubedResourceFactory).when(openshiftClient).getResourceFactory();
		doReturn(new StubbedSecret(new ModelNode(), null)).when(stubedResourceFactory).stub(ArgumentMatchers.matches(ResourceKind.SECRET), ArgumentMatchers.matches(secretName), ArgumentMatchers.matches(project.getNamespace()));

		// Act
		Secret secret = spyService.createSecret(secretName, project, stubedsecret);

		// Assert
		assertNotNull("secret can not be null", secret);
	}

	@Test
	public void testAddSecretToServiceAccount() throws IOException {
		// Arrange
		String username = "demoTestUsername";
		OpenShiftService openshiftService = new OpenShiftService();
		StubbedSecret secret = new StubbedSecret(new ModelNode(), null);
		StubbedOpenShiftProject project = new StubbedOpenShiftProject();
		StubbedOpenShiftClient openshiftClient = Mockito.mock(StubbedOpenShiftClient.class);
		OpenShiftService spyService = Mockito.spy(openshiftService);
		doReturn(openshiftClient).when(spyService).getOpenShiftClient();
		doReturn(new StubbedServiceAccount(new ModelNode(), null, null)).when(openshiftClient).execute(ArgumentMatchers.matches("GET"), ArgumentMatchers.matches("ServiceAccount"), ArgumentMatchers.matches(project.getNamespace()), ArgumentMatchers.matches(username), ArgumentMatchers.isNull(), ArgumentMatchers.isNull());
		doReturn(new StubbedServiceAccount(new ModelNode(), null, null)).when(openshiftClient).update(ArgumentMatchers.any(IResource.class));

		// Act
		ServiceAccount serviceaccount = spyService.addImagePullSecretToServiceAccount(secret, username, project);

		// Assert
		assertNotNull("serviceaccount can not be null.", serviceaccount);
	}

	@Test
	public void testGetConfigMaps() throws IOException {
		// Arrange
		String namespace = "demoTestNamespace";
		OpenShiftService openshiftService = new OpenShiftService();

		StubbedOpenShiftClient openshiftClient = Mockito.mock(StubbedOpenShiftClient.class);
		OpenShiftService spyService = Mockito.spy(openshiftService);
		doReturn(openshiftClient).when(spyService).getOpenShiftClient();
		doReturn(null).when(openshiftClient).execute(ArgumentMatchers.matches("GET"), ArgumentMatchers.matches("configmaps"), ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.any(IResource.class));

		// Act
		Collection<IResource> configmaps = spyService.getConfigMaps(namespace);

		// Assert
		assertNull(configmaps);
		verify(openshiftClient).execute(ArgumentMatchers.matches("GET"), ArgumentMatchers.matches("configmaps"), ArgumentMatchers.matches("demoTestNamespace"), ArgumentMatchers.isNull(), ArgumentMatchers.isNull(), ArgumentMatchers.isNull());
	}

	@Test
	public void testGetSecretsWithNotEmptyList() throws IOException {
		// Arrange
		String namespace = "demoTestNamespace";

		StubbedOpenShiftClient openshiftClient = Mockito.mock(StubbedOpenShiftClient.class);
		OpenShiftService openshiftService = new OpenShiftService();
		OpenShiftService spyService = Mockito.spy(openshiftService);
		doReturn(openshiftClient).when(spyService).getOpenShiftClient();
		doReturn(null).when(openshiftClient).execute(ArgumentMatchers.matches("GET"), ArgumentMatchers.matches("secrets"), ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.any(IResource.class));

		// Act
		Collection<IResource> secrets = spyService.getSecrets(namespace);

		// Assert
		assertNull(secrets);
		verify(openshiftClient).execute(ArgumentMatchers.matches("GET"), ArgumentMatchers.matches("secrets"), ArgumentMatchers.anyString(), ArgumentMatchers.isNull(), ArgumentMatchers.isNull(), ArgumentMatchers.isNull());
	}

	@Test
	public void testCreateSecretFromPath() throws IOException {
		// Arrange
		String namespace = "demoTestNamespace";
		String secretName = "demoSecret";
		String secretPath = "/";
		OpenShiftService openshiftService = new OpenShiftService();

		StubbedOpenShiftClient openshiftClient = Mockito.mock(StubbedOpenShiftClient.class);
		SecretBuilder secretBuilder = Mockito.mock(SecretBuilder.class);
		OpenShiftService spyService = Mockito.spy(openshiftService);
		doReturn(openshiftClient).when(spyService).getOpenShiftClient();
		doReturn(secretBuilder).when(spyService).getSecretBuilder(ArgumentMatchers.any(IClient.class));
		doReturn(new StubbedSecret(new ModelNode(), null)).when(secretBuilder).build(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
		doReturn(new StubbedSecret(new ModelNode(), null)).when(openshiftClient).create(ArgumentMatchers.any(IResource.class));

		// Act String secretPath, String secretName, String namespace
		Secret secret = spyService.createSecret(secretPath, secretName, namespace);

		// Assert
		assertNotNull("secret can not be null.", secret);
	}

	@Test
	public void testCreateDockerSecret() throws IOException {
		// Arrange
		String name = "demoTestDockerSecret";
		String secretFilePath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "secrets/docker";
		OpenShiftService openshiftService = new OpenShiftService();
		OpenShiftService spyService = Mockito.spy(openshiftService);
		StubbedOpenShiftProject project = new StubbedOpenShiftProject();
		StubbedOpenShiftClient openshiftClient = Mockito.mock(StubbedOpenShiftClient.class);
		doReturn(openshiftClient).when(spyService).getOpenShiftClient();

		StubbedResourceFactory stubedResourceFactory = Mockito.mock(StubbedResourceFactory.class);
		doReturn(stubedResourceFactory).when(openshiftClient).getResourceFactory();
		doReturn(new StubbedSecret(new ModelNode(), null)).when(stubedResourceFactory).stub(ArgumentMatchers.matches(ResourceKind.SECRET), ArgumentMatchers.anyString(), ArgumentMatchers.anyString());

		// Act
		spyService.createDockerSecret(name, secretFilePath, project);

		// Assert
		verify(openshiftClient).create(ArgumentMatchers.any());
	}

	/**
	 * This only tests the first client.execute call. The second call was added to fix an error returned from openshift.
	 * @throws IOException
	 * @throws CatapultException
	 */
	@Test
	public void testAddImagePullSecretToServiceAccount() throws IOException, CatapultException {
		// Arrange
		String username = "demoTestUsername";
		StubbedSecret secret = new StubbedSecret(new ModelNode(), null);
		StubbedOpenShiftClient openshiftClient = Mockito.mock(StubbedOpenShiftClient.class);
		OpenShiftService openshiftService = new OpenShiftService();
		OpenShiftService spyService = Mockito.spy(openshiftService);
		doReturn(openshiftClient).when(spyService).getOpenShiftClient();
		doReturn(new StubbedServiceAccount(new ModelNode(), null, null)).when(openshiftClient).execute(ArgumentMatchers.matches("GET"), ArgumentMatchers.matches("ServiceAccount"), ArgumentMatchers.matches(secret.getNamespace()), ArgumentMatchers.matches(username), ArgumentMatchers.isNull(), ArgumentMatchers.isNull());

		// Act
		ServiceAccount serviceAccount = spyService.addSecretToServiceAccount(secret, username);

		// Assert
		assertNotNull("serviceAccount must be not null.", serviceAccount);
	}

	@Test
	public void testTriggerBuild() throws IOException {
		// Arrange
		String repositoryUrl = "http://repository.url/demotest";
		String commitId = "12345678";
		OpenShiftService openshiftService = new OpenShiftService();
		StubbedOpenShiftProject project = new StubbedOpenShiftProject();
		StubbedOpenShiftClient openshiftClient = Mockito.mock(StubbedOpenShiftClient.class);
		OpenShiftService spyService = Mockito.spy(openshiftService);
		doReturn(openshiftClient).when(spyService).getOpenShiftClient();
		when(openshiftClient.execute(ArgumentMatchers.matches("GET"),
				ArgumentMatchers.matches("BuildConfig"),
				ArgumentMatchers.matches(project.getName()),
				ArgumentMatchers.isNull(),
				ArgumentMatchers.isNull(),
				ArgumentMatchers.isNull())).thenReturn(new com.openshift.internal.restclient.model.List(new ModelNode(), null, null));

		// Act
		spyService.triggerBuild(repositoryUrl, commitId, project);

		// Assert
		verify(spyService).triggerBuild(repositoryUrl, commitId, project);
	}

}
