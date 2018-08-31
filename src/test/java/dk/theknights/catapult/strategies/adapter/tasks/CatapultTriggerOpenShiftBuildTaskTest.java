package dk.theknights.catapult.strategies.adapter.tasks;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.StubbedBitbucketWebhook;
import dk.theknights.catapult.strategies.StubbedOpenShiftProject;
import dk.theknights.catapult.strategies.StubbedOpenShiftService;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CatapultTriggerOpenShiftBuildTaskTest {

	@Test
	public void testProcessWithBuildFound() throws IOException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setWebhook(new StubbedBitbucketWebhook());
		CatapultTriggerOpenShiftBuildTask task = new CatapultTriggerOpenShiftBuildTask();
		CatapultTriggerOpenShiftBuildTask spyTask = Mockito.spy(task);

		StubbedOpenShiftService openshiftService = Mockito.spy(new StubbedOpenShiftService());
		when(openshiftService.getProject(ArgumentMatchers.anyString())).thenReturn(new StubbedOpenShiftProject());
		when(spyTask.getOpenShiftService()).thenReturn(openshiftService);

		// Act
		spyTask.perform(context);

		// Assert
		verify(openshiftService, Mockito.times(1)).triggerBuild(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.isNull());
	}

	@Test
	public void testProcessWithBuildNotFound() throws IOException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setWebhook(new StubbedBitbucketWebhook());
		context.setBuildsTriggered(null);

		CatapultTriggerOpenShiftBuildTask task = new CatapultTriggerOpenShiftBuildTask();
		CatapultTriggerOpenShiftBuildTask spyTask = Mockito.spy(task);

		StubbedOpenShiftService openshiftService = Mockito.spy(new StubbedOpenShiftService());
		when(openshiftService.triggerBuild(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.isNull())).thenReturn(null);
		when(spyTask.getOpenShiftService()).thenReturn(openshiftService);

		// Act
		spyTask.perform(context);

		// Assert
		assertNotNull("context can not have buildtriggered list null", context.getBuildsTriggered());
		assertThat(context.getBuildsTriggered(), IsEmptyCollection.empty());
	}

}
