package dk.theknights.catapult.strategies.adapter.tasks;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;

/**
 * Apply Catapult template changes to OpenShift project.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class CatapultProcessTemplateChangeTask implements CatapultAdapterTask {

	/**
	 * Accepts no states
	 * @param state Current catapult state
	 * @return false
	 */
	@Override
	public boolean accept(final CatapultStateEnum state) {
		return false;
	}

	/**
	 * Does nothing
	 * @param context Current Catapult context
	 */
	@Override
	public void perform(final CatapultContext context) {

	}

}
