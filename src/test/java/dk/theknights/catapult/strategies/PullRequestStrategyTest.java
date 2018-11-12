package dk.theknights.catapult.strategies;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.adapter.CatapultAdapterFactory;
import dk.theknights.catapult.strategies.adapter.PullRequestAdapter;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import dk.theknights.catapult.strategies.state.InvalidCatapultStateException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 4/23/18.
 */
public class PullRequestStrategyTest {

	CatapultStrategy strategy;

	@Before
	public void setUp() {
		strategy = new CatapultStrategy();
		strategy.setTransition(new PullRequestTransition());
	}

	@Test
	public void testExecuteTransitionsFinish() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.INITIAL);
		context.setWebhook(new StubbedBitbucketWebhook());
		PullRequestAdapter adapter = new PullRequestAdapter();
		PullRequestAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory mockAdapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		context.setCatapultAdapterFactory(mockAdapterFactory);
		when(mockAdapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		doNothing().when(spyAdapter).process(ArgumentMatchers.any());

		// Act
		strategy.execute(context);

		// Assert
		assertEquals("PullRequestStrategy not finishing with correct state.", context.getCatapultState(), CatapultStateEnum.CATAPULT_DONE);
	}

	@Test
	public void testAdapterAcceptInitialState() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.INITIAL);
		PullRequestAdapter adapter = new PullRequestAdapter();
		PullRequestAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory mockAdapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		context.setCatapultAdapterFactory(mockAdapterFactory);
		when(mockAdapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);

		// Act
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(1)).process(context);
	}

	@Test
	public void testAdapterAcceptOpenShiftProjectNotFoundState() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.OPENSHIFT_PROJECT_NOT_FOUND);
		PullRequestAdapter adapter = new PullRequestAdapter();
		PullRequestAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory mockAdapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		context.setCatapultAdapterFactory(mockAdapterFactory);
		when(mockAdapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);

		// Act
		strategy.execute(context);

		// Assert
		verify(spyAdapter).process(context);
	}

	@Test
	public void testAdapterAcceptOpenShiftProjectFoundState() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.OPENSHIFT_PROJECT_FOUND);
		context.setWebhook(new StubbedBitbucketWebhook());
		PullRequestAdapter adapter = new PullRequestAdapter();
		PullRequestAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory mockAdapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		context.setCatapultAdapterFactory(mockAdapterFactory);
		when(mockAdapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);

		// Act
		strategy.execute(context);

		// Assert
		verify(spyAdapter).process(context);
	}

	@Test
	public void testAdapterNotAcceptOpenShiftProjectDeletedState() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.OPENSHIFT_PROJECT_DELETED);
		PullRequestAdapter adapter = new PullRequestAdapter();
		PullRequestAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory mockAdapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		context.setCatapultAdapterFactory(mockAdapterFactory);
		when(mockAdapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);

		// Act
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(0)).process(context);
	}

	@Test
	public void testAdapterNotAcceptDoneState() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.CATAPULT_DONE);
		PullRequestAdapter adapter = new PullRequestAdapter();
		PullRequestAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory mockAdapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		context.setCatapultAdapterFactory(mockAdapterFactory);
		when(mockAdapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);

		// Act
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(0)).process(context);
	}

}
