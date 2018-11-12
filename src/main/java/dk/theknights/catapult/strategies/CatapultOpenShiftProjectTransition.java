package dk.theknights.catapult.strategies;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;

/**
 * This transition class implements OpenShift Project transitions.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 4/24/18.
 */
public class CatapultOpenShiftProjectTransition implements Transition {

	/**
	 * Do transition from current catapult secrets state to the next one.
	 *
	 * @param context Current catapult context
	 * @return Next Catapult state
	 */
	public CatapultStateEnum next(final CatapultContext context) {
		switch (context.getCatapultState()) {
			case OPENSHIFT_PROJECT_FOUND:
				if (context.isCatapultTemplateChanged()) {
					return CatapultStateEnum.CATAPULT_TEMPLATE_CHANGED;
				} else {
					return CatapultStateEnum.CATAPULT_TEMPLATE_NOT_CHANGED;
				}
			case OPENSHIFT_PROJECT_NOT_FOUND:
				if (context.getOpenShiftProject() == null) {
					return CatapultStateEnum.CATAPULT_DONE;
				} else {
					return CatapultStateEnum.OPENSHIFT_PROJECT_CREATED;
				}
			case OPENSHIFT_PROJECT_CREATED:
				return CatapultStateEnum.POLICY_BINDINGS_UPDATED;
			default:
				return null;
		}
	}

}
