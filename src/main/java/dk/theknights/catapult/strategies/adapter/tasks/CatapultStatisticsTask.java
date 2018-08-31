package dk.theknights.catapult.strategies.adapter.tasks;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;

/**
 * Update the statistics for Catapult usage.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 * */
public class CatapultStatisticsTask implements CatapultAdapterTask {

	/**
	 * Accept no states.
	 *
	 * @param state Current catapult state
	 * @return
	 */
	@Override
	public boolean accept(final CatapultStateEnum state) {
		return false;
	}

	/**
	 * Does nothing.
	 *
	 * @param context Current Catapult context
	 */
	@Override
	public void perform(final CatapultContext context) {

	}

}
