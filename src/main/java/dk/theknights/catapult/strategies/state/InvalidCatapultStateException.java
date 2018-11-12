package dk.theknights.catapult.strategies.state;

import dk.theknights.catapult.CatapultException;

/**
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/6/18.
 */
public class InvalidCatapultStateException extends CatapultException {

	public InvalidCatapultStateException() {
		super("Invalid state exception.");
	}

	public InvalidCatapultStateException(final Exception exception) {
		super(exception);
	}

	public InvalidCatapultStateException(final String message) {
		super(message);
	}

}
