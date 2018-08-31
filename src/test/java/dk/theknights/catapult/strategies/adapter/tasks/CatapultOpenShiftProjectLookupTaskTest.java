package dk.theknights.catapult.strategies.adapter.tasks;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.StubbedBitbucketWebhook;
import dk.theknights.catapult.strategies.StubbedOpenShiftProject;
import dk.theknights.catapult.strategies.StubbedOpenShiftService;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class CatapultOpenShiftProjectLookupTaskTest {

	@Test
	public void testProcessWithOpenShiftProjectNotFoundFromBitbucketWebhook() throws IOException {
		// Arrange
		CatapultContext context = new CatapultContext();
		StubbedBitbucketWebhook webhook = Mockito.mock(StubbedBitbucketWebhook.class);
		context.setWebhook(webhook);
		context.setOpenShiftProject(new StubbedOpenShiftProject());
		CatapultOpenShiftProjectLookupTask task = new CatapultOpenShiftProjectLookupTask();
		CatapultOpenShiftProjectLookupTask spyTask = Mockito.spy(task);

		StubbedOpenShiftService openshiftService = Mockito.spy(new StubbedOpenShiftService());
		when(openshiftService.getProject(ArgumentMatchers.anyString())).thenReturn(null);
		when(spyTask.getOpenShiftService()).thenReturn(openshiftService);
		doReturn("gootprojectname").when(webhook).getOpenShiftProjectName();

		// Act
		spyTask.perform(context);

		// Assert
		assertNull("OpenShift project must be null.", context.getOpenShiftProject());
	}

	@Test
	public void testProcessWithOpenShiftProjectFoundFromBitbucketWebhook() throws IOException {
		// Arrange
		CatapultContext context = new CatapultContext();
		StubbedBitbucketWebhook webhook = Mockito.mock(StubbedBitbucketWebhook.class);
		context.setWebhook(webhook);
		context.setOpenShiftProject(null);
		CatapultOpenShiftProjectLookupTask task = new CatapultOpenShiftProjectLookupTask();
		CatapultOpenShiftProjectLookupTask spyTask = Mockito.spy(task);

		StubbedOpenShiftService openshiftService = Mockito.spy(new StubbedOpenShiftService());
		when(openshiftService.getProject(ArgumentMatchers.anyString())).thenReturn(new StubbedOpenShiftProject());
		when(spyTask.getOpenShiftService()).thenReturn(openshiftService);
		doReturn("gootprojectname").when(webhook).getOpenShiftProjectName();

		// Act
		spyTask.perform(context);

		// Assert
		assertNotNull("OpenShift project must not be null.", context.getOpenShiftProject());
	}

	/**
	 * These test must test twhat happens when users make repos and branches that does not comply with namespace rules.
	 * OpenShift will not let us create the project so we test that Catapult will fail with a nice error message that
	 * we can sen to the user.
	 *
	 * Regex for validating namespaces:
	 * [a-z0-9]([-a-z0-9]*[a-z0-9])?(\.[a-z0-9]([-a-z0-9]*[a-z0-9])?)*
	 */

	@Test
	public void testProcessWithInvalidBranchName() {
		// Arrange
		CatapultContext context = new CatapultContext();
		StubbedBitbucketWebhook webhook = Mockito.mock(StubbedBitbucketWebhook.class);
		context.setWebhook(webhook);
		doReturn("Crap%^&branch name").when(webhook).getBranchName();
		CatapultOpenShiftProjectLookupTask task = new CatapultOpenShiftProjectLookupTask();
		CatapultOpenShiftProjectLookupTask spyTask = Mockito.spy(task);
		doReturn(new StubbedOpenShiftService()).when(spyTask).getOpenShiftService();

		// Act
		spyTask.perform(context);

		// Assert
		assertNull("OpenShift project must be null.", context.getOpenShiftProject());
	}

	@Test
	public void testProcessWithInvalidRepositoryName() {
		// Arrange
		CatapultContext context = new CatapultContext();
		StubbedBitbucketWebhook webhook = Mockito.mock(StubbedBitbucketWebhook.class);
		context.setWebhook(webhook);
		doReturn("Crap%^&branch name").when(webhook).getRepositoryName();
		CatapultOpenShiftProjectLookupTask task = new CatapultOpenShiftProjectLookupTask();
		CatapultOpenShiftProjectLookupTask spyTask = Mockito.spy(task);
		doReturn(new StubbedOpenShiftService()).when(spyTask).getOpenShiftService();

		// Act
		spyTask.perform(context);

		// Assert
		assertNull("OpenShift project must be null.", context.getOpenShiftProject());
	}

}
