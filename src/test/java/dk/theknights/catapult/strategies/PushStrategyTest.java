package dk.theknights.catapult.strategies;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.adapter.CatapultAdapterFactory;
import dk.theknights.catapult.strategies.adapter.CatapultInitialStateAdapter;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PushStrategyTest {

	PushStrategy strategy;

	@Before
	public void setUp() {
		strategy = new PushStrategy();
	}

	@Test
	public void testExecuteTransitionsFinish() throws Exception {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.INITIAL);
		context.setWebhook(new StubbedBitbucketWebhook());
		CatapultInitialStateAdapter adapter = new CatapultInitialStateAdapter();
		CatapultInitialStateAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory mockAdapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		context.setCatapultAdapterFactory(mockAdapterFactory);
		when(mockAdapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);
		doNothing().when(spyAdapter).process(ArgumentMatchers.any());

		// Act
		strategy.execute(context);

		// Assert
		assertEquals("PushStrategy not finishing with correct state.", context.getCatapultState(), CatapultStateEnum.CATAPULT_DONE);
	}

	@Test
	public void testExecuteAdapterAccept() throws Exception {
		// Arrange
		CatapultContext context = new CatapultContext();
		CatapultInitialStateAdapter adapter = new CatapultInitialStateAdapter();
		CatapultInitialStateAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory mockAdapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		context.setCatapultAdapterFactory(mockAdapterFactory);
		when(mockAdapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(spyAdapter);

		// Act
		strategy.execute(context);

		// Assert
		verify(spyAdapter).process(context);
	}

	@Test
	public void testExecuteAdapterNotAccept() throws Exception {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.CATAPULT_DONE);
		CatapultInitialStateAdapter adapter = new CatapultInitialStateAdapter();
		CatapultInitialStateAdapter spyAdapter = Mockito.spy(adapter);
		CatapultAdapterFactory mockAdapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		context.setCatapultAdapterFactory(mockAdapterFactory);
		when(mockAdapterFactory.create(ArgumentMatchers.any(CatapultStateEnum.class))).thenReturn(spyAdapter);

		// Act
		strategy.execute(context);

		// Assert
		verify(spyAdapter, Mockito.times(0)).process(context);
		Mockito.doReturn(false).when(spyAdapter).accept(context);
	}

	@Test
	public void testNoAdapter() throws Exception {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.INITIAL);
		CatapultAdapterFactory mockAdapterFactory = Mockito.mock(CatapultAdapterFactory.class);
		CatapultInitialStateAdapter initialAdapter = Mockito.mock(CatapultInitialStateAdapter.class);
		context.setCatapultAdapterFactory(mockAdapterFactory);
		when(mockAdapterFactory.create(ArgumentMatchers.any(CatapultContext.class))).thenReturn(initialAdapter);

		// Act
		strategy.execute(context);

		// Assert

	}

}
