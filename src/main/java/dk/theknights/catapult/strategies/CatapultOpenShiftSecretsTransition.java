package dk.theknights.catapult.strategies;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * This Transition implements OpenShift Secrets transitions.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 4/24/18.
 */
public class CatapultOpenShiftSecretsTransition implements Transition {

	/**
	 * Do transition from current catapult secrets state to the next one.
	 *
	 * @param context Current catapult context
	 * @return Next Catapult secret state
	 */
	@SuppressFBWarnings("DB_DUPLICATE_SWITCH_CLAUSES")
	public CatapultStateEnum next(final CatapultContext context) {
		switch (context.getCatapultState()) {
			case NO_SECRETS_FOUND:
				if (context.getConfigMaps().isEmpty()) {
					return CatapultStateEnum.NO_CONFIGMAPS_FOUND;
				} else {
					return CatapultStateEnum.CONFIGMAPS_UPDATED;
				}
			case SECRETS_UPDATED:
				if (context.getConfigMaps().isEmpty()) {
					return CatapultStateEnum.NO_CONFIGMAPS_FOUND;
				} else {
					return CatapultStateEnum.CONFIGMAPS_UPDATED;
				}
			default:
				return null;
		}
	}

}
