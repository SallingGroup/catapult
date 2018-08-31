package dk.theknights.catapult.strategies.adapter.tasks;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.CatapultException;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;

/**
 * All Catapult tasks must implement this interface.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public interface CatapultAdapterTask {

	/**
	 * Task only accept specific Catapult states.
	 *
	 * @param state Current catapult state
	 * @return true if current state is in tasks valid state list, otherwise false
	 */
	boolean accept(CatapultStateEnum state);

	/**
	 * Execute task with current Catapult context
	 *
	 * @param context Current Catapult context
	 * @throws CatapultException
	 */
	void perform(CatapultContext context) throws CatapultException;

}
