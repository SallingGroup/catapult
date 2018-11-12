package dk.theknights.catapult.strategies;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;

/**
 * Created by Ole Gregersen (ole.gregersen@dsg.dk) on 8/30/18.
 */
public class CatapultInitialTransition implements Transition {

	/**
	 * Do transition from initial state to the next one.
	 *
	 * @param context Current catapult context
	 * @return next catapult state
	 */
	public CatapultStateEnum next(final CatapultContext context) {
		switch (context.getCatapultState()) {
			case INITIAL:
				if (context.getCatapultTemplate() != null) {
					return CatapultStateEnum.CATAPULT_TEMPLATE_FOUND;
				}
				return CatapultStateEnum.CATAPULT_TEMPLATE_NOT_FOUND;
			default:
				return null;
		}
	}

}
