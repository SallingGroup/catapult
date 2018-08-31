package dk.theknights.catapult.strategies;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;

/**
 * This Transition implements OpenShift Policy Bindings transitions.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 4/24/18.
 */
public class CatapultOpenShiftPolicyBindingsTransition implements Transition {

	/**
	 * Do transition from current catapult secrets state to the next one.
	 *
	 * @param context Current catapult context
	 * @return Next Catapult state
	 */
	public CatapultStateEnum next(final CatapultContext context) {
		switch (context.getCatapultState()) {
			case POLICY_BINDINGS_UPDATED:
				if (context.getSecrets().isEmpty()) {
					return CatapultStateEnum.NO_SECRETS_FOUND;
				} else {
					return CatapultStateEnum.SECRETS_UPDATED;
				}
			default:
				return null;
		}
	}

}
