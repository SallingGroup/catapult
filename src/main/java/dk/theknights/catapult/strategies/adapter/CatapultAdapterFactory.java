package dk.theknights.catapult.strategies.adapter;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.model.webhook.AbstractWebHook;
import dk.theknights.catapult.model.webhook.RequestTypeEnum;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import dk.theknights.catapult.strategies.state.InvalidCatapultStateException;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/7/18.
 */
public class CatapultAdapterFactory {

	private static CatapultAdapterFactory instance = null;

	/**
	 * Constructor that creates the static factory instance.
	 *
	 * @return Factory for creating adapters
	 */
	public static synchronized CatapultAdapterFactory getInstance() {
		if (instance == null) {
			instance = new CatapultAdapterFactory();
		}
		return instance;
	}

	/**
	 * Create a CatapultAdapter that accepts the current Catapult context. Only one adapter can be created for any given state.
	 *
	 * @param context Current Catapult context
	 * @return CatapultAdapter that applies to the current state.
	 * @throws InvalidCatapultStateException
	 */
	public CatapultAdapter create(final CatapultContext context) throws InvalidCatapultStateException {
		AbstractWebHook webhook = context.getWebhook();
		if (webhook == null) {
			throw new InvalidCatapultStateException();
		}
		switch (context.getCatapultState()) {
			case INITIAL:
				if (webhook.getRequestType().equals(RequestTypeEnum.PUSH_REQUEST)) {
					return new CatapultInitialStateAdapter();
				} else if (webhook.getRequestType().equals(RequestTypeEnum.PULL_REQUEST)) {
					return new PullRequestAdapter();
				} else if (webhook.getRequestType().equals(RequestTypeEnum.TAG_REQUEST)) {
					return new TagAdapter();
				} else {
					throw new InvalidCatapultStateException();
				}
			case OPENSHIFT_PROJECT_FOUND:
			case OPENSHIFT_PROJECT_NOT_FOUND:
			case CATAPULT_TEMPLATE_FOUND:
			case OPENSHIFT_PROJECT_DELETED:
				if (webhook.getRequestType().equals(RequestTypeEnum.PULL_REQUEST)) {
					return new PullRequestAdapter();
				} else {
					return create(context.getCatapultState());
				}
			case OPENSHIFT_PROJECT_CREATED:
			case RELEASE_PROJECT_NOT_FOUND:
			case RELEASE_PROJECT_FOUND:
				if (webhook.getRequestType().equals(RequestTypeEnum.TAG_REQUEST)) {
					return new TagAdapter();
				} else {
					return create(context.getCatapultState());
				}
			default:
				return create(context.getCatapultState());
		}
	}

	/**
	 * Create an instance of a CatapultAdapter that is suitable for the current state.
	 * Only one adapter can be created for any given state.
	 *
	 * @param state Current Catapult state
	 * @return CatapultAdapter that applies to the current state.
	 * @throws InvalidCatapultStateException
	 */
	@SuppressFBWarnings("STYLE")
	public CatapultAdapter create(final CatapultStateEnum state) throws InvalidCatapultStateException {
		switch (state) {
			case INITIAL:
				return new CatapultInitialStateAdapter();
			case CATAPULT_TEMPLATE_NOT_FOUND:
			case CATAPULT_TEMPLATE_FOUND:
			case OPENSHIFT_PROJECT_FOUND:
			case OPENSHIFT_PROJECT_NOT_FOUND:
			case OPENSHIFT_PROJECT_CREATED:
				return new CatapultOpenShiftProjectAdapter();
			case POLICY_BINDINGS_UPDATED:
			case NO_SECRETS_FOUND:
				return new CatapultOpenShiftSecretsAdapter();
			case CATAPULT_TEMPLATE_NOT_CHANGED:
			case CATAPULT_TEMPLATE_CHANGED:
			case SECRETS_UPDATED:
			case NO_CONFIGMAPS_FOUND:
			case CONFIGMAPS_UPDATED:
			case CATAPULT_TEMPLATE_PROCESSED:
			case CATAPULT_TEMPLATE_PROCESS_ERROR:
				return new CatapultTemplateAdapter();
			case CATAPULT_DONE:
				return new CatapultDoneStateAdapter();
			default:
				throw new InvalidCatapultStateException();
		}
	}
}
