package dk.theknights.catapult.strategies.adapter.tasks;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;

/**
 * Validate the Catapult configuration. The Catapult will not function correctly if this task does not validate all configurations as working.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class CatapultValidateConfigTask implements CatapultAdapterTask {

	/**
	 * Accept state INITIAL
	 * @param state Current catapult state
	 * @return true if state is INITIAL, otherwise false
	 */
	@Override
	public boolean accept(final CatapultStateEnum state) {
		if (state.equals(CatapultStateEnum.INITIAL)) {
			return true;
		}
		return false;
	}

	/**
	 * Does nothing.
	 *
	 * @param context Current Catapult context
	 */
	public void perform(final CatapultContext context) {
	}

}
