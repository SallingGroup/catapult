package dk.theknights.catapult.strategies.adapter.tasks.bitbucket;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.CatapultTemplate;
import dk.theknights.catapult.bitbucket.BitbucketAPI;
import dk.theknights.catapult.config.CatapultConfig;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultAdapterTask;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * This task will use the Bitbucket API to fetch the json template from source repository. If successful Catapult context is updated.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class CatapultFetchBitbucketTemplateTask implements CatapultAdapterTask {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	public static final String CATAPULT_FILE = "/openshift/templates/catapult.json";

	/**
	 * Accept state INITIAL
	 * @param state Current catapult state
	 * @return true if current state is INITIAL, otherwise false
	 */
	@Override
	public boolean accept(final CatapultStateEnum state) {
		if (state.equals(CatapultStateEnum.INITIAL)) {
			return true;
		}
		return false;
	}

	/**
	 * Fetch the Catapult template from bitbucket with a raw commit download.
	 *
	 * @param context Current Catapult context
	 */
	public void perform(final CatapultContext context) {
		String url = context.getWebhook().getRepositoryUrl();
		url += "/raw/" + context.getWebhook().getCommitId() + CATAPULT_FILE;

		try {
			BitbucketAPI bitbucketAPI = getBitbucketAPI(context.getCatapultConfig());
			context.setCatapultTemplate(getCatapultTemplate(bitbucketAPI.getRawCommit(url)));
		} catch (IOException e) {
			logger.error("Need to do something about this try/catch", e);
			context.setCatapultTemplate(null);
		}
	}

	/**
	 * Get Catapult template as a wrapped json template in a CatapultTemplate class.
	 *
	 * @param template json String downloaded from bitbucket
	 * @return json template wrapped in the CatapultTemplate object
	 */
	public CatapultTemplate getCatapultTemplate(final String template) {
		return new CatapultTemplate(template);
	}

	/**
	 * Get the bitbucket api service.
	 *
	 * @param configuration Catapult configuration
	 * @return New bitbucket api service object.
	 */
	public BitbucketAPI getBitbucketAPI(final CatapultConfig configuration) {
		return new BitbucketAPI(configuration);
	}

}
