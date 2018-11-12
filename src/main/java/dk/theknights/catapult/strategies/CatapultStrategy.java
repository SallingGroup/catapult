package dk.theknights.catapult.strategies;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.adapter.CatapultAdapter;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import dk.theknights.catapult.strategies.state.InvalidCatapultStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 9/6/18.
 */
public class CatapultStrategy {

	protected Transition transition;

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public void setTransition(final Transition transition) {
		this.transition = transition;
	}

	/**
	 * Start executing the state machine.
	 *
	 * @param context New state machine context
	 * @throws InvalidCatapultStateException
	 */
	public void execute(final CatapultContext context) throws InvalidCatapultStateException {
		CatapultStateEnum currentState = context.getCatapultState();
		while (currentState != CatapultStateEnum.CATAPULT_DONE) {
			CatapultAdapter adapter =  context.getCatapultAdapter(context);
			if (adapter.accept(context)) {
				adapter.process(context);
			} else {
				logger.warn(context.getId() + ": There are no adapter for state (" + currentState + ") - some functionality might be missing.");
			}
			currentState = transition.next(context);
			logger.info(context.getId() + ": Changing state from (" + context.getCatapultState() + ") to (" + currentState + ")");
			context.setCatapultState(currentState);
		}
	}
}
