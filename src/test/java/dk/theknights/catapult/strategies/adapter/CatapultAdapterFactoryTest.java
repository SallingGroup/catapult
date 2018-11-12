package dk.theknights.catapult.strategies.adapter;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.model.webhook.RequestTypeEnum;
import dk.theknights.catapult.strategies.StubbedBitbucketWebhook;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import dk.theknights.catapult.strategies.state.InvalidCatapultStateException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;

/**
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/7/18.
 */
public class CatapultAdapterFactoryTest {

	CatapultAdapterFactory catapultAdapterFactory;

	@Before
	public void setUp() {
		catapultAdapterFactory = CatapultAdapterFactory.getInstance();
	}

	@Test
	public void testInitialStatePushRequest() throws InvalidCatapultStateException {
		// Arrange
		CatapultStateEnum state = CatapultStateEnum.INITIAL;
		//new CatapultInitialState();

		// Act
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(state);

		// Assert
		assertThat("catapultAdapter must be instance of CatapultTemplateAdapter when given " + CatapultStateEnum.INITIAL + " state", catapultAdapter, instanceOf(CatapultInitialStateAdapter.class));
	}

	@Test
	public void testInitialStatePullRequest() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.INITIAL);
		StubbedBitbucketWebhook webhoook = Mockito.spy(new StubbedBitbucketWebhook());
		doReturn(RequestTypeEnum.PULL_REQUEST).when(webhoook).getRequestType();
		context.setWebhook(webhoook);

		// Act
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(context);

		// Assert
		assertThat("catapultAdapter must be instance of CatapultOpenShiftProjectAdapter when given " + CatapultStateEnum.INITIAL + " state", catapultAdapter, instanceOf(PullRequestAdapter.class));
	}

	@Test
	public void testInitialStateTagRequest() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.INITIAL);
		StubbedBitbucketWebhook webhoook = Mockito.spy(new StubbedBitbucketWebhook());
		doReturn(RequestTypeEnum.TAG_REQUEST).when(webhoook).getRequestType();
		context.setWebhook(webhoook);

		// Act
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(context);

		// Assert
		assertThat("catapultAdapter must be instance of CatapultOpenShiftProjectAdapter when given " + CatapultStateEnum.INITIAL + " state", catapultAdapter, instanceOf(TagAdapter.class));
	}

	@Test(expected = InvalidCatapultStateException.class)
	public void testNoWebhookInitialState() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setWebhook(null);
		context.setCatapultState(CatapultStateEnum.INITIAL);

		// Act
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(context);

		// Assert
		assertNull("CatapultAdapter must be null", catapultAdapter);
	}

	@Test
	public void testCatapultTemplateNotFoundStatePushRequest() throws InvalidCatapultStateException {
		// Arrange
		CatapultStateEnum state = CatapultStateEnum.CATAPULT_TEMPLATE_NOT_FOUND;
		//new CatapultTemplateNotFoundState();

		// Act
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(state);

		// Assert
		assertThat("catapultAdapter must be instance of CatapultOpenShiftProjectAdapter when given " + CatapultStateEnum.CATAPULT_TEMPLATE_NOT_FOUND + " state", catapultAdapter, instanceOf(CatapultOpenShiftProjectAdapter.class));
	}

	@Test
	public void testCatapultTemplateFoundStatePushRequest() throws InvalidCatapultStateException {
		// Arrange
		CatapultStateEnum state = CatapultStateEnum.CATAPULT_TEMPLATE_FOUND;
		//new CatapultTemplateFoundState();

		// Act
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(state);

		// Assert
		assertThat("catapultAdapter must be instance of CatapultOpenShiftProjectAdapter when given " + CatapultStateEnum.CATAPULT_TEMPLATE_FOUND + " state", catapultAdapter, instanceOf(CatapultOpenShiftProjectAdapter.class));
	}

	@Test
	public void testOpenShiftProjectFoundStatePushRequest() throws InvalidCatapultStateException {
		// Arrange
		CatapultStateEnum state = CatapultStateEnum.OPENSHIFT_PROJECT_FOUND;

		// Act
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(state);

		// Assert
		assertThat("catapultAdapter must be instance of CatapultOpenShiftProjectAdapter when given " + CatapultStateEnum.OPENSHIFT_PROJECT_FOUND + " state", catapultAdapter, instanceOf(CatapultOpenShiftProjectAdapter.class));
	}

	@Test
	public void testProjectNotFoundStatePushRequest() throws InvalidCatapultStateException {
		// Arrange
		CatapultStateEnum state = CatapultStateEnum.OPENSHIFT_PROJECT_NOT_FOUND;

		// Act
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(state);

		// Assert
		assertThat("catapultAdapter must be instance of CatapultOpenShiftProjectAdapter when given " + CatapultStateEnum.OPENSHIFT_PROJECT_NOT_FOUND + " state", catapultAdapter, instanceOf(CatapultOpenShiftProjectAdapter.class));
	}

	@Test
	public void testCatapultTemplateNotChangedStatePushRequest() throws InvalidCatapultStateException {
		// Arrange
		CatapultStateEnum state = CatapultStateEnum.CATAPULT_TEMPLATE_NOT_CHANGED;

		// Act
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(state);

		// Assert
		assertThat("catapultAdapter must be instance of CatapultOpenShiftProjectAdapter when given " + CatapultStateEnum.CATAPULT_TEMPLATE_NOT_CHANGED + " state", catapultAdapter, instanceOf(CatapultTemplateAdapter.class));
	}

	@Test
	public void testCatapultTemplateChangedStatePushRequest() throws InvalidCatapultStateException {
		// Arrange
		CatapultStateEnum state = CatapultStateEnum.CATAPULT_TEMPLATE_CHANGED;

		// Act
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(state);

		// Assert
		assertThat("catapultAdapter must be instance of CatapultOpenShiftProjectAdapter when given " + CatapultStateEnum.CATAPULT_TEMPLATE_CHANGED + " state", catapultAdapter, instanceOf(CatapultTemplateAdapter.class));
	}

	@Test
	public void testOpenShiftProjectCreatedStatePushRequest() throws InvalidCatapultStateException {
		// Arrange
		CatapultStateEnum state = CatapultStateEnum.OPENSHIFT_PROJECT_CREATED;

		// Act
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(state);

		// Assert
		assertThat("catapultAdapter must be instance of CatapultOpenShiftProjectAdapter when given " + CatapultStateEnum.OPENSHIFT_PROJECT_CREATED + " state", catapultAdapter, instanceOf(CatapultOpenShiftProjectAdapter.class));
	}

	@Test
	public void testPolicyBindingsUpdatedStatePushRequest() throws InvalidCatapultStateException {
		// Arrange
		CatapultStateEnum state = CatapultStateEnum.POLICY_BINDINGS_UPDATED;

		// Act
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(state);

		// Assert
		assertThat("catapultAdapter must be instance of CatapultOpenShiftProjectAdapter when given " + CatapultStateEnum.POLICY_BINDINGS_UPDATED + " state", catapultAdapter, instanceOf(CatapultOpenShiftSecretsAdapter.class));
	}

	@Test
	public void testNoSecretsFoundStatePushRequest() throws InvalidCatapultStateException {
		// Arrange
		CatapultStateEnum state = CatapultStateEnum.NO_SECRETS_FOUND;

		// Act
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(state);

		// Assert
		assertThat("catapultAdapter must be instance of CatapultOpenShiftProjectAdapter when given " + CatapultStateEnum.NO_SECRETS_FOUND + " state", catapultAdapter, instanceOf(CatapultOpenShiftSecretsAdapter.class));
	}

	@Test
	public void testNoConfigMapsFoundStatePushRequest() throws InvalidCatapultStateException {
		// Arrange
		CatapultStateEnum state = CatapultStateEnum.NO_CONFIGMAPS_FOUND;

		// Act
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(state);

		// Assert
		assertThat("catapultAdapter must be instance of CatapultOpenShiftProjectAdapter when given " + CatapultStateEnum.NO_CONFIGMAPS_FOUND + " state", catapultAdapter, instanceOf(CatapultTemplateAdapter.class));
	}

	@Test
	public void testSecretsUpdatedStatePushRequest() throws InvalidCatapultStateException {
		// Arrange
		CatapultStateEnum state = CatapultStateEnum.SECRETS_UPDATED;

		// Act
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(state);

		// Assert
		assertThat("catapultAdapter must be instance of CatapultOpenShiftProjectAdapter when given " + CatapultStateEnum.SECRETS_UPDATED + " state", catapultAdapter, instanceOf(CatapultTemplateAdapter.class));
	}

	@Test
	public void testConfigMapsUpdatedStatePushRequest() throws InvalidCatapultStateException {
		// Arrange
		CatapultStateEnum state = CatapultStateEnum.CONFIGMAPS_UPDATED;

		// Act
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(state);

		// Assert
		assertThat("catapultAdapter must be instance of CatapultOpenShiftProjectAdapter when given " + CatapultStateEnum.CONFIGMAPS_UPDATED + " state", catapultAdapter, instanceOf(CatapultTemplateAdapter.class));
	}

	@Test
	public void testCatapultDoneStatePushRequest() throws InvalidCatapultStateException {
		// Arrange
		CatapultStateEnum state = CatapultStateEnum.CATAPULT_DONE;

		// Act
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(state);

		// Assert
		assertThat("catapultAdapter must be instance of CatapultOpenShiftProjectAdapter when given " + CatapultStateEnum.CATAPULT_DONE + " state", catapultAdapter, instanceOf(CatapultDoneStateAdapter.class));
	}

}
