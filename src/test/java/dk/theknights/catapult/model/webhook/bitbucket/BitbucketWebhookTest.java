package dk.theknights.catapult.model.webhook.bitbucket;

import dk.theknights.catapult.config.CatapultConfig;
import dk.theknights.catapult.config.CatapultProperties;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 8/21/18.
 */
public class BitbucketWebhookTest {

	public static final int MAX_PROJECT_NAME_LENGTH = 76;
	public static final String PROJECT_NAME_REGEX = "[a-z]([-a-z0-9]*[a-z0-9])?";

	@Test
	public void testGetOpenShiftProjectName() {
		// Arrange
		BitbucketWebhook webhook = new BitbucketWebhook();
		Push push = new Push();
		Change change = new Change();
		New newChange = new New();
		newChange.setType("branch");
		newChange.setName("Random-branchname");
		change.setNew(newChange);
		ArrayList<Change> changes = new ArrayList<>();
		changes.add(change);
		push.setChanges(changes);
		Repository repository = new Repository();
		Project project = new Project();
		project.setName("Some-random-name");
		repository.setProject(project);
		webhook.setPush(push);
		webhook.setRepository(repository);

		CatapultProperties catapultProperties = Mockito.mock(CatapultProperties.class);
		CatapultConfig configuration = new CatapultConfig(catapultProperties);

		when(catapultProperties.getProjectMapping()).thenReturn("");
		webhook.setCatapultConfig(configuration);

		// Act
		String openshiftProjectName = webhook.getOpenShiftProjectName();

		// Assert
		assertTrue("String was (" + openshiftProjectName + ") length (" + openshiftProjectName.length() + ")", MAX_PROJECT_NAME_LENGTH > openshiftProjectName.length());

		assertNotNull("openshiftProjectName must not be null", openshiftProjectName);
		boolean matches = openshiftProjectName.matches(PROJECT_NAME_REGEX);

		assertTrue("Project name must match: " + PROJECT_NAME_REGEX + " Project name was (" + openshiftProjectName + ")", matches);
	}

	@Test
	public void testGetOpenShiftProjectNameLongerThan76Characters() {
		// Arrange
		BitbucketWebhook webhook = new BitbucketWebhook();
		Push push = new Push();
		Change change = new Change();
		New newChange = new New();
		newChange.setType("branch");
		newChange.setName("Random-very-very-very-very-very-very-very-very-long-branchname-that-is-not-valid");
		change.setNew(newChange);
		ArrayList<Change> changes = new ArrayList<>();
		changes.add(change);
		push.setChanges(changes);
		Repository repository = new Repository();
		Project project = new Project();
		project.setName("Some random name");
		repository.setProject(project);
		webhook.setPush(push);
		webhook.setRepository(repository);

		CatapultProperties catapultProperties = Mockito.mock(CatapultProperties.class);
		CatapultConfig configuration = new CatapultConfig(catapultProperties);

		when(catapultProperties.getProjectMapping()).thenReturn("");
		webhook.setCatapultConfig(configuration);

		// Act
		String openshiftProjectName = webhook.getOpenShiftProjectName();

		// Assert
		assertFalse("Project name was (" + openshiftProjectName + ") length (" + openshiftProjectName.length() + ") must be larger than " + MAX_PROJECT_NAME_LENGTH, MAX_PROJECT_NAME_LENGTH > openshiftProjectName.length());

		assertNotNull("openshiftProjectName must not be null", openshiftProjectName);
		boolean matches = openshiftProjectName.matches(PROJECT_NAME_REGEX);

		assertTrue("Project name must match: " + PROJECT_NAME_REGEX, matches);
	}

	@Test
	public void testGetOpenShiftProjectNameWithSpaces() {
		// Arrange
		BitbucketWebhook webhook = new BitbucketWebhook();
		Push push = new Push();
		Change change = new Change();
		New newChange = new New();
		newChange.setType("branch");
		newChange.setName("Random branchname");
		change.setNew(newChange);
		ArrayList<Change> changes = new ArrayList<>();
		changes.add(change);
		push.setChanges(changes);
		Repository repository = new Repository();
		Project project = new Project();
		project.setName("Some-random-name");
		repository.setProject(project);
		webhook.setPush(push);
		webhook.setRepository(repository);

		CatapultProperties catapultProperties = Mockito.mock(CatapultProperties.class);
		CatapultConfig configuration = new CatapultConfig(catapultProperties);

		when(catapultProperties.getProjectMapping()).thenReturn("");
		webhook.setCatapultConfig(configuration);

		// Act
		String openshiftProjectName = webhook.getOpenShiftProjectName();

		// Assert
		assertTrue("String was (" + openshiftProjectName + ") length (" + openshiftProjectName.length() + ")", MAX_PROJECT_NAME_LENGTH > openshiftProjectName.length());

		assertNotNull("openshiftProjectName must not be null", openshiftProjectName);
		boolean matches = openshiftProjectName.matches(PROJECT_NAME_REGEX);

		assertFalse("Project name must match: " + PROJECT_NAME_REGEX + " Project name was (" + openshiftProjectName + ")", matches);
	}

	@Test
	public void testGetOpenShiftProjectNameWithNamingMapping() {
		// Arrange
		BitbucketWebhook webhook = new BitbucketWebhook();
		Push push = new Push();
		Change change = new Change();
		New newChange = new New();
		newChange.setType("branch");
		newChange.setName("demotester");
		change.setNew(newChange);
		ArrayList<Change> changes = new ArrayList<>();
		changes.add(change);
		push.setChanges(changes);
		Repository repository = new Repository();
		Project project = new Project();
		project.setName("somebitbucketproject");
		repository.setProject(project);
		webhook.setPush(push);
		webhook.setRepository(repository);

		CatapultProperties catapultProperties = Mockito.mock(CatapultProperties.class);
		CatapultConfig configuration = new CatapultConfig(catapultProperties);

		when(catapultProperties.getProjectMapping()).thenReturn("somebitbucketproject=masterproject");
		webhook.setCatapultConfig(configuration);

		// Act
		String openshiftProjectName = webhook.getOpenShiftProjectName();

		// Assert
		assertTrue("String was (" + openshiftProjectName + ") length (" + openshiftProjectName.length() + ")", MAX_PROJECT_NAME_LENGTH > openshiftProjectName.length());

		assertNotNull("openshiftProjectName must not be null", openshiftProjectName);

		boolean matches = openshiftProjectName.matches(PROJECT_NAME_REGEX);
		assertTrue("Project name must match: " + PROJECT_NAME_REGEX + " Project name was (" + openshiftProjectName + ")", matches);

		assertNotEquals("Project name must NOT match: hybriscompinents", "demotester", openshiftProjectName);
		assertEquals("Project name must match: masterproject", "masterproject", openshiftProjectName);
	}


}
