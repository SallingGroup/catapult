package dk.theknights.catapult.strategies;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.CatapultTemplate;
import dk.theknights.catapult.strategies.adapter.CatapultAdapterFactory;
import dk.theknights.catapult.strategies.adapter.ReleaseAdapter;
import dk.theknights.catapult.strategies.adapter.tasks.bitbucket.CatapultFetchBitbucketTemplateTask;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import dk.theknights.catapult.strategies.state.InvalidCatapultStateException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 4/30/18.
 */
public class ReleaseStrategyTest {

	@Before
	public void setup() {

	}

	@Test
	public void testExecuteTransitionsFinish() throws InvalidCatapultStateException {
		// Arrange
		CatapultFetchBitbucketTemplateTask templateTask = Mockito.mock(CatapultFetchBitbucketTemplateTask.class);
		when(templateTask.getCatapultTemplate(ArgumentMatchers.anyString())).thenReturn(new CatapultTemplate("Fake template"));
		CatapultAdapterFactory factory = Mockito.mock(CatapultAdapterFactory.class);
		ReleaseAdapter releaseAdapter = Mockito.mock(ReleaseAdapter.class);
		when(factory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(releaseAdapter);
		CatapultContext context = new CatapultContext();
		context.setCatapultAdapterFactory(factory);
		context.setCatapultState(CatapultStateEnum.INITIAL);
		context.setWebhook(new StubbedBitbucketWebhook());

		// Act
		ReleaseStrategy strategy = new ReleaseStrategy();
		strategy.execute(context);

		// Assert
		assertEquals("ReleaseStrategy not finishing with correct state.", context.getCatapultState(), CatapultStateEnum.CATAPULT_DONE);
	}

	@Test
	public void testExecuteAdapterAcceptInitialState() throws Exception {
		// Arrange
		ReleaseAdapter adapter = new ReleaseAdapter();
		ReleaseAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		doReturn(new ArrayList<>()).when(spyAdapter).getTasks();
		CatapultContext context = new CatapultContext();
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.INITIAL);
		context.setWebhook(new StubbedBitbucketWebhook());

		// Act
		ReleaseStrategy strategy = new ReleaseStrategy();
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(3)).process(context);
	}

	@Test
	public void testExecuteAdapterAcceptReleaseProjectFoundState() throws Exception {
		// Arrange
		ReleaseAdapter adapter = new ReleaseAdapter();
		ReleaseAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		doReturn(new ArrayList<>()).when(spyAdapter).getTasks();
		CatapultContext context = new CatapultContext();
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.RELEASE_PROJECT_FOUND);
		context.setWebhook(new StubbedBitbucketWebhook());

		// Act
		ReleaseStrategy strategy = new ReleaseStrategy();
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(4)).process(context);
	}

	@Test
	public void testExecuteAdapterAcceptReleaseProjectNotFoundState() throws Exception {
		// Arrange
		ReleaseAdapter adapter = new ReleaseAdapter();
		ReleaseAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		CatapultContext context = new CatapultContext();
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.RELEASE_PROJECT_NOT_FOUND);
		context.setWebhook(new StubbedBitbucketWebhook());

		// Act
		ReleaseStrategy strategy = new ReleaseStrategy();
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(2)).process(context);
	}

	@Test
	public void testExecuteAdapterAcceptCatapultTemplateNotFoundState() throws Exception {
		// Arrange
		ReleaseAdapter adapter = new ReleaseAdapter();
		ReleaseAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		CatapultContext context = new CatapultContext();
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_NOT_FOUND);
		context.setWebhook(new StubbedBitbucketWebhook());

		// Act
		ReleaseStrategy strategy = new ReleaseStrategy();
		strategy.execute(context);

		// Assert
		verify(spyAdapter).process(context);
	}

	@Test
	public void testExecuteAdapterAcceptCatapultTemplateFoundState() throws Exception {
		// Arrange
		ReleaseAdapter adapter = new ReleaseAdapter();
		ReleaseAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		doReturn(new ArrayList<>()).when(spyAdapter).getTasks();
		CatapultContext context = new CatapultContext();
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_FOUND);
		context.setWebhook(new StubbedBitbucketWebhook());

		// Act
		ReleaseStrategy strategy = new ReleaseStrategy();
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(6)).process(context);
	}

	@Test
	public void testExecuteAdapterAcceptOpenShiftProjectFoundState() throws Exception {
		// Arrange
		ReleaseAdapter adapter = new ReleaseAdapter();
		ReleaseAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		CatapultContext context = new CatapultContext();
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.OPENSHIFT_PROJECT_FOUND);
		context.setWebhook(new StubbedBitbucketWebhook());

		// Act
		ReleaseStrategy strategy = new ReleaseStrategy();
		strategy.execute(context);

		// Assert
		verify(spyAdapter).process(context);
	}

	@Test
	public void testExecuteAdapterAcceptOpenShiftProjectNotFoundState() throws Exception {
		// Arrange
		ReleaseAdapter adapter = new ReleaseAdapter();
		ReleaseAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		doReturn(new ArrayList<>()).when(spyAdapter).getTasks();
		CatapultContext context = new CatapultContext();
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.OPENSHIFT_PROJECT_NOT_FOUND);
		context.setWebhook(new StubbedBitbucketWebhook());

		// Act
		ReleaseStrategy strategy = new ReleaseStrategy();
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(5)).process(context);
	}

	@Test
	public void testExecuteAdapterAcceptCatapultTemplateChangedState() throws Exception {
		// Arrange
		ReleaseAdapter adapter = new ReleaseAdapter();
		ReleaseAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		CatapultContext context = new CatapultContext();
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_CHANGED);
		context.setWebhook(new StubbedBitbucketWebhook());

		// Act
		ReleaseStrategy strategy = new ReleaseStrategy();
		strategy.execute(context);

		// Assert
		verify(spyAdapter).process(context);
	}

	@Test
	public void testExecuteAdapterAcceptCatapultTemplateNotChangedState() throws Exception {
		// Arrange
		ReleaseAdapter adapter = new ReleaseAdapter();
		ReleaseAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		CatapultContext context = new CatapultContext();
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_NOT_CHANGED);
		context.setWebhook(new StubbedBitbucketWebhook());

		// Act
		ReleaseStrategy strategy = new ReleaseStrategy();
		strategy.execute(context);

		// Assert
		verify(spyAdapter).process(context);
	}

	@Test
	public void testExecuteAdapterAcceptOpenShiftProjectCreatedState() throws Exception {
		// Arrange
		ReleaseAdapter adapter = new ReleaseAdapter();
		ReleaseAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		doReturn(new ArrayList<>()).when(spyAdapter).getTasks();
		CatapultContext context = new CatapultContext();
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.OPENSHIFT_PROJECT_CREATED);
		context.setWebhook(new StubbedBitbucketWebhook());

		// Act
		ReleaseStrategy strategy = new ReleaseStrategy();
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(4)).process(context);
	}

	@Test
	public void testExecuteAdapterAcceptPolicyBindingsUpdatedState() throws Exception {
		// Arrange
		ReleaseAdapter adapter = new ReleaseAdapter();
		ReleaseAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		CatapultContext context = new CatapultContext();
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.POLICY_BINDINGS_UPDATED);
		context.setWebhook(new StubbedBitbucketWebhook());

		// Act
		ReleaseStrategy strategy = new ReleaseStrategy();
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(3)).process(context);
	}

	@Test
	public void testExecuteAdapterAcceptSecretsUpdatedState() throws Exception {
		// Arrange
		ReleaseAdapter adapter = new ReleaseAdapter();
		ReleaseAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		CatapultContext context = new CatapultContext();
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.SECRETS_UPDATED);
		context.setWebhook(new StubbedBitbucketWebhook());

		// Act
		ReleaseStrategy strategy = new ReleaseStrategy();
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(3)).process(context);
	}

	@Test
	public void testExecuteAdapterNoSecretsFoundState() throws Exception {
		// Arrange
		ReleaseAdapter adapter = new ReleaseAdapter();
		ReleaseAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		CatapultContext context = new CatapultContext();
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.NO_SECRETS_FOUND);
		context.setWebhook(new StubbedBitbucketWebhook());

		// Act
		ReleaseStrategy strategy = new ReleaseStrategy();
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(2)).process(context);
	}

	@Test
	public void testExecuteAdapterConfigMapsUpdatedState() throws Exception {
		// Arrange
		ReleaseAdapter adapter = new ReleaseAdapter();
		ReleaseAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		CatapultContext context = new CatapultContext();
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.CONFIGMAPS_UPDATED);
		context.setWebhook(new StubbedBitbucketWebhook());

		// Act
		ReleaseStrategy strategy = new ReleaseStrategy();
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(2)).process(context);
	}

	@Test
	public void testExecuteAdapterNoConfigMapsFoundState() throws Exception {
		// Arrange
		ReleaseAdapter adapter = new ReleaseAdapter();
		ReleaseAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		CatapultContext context = new CatapultContext();
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.NO_CONFIGMAPS_FOUND);
		context.setWebhook(new StubbedBitbucketWebhook());

		// Act
		ReleaseStrategy strategy = new ReleaseStrategy();
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(2)).process(context);
	}

	@Test
	public void testExecuteAdapterCatapultTemplateProcessedState() throws Exception {
		// Arrange
		ReleaseAdapter adapter = new ReleaseAdapter();
		ReleaseAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		CatapultContext context = new CatapultContext();
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_PROCESSED);
		context.setWebhook(new StubbedBitbucketWebhook());

		// Act
		ReleaseStrategy strategy = new ReleaseStrategy();
		strategy.execute(context);

		// Assert
		verify(spyAdapter).process(context);
	}

	@Test
	public void testExecuteAdapterCatapultTemplateProcessErrorState() throws Exception {
		// Arrange
		ReleaseAdapter adapter = new ReleaseAdapter();
		ReleaseAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		CatapultContext context = new CatapultContext();
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_PROCESS_ERROR);
		context.setWebhook(new StubbedBitbucketWebhook());

		// Act
		ReleaseStrategy strategy = new ReleaseStrategy();
		strategy.execute(context);

		// Assert
		verify(spyAdapter).process(context);
	}

	@Test
	public void testExecuteAdapterNotAccept() throws Exception {
		// Arrange
		ReleaseAdapter adapter = new ReleaseAdapter();
		ReleaseAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		CatapultContext context = new CatapultContext();
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.CATAPULT_DONE);
		context.setWebhook(new StubbedBitbucketWebhook());

		// Act
		ReleaseStrategy strategy = new ReleaseStrategy();
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(0)).process(context);
	}

}
