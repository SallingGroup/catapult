package dk.theknights.catapult.strategies;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.CatapultTemplate;
import dk.theknights.catapult.strategies.adapter.CatapultDoneStateAdapter;
import dk.theknights.catapult.strategies.adapter.CatapultOpenShiftProjectAdapter;
import dk.theknights.catapult.strategies.adapter.PullRequestAdapter;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import dk.theknights.catapult.strategies.state.InvalidCatapultStateException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;

/**
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 4/23/18.
 */
public class PullRequestTransitionTest {

	PullRequestTransition transition;
	CatapultContext context;
	StubbedBitbucketWebhook bitbucketWebhook;

	@Before
	public void setup() {
		transition = new PullRequestTransition();
		context = new CatapultContext();
		bitbucketWebhook = new StubbedBitbucketWebhook();
		bitbucketWebhook.setIsPullRequest(true);
		context.setWebhook(bitbucketWebhook);
	}


	@Test
	public void testTransitionFromInitialToCatapultTemplateNotFound() throws InvalidCatapultStateException {
		// Arrange
		context.setOpenShiftProject(null);
		context.setCatapultState(CatapultStateEnum.INITIAL);

		// Act
		CatapultStateEnum state = transition.next(context);

		//Assert
		assertNotNull("CatapultState can not be null.", state);
		assertNotNull("Adapter cannot be null.", context.getCatapultAdapter(context));
		assertThat("Adapter must be PullRequestAdapter class!", context.getCatapultAdapter(context), instanceOf(PullRequestAdapter.class));
		assertEquals("State must be <" + CatapultStateEnum.CATAPULT_TEMPLATE_NOT_FOUND + ">", CatapultStateEnum.CATAPULT_TEMPLATE_NOT_FOUND, state);
	}

	@Test
	public void testTransitionFromInitialToCatapultTemplateFound() throws InvalidCatapultStateException {
		// Arrange
		context.setCatapultTemplate(new CatapultTemplate("Fake template"));
		context.setCatapultState(CatapultStateEnum.INITIAL);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertNotNull("Adapter cannot be null.", context.getCatapultAdapter(context));
		assertThat("Adapter must be PullRequestAdapter class!", context.getCatapultAdapter(context), instanceOf(PullRequestAdapter.class));
		assertEquals("State must be <" + CatapultStateEnum.CATAPULT_TEMPLATE_FOUND + ">", CatapultStateEnum.CATAPULT_TEMPLATE_FOUND, state);
	}

	@Test
	public void testTransitionFromCatapultTemplateNotFoundToDone() throws InvalidCatapultStateException {
		// Arrange
		context.setOpenShiftProject(new StubbedOpenShiftProject());
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_NOT_FOUND);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertNotNull("Adapter cannot be null.", context.getCatapultAdapter(context));
		assertThat("Adapter must be CatapultOpenShiftProjectAdapter class!", context.getCatapultAdapter(context), instanceOf(CatapultOpenShiftProjectAdapter.class));
		assertEquals("State must be <" + CatapultStateEnum.CATAPULT_DONE + ">", CatapultStateEnum.CATAPULT_DONE, state);
	}

	@Test
	public void testTransitionFromCatapultTemplateFoundToOpenShiftProjectFound() throws InvalidCatapultStateException {
		// Arrange
		context.setOpenShiftProject(new StubbedOpenShiftProject());
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_FOUND);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertNotNull("Adapter cannot be null.", context.getCatapultAdapter(context));
		assertThat("Adapter must be PullRequestAdapter class!", context.getCatapultAdapter(context), instanceOf(PullRequestAdapter.class));
		assertEquals("State must be <" + CatapultStateEnum.OPENSHIFT_PROJECT_FOUND + ">", CatapultStateEnum.OPENSHIFT_PROJECT_FOUND, state);
	}

	@Test
	public void testTransitionFromCatapultTemplateFoundToOpenShiftProjectNotFound() throws InvalidCatapultStateException {
		// Arrange
		context.setOpenShiftProject(null);
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_FOUND);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertNotNull("Adapter cannot be null.", context.getCatapultAdapter(context));
		assertThat("Adapter must be PullRequestAdapter class!", context.getCatapultAdapter(context), instanceOf(PullRequestAdapter.class));
		assertEquals("State must be <" + CatapultStateEnum.OPENSHIFT_PROJECT_NOT_FOUND + ">", CatapultStateEnum.OPENSHIFT_PROJECT_NOT_FOUND, state);
	}

	@Test
	public void testTransitionFromOpenShiftProjectNotFoundToDone() throws InvalidCatapultStateException {
		// Arrange
		context.setCatapultState(CatapultStateEnum.OPENSHIFT_PROJECT_NOT_FOUND);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertNotNull("Adapter cannot be null.", context.getCatapultAdapter(context));
		assertThat("Adapter must be PullRequestAdapter class!", context.getCatapultAdapter(context), instanceOf(PullRequestAdapter.class));
		assertEquals("State must be <" + CatapultStateEnum.CATAPULT_DONE + ">", CatapultStateEnum.CATAPULT_DONE, state);
	}

	@Test
	public void testTransitionFromOpenShiftProjectFoundToOpenShiftProjectDeleted() throws InvalidCatapultStateException {
		// Arrange
		context.setCatapultState(CatapultStateEnum.OPENSHIFT_PROJECT_FOUND);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertNotNull("Adapter cannot be null.", context.getCatapultAdapter(context));
		assertThat("Adapter must be PullRequestAdapter class!", context.getCatapultAdapter(context), instanceOf(PullRequestAdapter.class));
		assertEquals("State must be <" + CatapultStateEnum.OPENSHIFT_PROJECT_DELETED + ">", CatapultStateEnum.OPENSHIFT_PROJECT_DELETED, state);
	}

	@Test
	public void testTransitionFromOpenShiftProjectDeletedToDone() throws InvalidCatapultStateException {
		// Arrange
		context.setCatapultState(CatapultStateEnum.OPENSHIFT_PROJECT_DELETED);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNotNull("CatapultState can not be null.", state);
		assertNotNull("Adapter cannot be null.", context.getCatapultAdapter(context));
		assertThat("Adapter must be PullRequestAdapter class!", context.getCatapultAdapter(context), instanceOf(PullRequestAdapter.class));
		assertEquals("State must be <" + CatapultStateEnum.CATAPULT_DONE + ">", CatapultStateEnum.CATAPULT_DONE, state);
	}

	@Test
	public void testDone() throws InvalidCatapultStateException {
		// Arrange
		context.setCatapultState(CatapultStateEnum.CATAPULT_DONE);

		// Act
		CatapultStateEnum state = transition.next(context);

		// Assert
		assertNull("CatapultState can not be null.", state);
		assertNotNull("Adapter cannot be null.", context.getCatapultAdapter(context));
		assertThat("Adapter must be CatapultDoneAdapter class!", context.getCatapultAdapter(context), instanceOf(CatapultDoneStateAdapter.class));
	}

}
