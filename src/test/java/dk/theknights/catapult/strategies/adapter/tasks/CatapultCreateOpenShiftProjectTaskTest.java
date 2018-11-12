package dk.theknights.catapult.strategies.adapter.tasks;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.CatapultException;
import dk.theknights.catapult.strategies.StubbedBitbucketWebhook;
import dk.theknights.catapult.strategies.StubbedOpenShiftProject;
import dk.theknights.catapult.strategies.StubbedOpenShiftService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doReturn;

public class CatapultCreateOpenShiftProjectTaskTest {

	@Before
	public void setUp() {

	}

	@Test
	public void testProcessWithValidProjectName() throws IOException, CatapultException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setWebhook(new StubbedBitbucketWebhook());
		context.setOpenShiftProject(null);

		StubbedOpenShiftService stubedOpenShiftService = new StubbedOpenShiftService();

		CatapultCreateOpenShiftProjectTask spyTask = Mockito.spy(new CatapultCreateOpenShiftProjectTask());
		doReturn(stubedOpenShiftService).when(spyTask).getOpenShiftService();

		// Act
		spyTask.perform(context);

		// Assert
		assertNotNull("Context must have a OpenShift project.", context.getOpenShiftProject());
	}

	@Test
	public void testProcessWithInvalidProjectName() throws IOException, CatapultException {
		// Arrange
		CatapultContext context = Mockito.mock(CatapultContext.class);
		StubbedBitbucketWebhook webhook = Mockito.mock(StubbedBitbucketWebhook.class);
		context.setWebhook(webhook);
		doReturn("bad%project&name").when(webhook).getOpenShiftProjectName();
		doReturn(webhook).when(context).getWebhook();
		context.setOpenShiftProject(new StubbedOpenShiftProject());

		StubbedOpenShiftService spyService = Mockito.spy(new StubbedOpenShiftService());
		doReturn(null).when(spyService).createProject(ArgumentMatchers.anyString());

		CatapultCreateOpenShiftProjectTask spyTask = Mockito.spy(new CatapultCreateOpenShiftProjectTask());
		doReturn(spyService).when(spyTask).getOpenShiftService();

		// Act
		spyTask.perform(context);

		// Assert
		assertNull("Context must NOT have a OpenShift project.", context.getOpenShiftProject());
	}

}
