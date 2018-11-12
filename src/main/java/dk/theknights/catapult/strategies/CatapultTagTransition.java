package dk.theknights.catapult.strategies;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;

/**
 * Created by Ole Gregersen (ole.gregersen@dsg.dk) on 8/30/18.
 */
public class CatapultTagTransition implements Transition {

	/**
	 * Do transitions for release states.
	 *
	 * @param context Current catapult context
	 * @return next catapult state
	 */
	public CatapultStateEnum next(final CatapultContext context) {
		switch (context.getCatapultState()) {
			case INITIAL:
				if (context.getOpenShiftProject() != null) {
					return CatapultStateEnum.RELEASE_PROJECT_FOUND;
				} else {
					return CatapultStateEnum.RELEASE_PROJECT_NOT_FOUND;
				}
			case RELEASE_PROJECT_FOUND:
				return CatapultStateEnum.INITIAL;
			case RELEASE_PROJECT_NOT_FOUND:
				if (context.getCatapultTemplate() != null) {
					return CatapultStateEnum.CATAPULT_TEMPLATE_FOUND;
				} else {
					return CatapultStateEnum.CATAPULT_TEMPLATE_NOT_FOUND;
				}
			default:
				return null;
		}
	}

}
