package dk.theknights.catapult.strategies.adapter.tasks;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.CatapultException;
import dk.theknights.catapult.strategies.StubbedOpenShiftProject;
import dk.theknights.catapult.strategies.StubbedOpenShiftService;
import dk.theknights.catapult.strategies.StubbedSecret;
import org.jboss.dmr.ModelNode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CatapultCreateOpenShiftSecretTaskTest {

	@Before
	public void setUp() {

	}

	@Test
	public void testProcessCreateMultipleSecrets() throws IOException, CatapultException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.addSecret(new StubbedSecret(new ModelNode(), null));
		context.addSecret(new StubbedSecret(new ModelNode(), null));
		context.addSecret(new StubbedSecret(new ModelNode(), null));
		context.setOpenShiftProject(new StubbedOpenShiftProject());

		CatapultCreateOpenShiftSecretTask spyTask = Mockito.spy(new CatapultCreateOpenShiftSecretTask());
		StubbedOpenShiftService stubedOpenShiftService = Mockito.spy(new StubbedOpenShiftService());
		doReturn(stubedOpenShiftService).when(spyTask).getOpenShiftService();
		doReturn(new StubbedSecret(new ModelNode(), null)).when(stubedOpenShiftService).createSecret(ArgumentMatchers.anyString(), ArgumentMatchers.any(StubbedOpenShiftProject.class), ArgumentMatchers.any(StubbedSecret.class));

		// Act
		spyTask.perform(context);

		// Assert
		assertEquals("context must have 3 secrets to add.", 3, context.getSecrets().size());
		verify(stubedOpenShiftService, times(3)).createSecret(ArgumentMatchers.anyString(), ArgumentMatchers.any(StubbedOpenShiftProject.class), ArgumentMatchers.any(StubbedSecret.class));
	}

	@Test
	public void testProcessCreateNoSecrets() throws IOException, CatapultException {
		// Arrange
		CatapultContext context = new CatapultContext();

		CatapultCreateOpenShiftSecretTask spyTask = Mockito.spy(new CatapultCreateOpenShiftSecretTask());
		StubbedOpenShiftService stubedOpenShiftService = Mockito.spy(new StubbedOpenShiftService());
		doReturn(stubedOpenShiftService).when(spyTask).getOpenShiftService();

		// Act
		spyTask.perform(context);

		// Assert
		assertEquals("context must have 0 secrets to add.", 0, context.getSecrets().size());
		verify(stubedOpenShiftService, times(0)).createSecret(ArgumentMatchers.anyString(), ArgumentMatchers.any(StubbedOpenShiftProject.class), ArgumentMatchers.any(StubbedSecret.class));
	}

}
