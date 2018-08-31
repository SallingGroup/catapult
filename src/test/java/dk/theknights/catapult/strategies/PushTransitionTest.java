package dk.theknights.catapult.strategies;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.CatapultTemplate;
import dk.theknights.catapult.config.secret.BasicAuthSecret;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import dk.theknights.catapult.strategies.state.InvalidCatapultStateException;
import org.jboss.dmr.ModelNode;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PushTransitionTest {

	PushTransition transition;

	@Before
	public void setUp() {
		transition = new PushTransition();
	}
/**
	@Test(expected = InvalidCatapultStateException.class)
	public void testInvalidCatapultState() throws Exception {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultTemplate(null);
		context.setCatapultState(new CatapultState());
		context.getCatapultState().setId("WRONG_STATE_ID");

		// Act
		transition.next(context);

		// Assert
	}
**/
	@Test
	public void testInitialToCatapultTemplateNotFound() throws Exception {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultTemplate(null);
		context.setCatapultState(CatapultStateEnum.INITIAL);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.CATAPULT_TEMPLATE_NOT_FOUND + ">", state, CatapultStateEnum.CATAPULT_TEMPLATE_NOT_FOUND);
	}

	@Test
	public void testInitialToCatapultTemplateFound() throws Exception {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultTemplate(new CatapultTemplate(""));
		context.setCatapultState(CatapultStateEnum.INITIAL);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.CATAPULT_TEMPLATE_FOUND + ">", state, CatapultStateEnum.CATAPULT_TEMPLATE_FOUND);
	}

	@Test
	public void testCatapultTemplateNotFoundToCatapultDone() throws Exception {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultTemplate(new CatapultTemplate(""));
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_NOT_FOUND);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.CATAPULT_DONE + ">", state, CatapultStateEnum.CATAPULT_DONE);
	}

	@Test
	public void testCatapultTemplateFoundToOpenShiftProjectFound() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultTemplate(new CatapultTemplate(""));
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_FOUND);
		context.setOpenShiftProject(new StubbedOpenShiftProject());

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.OPENSHIFT_PROJECT_FOUND + ">", state, CatapultStateEnum.OPENSHIFT_PROJECT_FOUND);
	}

	@Test
	public void testCatapultTemplateFoundToOpenShiftProjectNotFound() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_FOUND);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.OPENSHIFT_PROJECT_NOT_FOUND + ">", state, CatapultStateEnum.OPENSHIFT_PROJECT_NOT_FOUND);
	}

	@Test
	public void testOpenShiftProjectFoundToCatapultTemplateChanged() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultTemplateChanged(true);
		context.setCatapultState(CatapultStateEnum.OPENSHIFT_PROJECT_FOUND);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.CATAPULT_TEMPLATE_CHANGED + ">", state, CatapultStateEnum.CATAPULT_TEMPLATE_CHANGED);
	}

	@Test
	public void testCatapultTemplateChangedToDone() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultTemplateChanged(true);
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_CHANGED);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.CATAPULT_DONE + ">", state, CatapultStateEnum.CATAPULT_DONE);
	}

	@Test
	public void testOpenShiftProjectFoundToCatapultTemplateNotChanged() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultTemplateChanged(false);
		context.setCatapultState(CatapultStateEnum.OPENSHIFT_PROJECT_FOUND);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.CATAPULT_TEMPLATE_NOT_CHANGED + ">", state, CatapultStateEnum.CATAPULT_TEMPLATE_NOT_CHANGED);
	}

	@Test
	public void testCatapultTemplateNotChangedToDone() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultTemplateChanged(false);
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_NOT_CHANGED);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.CATAPULT_DONE + ">", state, CatapultStateEnum.CATAPULT_DONE);
	}

	@Test
	public void testOpenShiftProjectNotFoundToOpenShiftProjectCreated() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.OPENSHIFT_PROJECT_NOT_FOUND);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.OPENSHIFT_PROJECT_CREATED + ">", state, CatapultStateEnum.OPENSHIFT_PROJECT_CREATED);
	}

	@Test
	public void testOpenShiftProjectCreatedToPolicyBindingsUpdated() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.OPENSHIFT_PROJECT_CREATED);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.POLICY_BINDINGS_UPDATED + ">", state, CatapultStateEnum.POLICY_BINDINGS_UPDATED);
	}

	@Test
	public void testPolicyBindingsUpdatedToSecretsUpdated() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.addSecret(new BasicAuthSecret(new ModelNode(), null));
		context.setCatapultState(CatapultStateEnum.POLICY_BINDINGS_UPDATED);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.SECRETS_UPDATED + ">", state, CatapultStateEnum.SECRETS_UPDATED);
	}

	@Test
	public void testPolicyBindingsUpdatedToNoSecretsFound() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.POLICY_BINDINGS_UPDATED);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.NO_SECRETS_FOUND + ">", state, CatapultStateEnum.NO_SECRETS_FOUND);
	}

	@Test
	public void testSecretsUpdatedToConfigMapsUpdated() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.addConfigMap(new BasicAuthSecret(new ModelNode(), null));
		context.setCatapultState(CatapultStateEnum.SECRETS_UPDATED);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.CONFIGMAPS_UPDATED + ">", state, CatapultStateEnum.CONFIGMAPS_UPDATED);
	}

	@Test
	public void testNoSecretsFoundToConfigMapsUpdated() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.addConfigMap(new BasicAuthSecret(new ModelNode(), null));
		context.setCatapultState(CatapultStateEnum.NO_SECRETS_FOUND);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.CONFIGMAPS_UPDATED + ">", state, CatapultStateEnum.CONFIGMAPS_UPDATED);
	}

	@Test
	public void testSecretsUpdatedToNoConfigMapsFound() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.SECRETS_UPDATED);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.NO_CONFIGMAPS_FOUND + ">", state, CatapultStateEnum.NO_CONFIGMAPS_FOUND);
	}

	@Test
	public void testNoSecretsFoundToNoConfigMapsFound() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.NO_SECRETS_FOUND);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.NO_CONFIGMAPS_FOUND + ">", state, CatapultStateEnum.NO_CONFIGMAPS_FOUND);
	}

	@Test
	public void testConfigMapsUpdatedToCatapultTemplateProcessed() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.CONFIGMAPS_UPDATED);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.CATAPULT_TEMPLATE_PROCESSED + ">", state, CatapultStateEnum.CATAPULT_TEMPLATE_PROCESSED);
	}

	@Test
	public void testConfigMapsUpdatedToCatapultTemplateProcessError() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultTemplate(new CatapultTemplate("ERROR"));
		context.setCatapultState(CatapultStateEnum.CONFIGMAPS_UPDATED);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.CATAPULT_TEMPLATE_PROCESS_ERROR + ">", state, CatapultStateEnum.CATAPULT_TEMPLATE_PROCESS_ERROR);
	}

	@Test
	public void testNoConfigMapsFoundToCatapultTemplateProcessed() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.NO_CONFIGMAPS_FOUND);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.CATAPULT_TEMPLATE_PROCESSED + ">", state, CatapultStateEnum.CATAPULT_TEMPLATE_PROCESSED);
	}

	@Test
	public void testNoConfigMapsFoundToCatapultTemplateProcessError() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultTemplate(new CatapultTemplate("ERROR"));
		context.setCatapultState(CatapultStateEnum.NO_CONFIGMAPS_FOUND);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.CATAPULT_TEMPLATE_PROCESS_ERROR + ">", state, CatapultStateEnum.CATAPULT_TEMPLATE_PROCESS_ERROR);
	}

	@Test
	public void testCatapultTemplateProcessedToDone() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_PROCESSED);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.CATAPULT_DONE + ">", state, CatapultStateEnum.CATAPULT_DONE);
	}

	@Test
	public void testCatapultTemplateProcessErrorToDone() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_PROCESS_ERROR);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.CATAPULT_DONE + ">", state, CatapultStateEnum.CATAPULT_DONE);
	}

}
