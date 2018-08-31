package dk.theknights.catapult.strategies;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import dk.theknights.catapult.strategies.state.InvalidCatapultStateException;

import java.util.ArrayList;
import java.util.List;

/**
 * This PushTransition defines the states of the PushStrategy and how to do the transition from the current state to another.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 4/24/18.
 */
public class PushTransition implements Transition {

	private List<Transition> transitions = new ArrayList<Transition>();

	/**
	 * Default constructor for push transition that initializes transitions for a push strategy.
	 */
	public PushTransition() {
		transitions.add(new CatapultInitialTransition());
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
	 * @return Next Catapult state
	 * @throws InvalidCatapultStateException
	 */
	public CatapultStateEnum next(final CatapultContext context) throws InvalidCatapultStateException {
		for (Transition transition: transitions) {
			CatapultStateEnum newState = transition.next(context);
			if (newState != null) {
				return newState;
			}
		}
		return null;
	}

}
