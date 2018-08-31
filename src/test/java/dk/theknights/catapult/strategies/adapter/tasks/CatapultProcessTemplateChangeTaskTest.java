package dk.theknights.catapult.strategies.adapter.tasks;

import dk.theknights.catapult.CatapultContext;
import org.junit.Test;

import static org.junit.Assert.assertNull;

public class CatapultProcessTemplateChangeTaskTest {

	@Test
	public void testProcessWithOpenShiftProjectNotExisting() {
		// Arrange
		CatapultContext context = new CatapultContext();
		CatapultProcessTemplateChangeTask task = new CatapultProcessTemplateChangeTask();

		// Act
		task.perform(context);

		// Assert
		assertNull("OpenShift project must be null.", context.getOpenShiftProject());
	}

}
