package dk.theknights.catapult.strategies.adapter;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.state.InvalidCatapultStateException;

/**
 * This adapter holds tasks that need to be processed on the done state.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class CatapultDoneStateAdapter implements CatapultAdapter {

	/**
	 * There are no tasks in this adapter so there is no reason to accept any states.
	 *
	 * @param context Current catapult context
	 * @return Will always return false
	 */
	@Override
	public boolean accept(final CatapultContext context) {
		return false;
	}

	/**
	 * This method will activate tasks that need to execute on done state.
	 *
	 * @param context Current Catapult context
	 * @throws InvalidCatapultStateException
	 */
	@Override
	public void process(final CatapultContext context) throws InvalidCatapultStateException {

	}

}
