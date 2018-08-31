package dk.theknights.catapult.strategies;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import dk.theknights.catapult.strategies.state.InvalidCatapultStateException;

import java.util.ArrayList;
import java.util.List;

/**
 * This ReleaseTransition defines the states of the ReleaseStrategy and how to do the transition from one state to another.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 4/19/18.
 */
public class ReleaseTransition implements Transition {

	private List<Transition> transitions = new ArrayList<Transition>();

	/**
	 * Default constructor for release transition that initializes transitions for a release request.
	 */
	public ReleaseTransition() {
		transitions.add(new CatapultReleaseTransition());
		transitions.add(new CatapultTemplateTransition());
		transitions.add(new CatapultOpenShiftProjectTransition());
		transitions.add(new CatapultOpenShiftPolicyBindingsTransition());
		transitions.add(new CatapultOpenShiftSecretsTransition());
		transitions.add(new CatapultOpenShiftConfigMapsTransition());
	}

	/**
	 * Do transition from current catapult state to the next one.
	 *
	 * @param context Current catapult context
	 * @return New Catapult state
	 * @throws InvalidCatapultStateException
	 */
	public CatapultStateEnum next(final CatapultContext context) throws InvalidCatapultStateException {
		for (Transition transition: transitions) {
			CatapultStateEnum newstate = transition.next(context);
			if (newstate != null) {
				return newstate;
			}
		}
		return null;
	}

}
