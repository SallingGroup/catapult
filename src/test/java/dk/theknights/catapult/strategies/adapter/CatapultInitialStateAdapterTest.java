package dk.theknights.catapult.strategies.adapter;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultAdapterTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultValidateConfigTask;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import dk.theknights.catapult.strategies.state.InvalidCatapultStateException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class CatapultInitialStateAdapterTest {

	CatapultAdapterFactory catapultAdapterFactory;

	@Before
	public void setUp()	{
		catapultAdapterFactory = CatapultAdapterFactory.getInstance();
	}

	@Test
	public void testAcceptInitialState() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.INITIAL);
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(context.getCatapultState());

		// Act
		assertNotNull("catapultAdapter can not be null.", catapultAdapter);
		boolean accept = catapultAdapter.accept(context);

		// Assert
		assertThat("catapultAdapter must be instance of CatapultInitialStateAdapter when given " + CatapultStateEnum.INITIAL + " state", catapultAdapter, instanceOf(CatapultInitialStateAdapter.class));
		assertTrue("catapultAdapter must accept INITIAL state.", accept);
	}

	@Test
	public void testARejectOtherStateThanInitialState() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.INITIAL);
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(context.getCatapultState());
		context.setCatapultState(CatapultStateEnum.CATAPULT_DONE);

		// Act
		assertNotNull("catapultAdapter can not be null.", catapultAdapter);
		boolean accept = catapultAdapter.accept(context);

		// Assert
		assertThat("catapultAdapter must be instance of CatapultInitialStateAdapter when given " + CatapultStateEnum.INITIAL + " state", catapultAdapter, instanceOf(CatapultInitialStateAdapter.class));
		assertFalse("catapultAdapter may ONLY accept INITIAL state.", accept);
	}

	@Test
	public void testProcessCatapultValidateConfigTask() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.INITIAL);
		context.setCatapultAdapterFactory(catapultAdapterFactory);

		CatapultInitialStateAdapter catapultAdapter = (CatapultInitialStateAdapter) catapultAdapterFactory.create(context.getCatapultState());

		CatapultValidateConfigTask spyTask = Mockito.mock(CatapultValidateConfigTask.class);
		when(spyTask.accept(ArgumentMatchers.any())).thenReturn(true);
		CatapultInitialStateAdapter initialStateAdapter = Mockito.spy(catapultAdapter);
		List<CatapultAdapterTask> taskList = new ArrayList<CatapultAdapterTask>();
		taskList.add(spyTask);
		when(initialStateAdapter.getTasks()).thenReturn(taskList);

		// Act
		initialStateAdapter.process(context);

		// Assert
		verify(spyTask).perform(context);
	}

}
