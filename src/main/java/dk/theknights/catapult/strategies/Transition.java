package dk.theknights.catapult.strategies;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import dk.theknights.catapult.strategies.state.InvalidCatapultStateException;

/**
 * This interface defines how to get from one state to the next one.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 4/24/18.
 */
public interface Transition {

	/**
	 * Do transition to next state.
	 *
	 * @param context Current catapult context
	 * @return New catapult state
	 * @throws InvalidCatapultStateException
	 */
	CatapultStateEnum next(CatapultContext context) throws InvalidCatapultStateException;

}
