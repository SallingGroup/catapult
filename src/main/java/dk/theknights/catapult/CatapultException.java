package dk.theknights.catapult;

/**
 * Exception that is thrown by Catapult when things go wrong.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 11/22/17.
 */
public class CatapultException extends Exception {

	private Exception exception;
	private String message;

	public CatapultException(final Exception exception) {
		this.exception = exception;
	}

	public CatapultException(final String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		if (exception == null) {
			return message;
		} else {
			return exception.getMessage();
		}
	}

}
