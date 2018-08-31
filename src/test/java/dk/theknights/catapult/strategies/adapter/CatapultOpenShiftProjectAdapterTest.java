package dk.theknights.catapult.strategies.adapter;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import dk.theknights.catapult.strategies.state.InvalidCatapultStateException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/7/18.
 */
public class CatapultOpenShiftProjectAdapterTest {

	CatapultAdapterFactory catapultAdapterFactory;

	@Before
	public void setUp() {
		catapultAdapterFactory = CatapultAdapterFactory.getInstance();
	}

	@Test
	public void testAcceptCatapultTemplateFoundState() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_FOUND);
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(context.getCatapultState());

		// Act
		assertThat("catapultAdapter must be of class CatapultOpenShiftProjectAdapter.", catapultAdapter, instanceOf(CatapultOpenShiftProjectAdapter.class));
		boolean accept = catapultAdapter.accept(context);

		// Assert
		assertTrue("CatapultOpenShiftProjectAdapter must accept CatapultTemplateFoundState", accept);
	}

	@Test
	public void testProcessOpenShiftProjectNotFoundState() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.OPENSHIFT_PROJECT_NOT_FOUND);
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(context.getCatapultState());

		// Act
		assertThat("catapultAdapter must be of class CatapultOpenShiftProjectAdapter.", catapultAdapter, instanceOf(CatapultOpenShiftProjectAdapter.class));
		boolean accept = catapultAdapter.accept(context);

		// Assert
		assertTrue("CatapultOpenShiftProjectAdapter must accept OPENSHIFT_PROJECT_NOT_FOUND", accept);
	}

	@Test
	public void testProcessOpenShiftProjectFoundState() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.OPENSHIFT_PROJECT_FOUND);
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(context.getCatapultState());

		// Act
		assertThat("catapultAdapter must be of class CatapultOpenShiftProjectAdapter.", catapultAdapter, instanceOf(CatapultOpenShiftProjectAdapter.class));
		boolean accept = catapultAdapter.accept(context);

		// Assert
		assertTrue("CatapultOpenShiftProjectAdapter must accept OPENSHIFT_PROJECT_FOUND", accept);
	}

	@Test
	public void testProcessOpenShiftProjectCreatedState() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.OPENSHIFT_PROJECT_CREATED);
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(context.getCatapultState());

		// Act
		assertThat("catapultAdapter must be of class CatapultOpenShiftProjectAdapter.", catapultAdapter, instanceOf(CatapultOpenShiftProjectAdapter.class));
		boolean accept = catapultAdapter.accept(context);

		// Assert
		assertTrue("CatapultOpenShiftProjectAdapter must accept OPENSHIFT_PROJECT_CREATED", accept);
	}
}
