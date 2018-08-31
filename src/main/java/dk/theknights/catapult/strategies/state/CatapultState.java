package dk.theknights.catapult.strategies.state;

import dk.theknights.catapult.CatapultContext;

/**
 * Created by Ole Gregersen on 3/6/18.
 */
public interface CatapultState {

	void process(final CatapultContext context);

}
