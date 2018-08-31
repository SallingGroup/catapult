package dk.theknights.catapult.strategies.adapter.tasks;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.adapter.tasks.bitbucket.BitbucketDetectTemplateChangeTask;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Detect if the Catapult template has changed.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class CatapultDetectTemplateChangeTask implements CatapultAdapterTask {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

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
		switch (context.getRepositoryType()) {
			case BITBUCKET:
				BitbucketDetectTemplateChangeTask bitbuckettask = new BitbucketDetectTemplateChangeTask();
				bitbuckettask.perform(context);
				break;
			case GITLAB:
			case GITHUB:
				logger.warn("Not implemented!");
				break;
			default:
				logger.warn("Unknown repository type <" + context.getRepositoryType() + ">");
		}
	}

}
