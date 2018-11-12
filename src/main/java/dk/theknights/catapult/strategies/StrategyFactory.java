package dk.theknights.catapult.strategies;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.model.webhook.AbstractWebHook;
import dk.theknights.catapult.strategies.state.InvalidCatapultStateException;

/**
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 9/6/18.
 */
public class StrategyFactory {

	private static StrategyFactory instance = null;

	public static synchronized StrategyFactory getInstance() {
		if (instance == null) {
			instance = new StrategyFactory();
		}
		return instance;
	}

	/**
	 *
	 * @param context
	 * @return
	 * @throws InvalidCatapultStateException
	 */
	public static CatapultStrategy create(final CatapultContext context) throws InvalidCatapultStateException {
		AbstractWebHook webhook = context.getWebhook();
		if (webhook == null) {
			throw new InvalidCatapultStateException();
		}
		switch (context.getWebhook().getRequestType()) {
			case PUSH_REQUEST:
				CatapultStrategy pushStrategy = new CatapultStrategy();
				pushStrategy.setTransition(new PushTransition());
				return pushStrategy;
			case PULL_REQUEST:
				CatapultStrategy pullrequestStrategy = new CatapultStrategy();
				pullrequestStrategy.setTransition(new PullRequestTransition());
				return pullrequestStrategy;
			case TAG_REQUEST:
				CatapultStrategy tagStrategy = new CatapultStrategy();
				tagStrategy.setTransition(new TagTransition());
				return tagStrategy;
			default:
				throw new InvalidCatapultStateException();
		}
	}
}
