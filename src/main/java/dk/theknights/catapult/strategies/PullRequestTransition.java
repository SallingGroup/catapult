package dk.theknights.catapult.strategies;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import dk.theknights.catapult.strategies.state.InvalidCatapultStateException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 4/19/18.
 */
public class PullRequestTransition implements Transition {

	private List<Transition> transitions = new ArrayList<Transition>();

	public PullRequestTransition() {
		transitions.add(new CatapultInitialTransition());
		transitions.add(new CatapultPullRequestTransition());
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
