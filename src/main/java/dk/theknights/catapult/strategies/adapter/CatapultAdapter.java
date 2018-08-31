package dk.theknights.catapult.strategies.adapter;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.state.InvalidCatapultStateException;

/**
 * This interface specifies methods that Catapult adapters must implement.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/7/18.
 */
public interface CatapultAdapter {

	/**
	 * This method will determine if an adapter accepts the given Catapult context.
	 *
	 * @param context Current catapult context
	 * @return true if context has what the adapter need to execute its tasks, otherwise it will return false
	 */
	boolean accept(CatapultContext context);

	/**
	 * This method will execute tasks that accept the current Catapult context.
	 *
	 * @param context Current Catapult context
	 * @throws InvalidCatapultStateException
	 */
	void process(CatapultContext context) throws InvalidCatapultStateException;

}
