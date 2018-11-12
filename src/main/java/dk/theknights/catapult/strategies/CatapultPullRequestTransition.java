package dk.theknights.catapult.strategies;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;

/**
 * Created by Ole Gregersen (ole.gregersen@dsg.dk) on 8/30/18.
 */
public class CatapultPullRequestTransition implements Transition {

	/**
	 * Do transition from the current catapult template states to the next one.
	 *
	 * @param context Current catapult context
	 * @return next catapult state
	 */
	public CatapultStateEnum next(final CatapultContext context) {
		switch (context.getCatapultState()) {
			case CATAPULT_TEMPLATE_FOUND:
				if (context.getOpenShiftProject() != null) {
					return CatapultStateEnum.OPENSHIFT_PROJECT_FOUND;
				}
				return CatapultStateEnum.OPENSHIFT_PROJECT_NOT_FOUND;
			case CATAPULT_TEMPLATE_NOT_FOUND:
				return CatapultStateEnum.CATAPULT_DONE;
			case OPENSHIFT_PROJECT_FOUND:
				if (context.getOpenShiftProject() == null) {
					return CatapultStateEnum.OPENSHIFT_PROJECT_DELETED;
				}
			case OPENSHIFT_PROJECT_NOT_FOUND:
				return CatapultStateEnum.CATAPULT_DONE;
			case OPENSHIFT_PROJECT_DELETED:
				return CatapultStateEnum.CATAPULT_DONE;
			default:
				return null;
		}
	}

}
