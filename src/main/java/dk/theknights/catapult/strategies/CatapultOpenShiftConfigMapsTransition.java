package dk.theknights.catapult.strategies;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * This Transition implements OpenShift configmaps transitions.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 4/24/18.
 */
public class CatapultOpenShiftConfigMapsTransition implements Transition {

	/**
	 * Do transition from current catapult configmaps state to the next one.
	 *
	 * @param context Current catapult context
	 * @return Next Catapult state
	 */
	@SuppressFBWarnings("DB_DUPLICATE_SWITCH_CLAUSES")
	public CatapultStateEnum next(final CatapultContext context) {
		switch (context.getCatapultState()) {
			case CONFIGMAPS_UPDATED:
				if (context.getCatapultTemplate() != null && context.getCatapultTemplate().toString().equals("ERROR")) {
					return CatapultStateEnum.CATAPULT_TEMPLATE_PROCESS_ERROR;
				} else {
					return CatapultStateEnum.CATAPULT_TEMPLATE_PROCESSED;
				}
			case NO_CONFIGMAPS_FOUND:
				if (context.getCatapultTemplate() != null && context.getCatapultTemplate().toString().equals("ERROR")) {
					return CatapultStateEnum.CATAPULT_TEMPLATE_PROCESS_ERROR;
				} else {
					return CatapultStateEnum.CATAPULT_TEMPLATE_PROCESSED;
				}
			default:
				return null;
		}
	}

}
