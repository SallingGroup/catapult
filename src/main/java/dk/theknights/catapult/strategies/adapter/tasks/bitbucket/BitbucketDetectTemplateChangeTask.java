package dk.theknights.catapult.strategies.adapter.tasks.bitbucket;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.bitbucket.BitbucketAPI;
import dk.theknights.catapult.config.CatapultConfig;
import dk.theknights.catapult.model.webhook.AbstractWebHook;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultAdapterTask;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Ole Gregersen (ole.gregersen@sallinggruop.com) on 8/29/18.
 */
public class BitbucketDetectTemplateChangeTask implements CatapultAdapterTask {

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String CATAPULT_TEMPLATE_FILE = "openshift/templates/catapult.json";

	/**
	 * Accepts state OPENSHIFT_PROJECT_FOUND
	 * @param state Current catapult state
	 * @return true if state is OPENSHIFT_PROJECT_FOUND, otherwise false
	 */
	@Override
	public boolean accept(final CatapultStateEnum state) {
		if (state.equals(CatapultStateEnum.OPENSHIFT_PROJECT_FOUND)) {
			return true;
		}
		return false;
	}

	/**
	 * Detect if the template on Bitbucket has changed by downloading the commit and look for the catapult.json file in the file list.
	 * @param context Current Catapult context
	 */
	@Override
	public void perform(final CatapultContext context) {
		AbstractWebHook webhook = context.getWebhook();

		String changesUrl = webhook.getRepositoryUrl() + "/commits/" + webhook.getCommitId() + "/raw";
		try {
			BitbucketAPI bitbucketAPI = getBitbucketAPI(context.getCatapultConfig());
			String changes = bitbucketAPI.getRawCommit(changesUrl);

			if (changes.indexOf(CATAPULT_TEMPLATE_FILE) > -1) {
				context.setCatapultTemplateChanged(true);
			} else {
				context.setCatapultTemplateChanged(false);
			}
			logger.info("Template changed:" + context.isCatapultTemplateChanged());
		} catch (IOException e) {
			logger.error("error", e);
			context.setCatapultTemplateChanged(false);
		}
	}

	protected BitbucketAPI getBitbucketAPI(final CatapultConfig configuration) {
		return new BitbucketAPI(configuration);
	}

}
