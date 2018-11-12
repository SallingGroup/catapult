package dk.theknights.catapult.strategies.adapter;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.CatapultTemplate;
import dk.theknights.catapult.strategies.StubbedOpenShiftProject;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import dk.theknights.catapult.strategies.state.InvalidCatapultStateException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/7/18.
 */
public class CatapultTemplateAdapterTest {

	CatapultAdapterFactory catapultAdapterFactory;

	@Before
	public void setUp() {
		catapultAdapterFactory = CatapultAdapterFactory.getInstance();
	}

	@Test
	public void testProcessBadTemplate() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultTemplate(null);
		context.setCatapultState(CatapultStateEnum.INITIAL);
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(context.getCatapultState());

		// Act
		assertNotNull("catapultAdapter can not be null.", catapultAdapter);
		catapultAdapter.process(context);

		// Assert
		assertNull("CatapultContext must have CatapultTemplate that IS null.", context.getCatapultTemplate());
	}

	@Test
	public void testProcessValidTemplate() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultTemplate(new CatapultTemplate(""));
		context.setCatapultState(CatapultStateEnum.INITIAL);
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(context.getCatapultState());

		// Act
		assertNotNull("catapultAdapter can not be null.", catapultAdapter);
		catapultAdapter.process(context);

		// Assert
		assertNotNull("CatapultContext must have CatapultTemplate that is not null.", context.getCatapultTemplate());
	}

	@Test
	public void testAcceptNoTemplateInContext() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultTemplate(null);
		context.setCatapultState(CatapultStateEnum.INITIAL);
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(context.getCatapultState());

		// Act
		assertNotNull("catapultAdapter can not be null.", catapultAdapter);
		boolean accept = catapultAdapter.accept(context);

		// Assert
		assertThat("catapultAdapter must be instance of CatapultTemplateAdapter when given " + CatapultStateEnum.INITIAL + " state", catapultAdapter, instanceOf(CatapultInitialStateAdapter.class));
		assertTrue("CatapultTemplateAdapter must accept context with no template loaded.", accept);
	}

	@Test
	public void testAcceptWithWrongState() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.CATAPULT_DONE);
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(context.getCatapultState());

		// Act
		assertNotNull("catapultAdapter can not be null.", catapultAdapter);
		boolean accept = catapultAdapter.accept(context);

		// Assert
		assertFalse("CatapultTemplateAdapter must NOT accept wrong context.", accept);
	}

	@Test
	public void testAcceptCatapultTemplateChangedState() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_CHANGED);
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(context.getCatapultState());
		context.setOpenShiftProject(new StubbedOpenShiftProject());

		// Act
		assertNotNull("catapultAdapter can not be null.", catapultAdapter);
		boolean accept = catapultAdapter.accept(context);

		// Assert
		assertTrue("CatapultTemplateAdapter must accept CATAPULT_TEMPLATE_CHANGED.", accept);
	}

	@Test
	public void testAcceptCatapultTemplateProcessedState() throws InvalidCatapultStateException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultState(CatapultStateEnum.CATAPULT_TEMPLATE_PROCESSED);
		CatapultAdapter catapultAdapter = catapultAdapterFactory.create(context.getCatapultState());
		context.setOpenShiftProject(new StubbedOpenShiftProject());

		// Act
		assertNotNull("catapultAdapter can not be null.", catapultAdapter);
		boolean accept = catapultAdapter.accept(context);

		// Assert
		assertTrue("CatapultTemplateAdapter must accept CATAPULT_TEMPLATE_PROCESSED.", accept);
	}

}
