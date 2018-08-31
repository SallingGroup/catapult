package dk.theknights.catapult.strategies;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import dk.theknights.catapult.strategies.state.InvalidCatapultStateException;

/**
 * This Transition class implements template transitions.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 4/24/18.
 */
public class CatapultTemplateTransition implements Transition {

	/**
	 * Do transition from current catapult template state to the next one.
	 *
	 * @param context Current catapult context
	 * @return Next template state
	 * @throws InvalidCatapultStateException
	 */
	public CatapultStateEnum next(final CatapultContext context) {

		switch (context.getCatapultState()) {
			case CATAPULT_TEMPLATE_NOT_FOUND:
				return CatapultStateEnum.CATAPULT_DONE;
			case CATAPULT_TEMPLATE_FOUND:
				if (context.getOpenShiftProject() == null) {
					return CatapultStateEnum.OPENSHIFT_PROJECT_NOT_FOUND;
				} else {
					return CatapultStateEnum.OPENSHIFT_PROJECT_FOUND;
				}
			case CATAPULT_TEMPLATE_CHANGED:
				return CatapultStateEnum.CATAPULT_DONE;
			case CATAPULT_TEMPLATE_NOT_CHANGED:
				return CatapultStateEnum.CATAPULT_DONE;
			case CATAPULT_TEMPLATE_PROCESSED:
				return CatapultStateEnum.CATAPULT_DONE;
			case CATAPULT_TEMPLATE_PROCESS_ERROR:
				return CatapultStateEnum.CATAPULT_DONE;
			default:
				return null;
		}
	}
}
