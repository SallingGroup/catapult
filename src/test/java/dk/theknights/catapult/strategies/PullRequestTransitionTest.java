package dk.theknights.catapult.strategies;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.CatapultTemplate;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import dk.theknights.catapult.strategies.state.InvalidCatapultStateException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 4/23/18.
 */
public class PullRequestTransitionTest {

	PullRequestTransition transition;

	@Before
	public void setup() {
		transition = new PullRequestTransition();
	}

	@Test
	public void testTransitionFromInitialToCatapultTemplateNotFound() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setOpenShiftProject(null);
		context.setCatapultState(CatapultStateEnum.INITIAL);

		// Act
		CatapultStateEnum state = transition.next(context);

		//Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.CATAPULT_TEMPLATE_NOT_FOUND + ">", CatapultStateEnum.CATAPULT_TEMPLATE_NOT_FOUND, state);
	}

	@Test
	public void testTransitionFromInitialToCatapultTemplateFound() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultTemplate(new CatapultTemplate("Fake template"));
		context.setCatapultState(CatapultStateEnum.INITIAL);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.CATAPULT_TEMPLATE_FOUND + ">", CatapultStateEnum.CATAPULT_TEMPLATE_FOUND, state);
	}

	@Test
	public void testTransitionFromCatapultTemplateNotFoundToDone() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setOpenShiftProject(new StubbedOpenShiftProject());
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_NOT_FOUND);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.CATAPULT_DONE + ">", CatapultStateEnum.CATAPULT_DONE, state);
	}

	@Test
	public void testTransitionFromCatapultTemplateFoundToOpenShiftProjectFound() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setOpenShiftProject(new StubbedOpenShiftProject());
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_FOUND);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.OPENSHIFT_PROJECT_FOUND + ">", CatapultStateEnum.OPENSHIFT_PROJECT_FOUND, state);
	}

	@Test
	public void testTransitionFromCatapultTemplateFoundToOpenShiftProjectNotFound() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setOpenShiftProject(null);
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_FOUND);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.OPENSHIFT_PROJECT_NOT_FOUND + ">", CatapultStateEnum.OPENSHIFT_PROJECT_NOT_FOUND, state);
	}

	@Test
	public void testTransitionFromOpenShiftProjectNotFoundToDone() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.OPENSHIFT_PROJECT_NOT_FOUND);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.CATAPULT_DONE + ">", CatapultStateEnum.CATAPULT_DONE, state);
	}

	@Test
	public void testTransitionFromOpenShiftProjectFoundToOpenShiftProjectDeleted() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.OPENSHIFT_PROJECT_FOUND);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.OPENSHIFT_PROJECT_DELETED + ">", CatapultStateEnum.OPENSHIFT_PROJECT_DELETED, state);
	}

	@Test
	public void testTransitionFromOpenShiftProjectDeletedToDone() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.OPENSHIFT_PROJECT_DELETED);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertEquals("State must be <" + CatapultStateEnum.CATAPULT_DONE + ">", CatapultStateEnum.CATAPULT_DONE, state);
	}

}
