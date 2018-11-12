package dk.theknights.catapult.strategies;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.CatapultTemplate;
import dk.theknights.catapult.strategies.adapter.CatapultAdapterFactory;
import dk.theknights.catapult.strategies.adapter.TagAdapter;
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
public class TagStrategyTest {

	CatapultStrategy strategy;
	CatapultContext context;

	@Before
	public void setUp() {
		strategy = new CatapultStrategy();
		strategy.setTransition(new TagTransition());
		context = new CatapultContext();
		context.setWebhook(new StubbedBitbucketWebhook());
	}

	@Test
	public void testExecuteTransitionsFinish() throws InvalidCatapultStateException {
		// Arrange
		CatapultFetchBitbucketTemplateTask templateTask = Mockito.mock(CatapultFetchBitbucketTemplateTask.class);
		when(templateTask.getCatapultTemplate(ArgumentMatchers.anyString())).thenReturn(new CatapultTemplate("Fake template"));
		CatapultAdapterFactory factory = Mockito.mock(CatapultAdapterFactory.class);
		TagAdapter tagAdapter = Mockito.mock(TagAdapter.class);
		when(factory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(tagAdapter);
		context.setCatapultAdapterFactory(factory);
		context.setCatapultState(CatapultStateEnum.INITIAL);

		// Act
		strategy.execute(context);

		// Assert
		assertEquals("TagStrategy not finishing with correct state.", context.getCatapultState(), CatapultStateEnum.CATAPULT_DONE);
	}

	@Test
	public void testExecuteAdapterAcceptInitialState() throws Exception {
		// Arrange
		TagAdapter adapter = new TagAdapter();
		TagAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		doReturn(new ArrayList<>()).when(spyAdapter).getTasks();
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.INITIAL);

		// Act
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(3)).process(context);
	}

	@Test
	public void testExecuteAdapterAcceptReleaseProjectFoundState() throws Exception {
		// Arrange
		TagAdapter adapter = new TagAdapter();
		TagAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		doReturn(new ArrayList<>()).when(spyAdapter).getTasks();
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.RELEASE_PROJECT_FOUND);

		// Act
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(4)).process(context);
	}

	@Test
	public void testExecuteAdapterAcceptReleaseProjectNotFoundState() throws Exception {
		// Arrange
		TagAdapter adapter = new TagAdapter();
		TagAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.RELEASE_PROJECT_NOT_FOUND);

		// Act
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(2)).process(context);
	}

	@Test
	public void testExecuteAdapterAcceptCatapultTemplateNotFoundState() throws Exception {
		// Arrange
		TagAdapter adapter = new TagAdapter();
		TagAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_NOT_FOUND);

		// Act
		strategy.execute(context);

		// Assert
		verify(spyAdapter).process(context);
	}

	@Test
	public void testExecuteAdapterAcceptCatapultTemplateFoundState() throws Exception {
		// Arrange
		TagAdapter adapter = new TagAdapter();
		TagAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		doReturn(new ArrayList<>()).when(spyAdapter).getTasks();
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_FOUND);

		// Act
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(2)).process(context);
	}

	@Test
	public void testExecuteAdapterAcceptOpenShiftProjectFoundState() throws Exception {
		// Arrange
		TagAdapter adapter = new TagAdapter();
		TagAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.OPENSHIFT_PROJECT_FOUND);

		// Act
		strategy.execute(context);

		// Assert
		verify(spyAdapter).process(context);
	}

	@Test
	public void testExecuteAdapterAcceptOpenShiftProjectNotFoundState() throws Exception {
		// Arrange
		TagAdapter adapter = new TagAdapter();
		TagAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		doReturn(new ArrayList<>()).when(spyAdapter).getTasks();
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.OPENSHIFT_PROJECT_NOT_FOUND);

		// Act
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(1)).process(context);
	}

	@Test
	public void testExecuteAdapterAcceptCatapultTemplateChangedState() throws Exception {
		// Arrange
		TagAdapter adapter = new TagAdapter();
		TagAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_CHANGED);

		// Act
		strategy.execute(context);

		// Assert
		verify(spyAdapter).process(context);
	}

	@Test
	public void testExecuteAdapterAcceptCatapultTemplateNotChangedState() throws Exception {
		// Arrange
		TagAdapter adapter = new TagAdapter();
		TagAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_NOT_CHANGED);

		// Act
		strategy.execute(context);

		// Assert
		verify(spyAdapter).process(context);
	}

	@Test
	public void testExecuteAdapterAcceptOpenShiftProjectCreatedState() throws Exception {
		// Arrange
		TagAdapter adapter = new TagAdapter();
		TagAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		doReturn(new ArrayList<>()).when(spyAdapter).getTasks();
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.OPENSHIFT_PROJECT_CREATED);

		// Act
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(4)).process(context);
	}

	@Test
	public void testExecuteAdapterAcceptPolicyBindingsUpdatedState() throws Exception {
		// Arrange
		TagAdapter adapter = new TagAdapter();
		TagAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.POLICY_BINDINGS_UPDATED);

		// Act
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(3)).process(context);
	}

	@Test
	public void testExecuteAdapterAcceptSecretsUpdatedState() throws Exception {
		// Arrange
		TagAdapter adapter = new TagAdapter();
		TagAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.SECRETS_UPDATED);

		// Act
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(3)).process(context);
	}

	@Test
	public void testExecuteAdapterNoSecretsFoundState() throws Exception {
		// Arrange
		TagAdapter adapter = new TagAdapter();
		TagAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.NO_SECRETS_FOUND);

		// Act
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(2)).process(context);
	}

	@Test
	public void testExecuteAdapterConfigMapsUpdatedState() throws Exception {
		// Arrange
		TagAdapter adapter = new TagAdapter();
		TagAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.CONFIGMAPS_UPDATED);

		// Act
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(2)).process(context);
	}

	@Test
	public void testExecuteAdapterNoConfigMapsFoundState() throws Exception {
		// Arrange
		TagAdapter adapter = new TagAdapter();
		TagAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.NO_CONFIGMAPS_FOUND);

		// Act
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(2)).process(context);
	}

	@Test
	public void testExecuteAdapterCatapultTemplateProcessedState() throws Exception {
		// Arrange
		TagAdapter adapter = new TagAdapter();
		TagAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_PROCESSED);

		// Act
		strategy.execute(context);

		// Assert
		verify(spyAdapter).process(context);
	}

	@Test
	public void testExecuteAdapterCatapultTemplateProcessErrorState() throws Exception {
		// Arrange
		TagAdapter adapter = new TagAdapter();
		TagAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_PROCESS_ERROR);

		// Act
		strategy.execute(context);

		// Assert
		verify(spyAdapter).process(context);
	}

	@Test
	public void testExecuteAdapterNotAccept() throws Exception {
		// Arrange
		TagAdapter adapter = new TagAdapter();
		TagAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory adapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		when(adapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		context.setCatapultAdapterFactory(adapterFactory);
		context.setCatapultState(CatapultStateEnum.CATAPULT_DONE);

		// Act
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(0)).process(context);
	}

}
