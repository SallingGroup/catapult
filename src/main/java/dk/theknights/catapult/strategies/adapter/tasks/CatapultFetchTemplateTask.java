package dk.theknights.catapult.strategies.adapter.tasks;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.adapter.tasks.bitbucket.CatapultFetchBitbucketTemplateTask;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Task will retrieve the Catapult template from source repository.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class CatapultFetchTemplateTask implements CatapultAdapterTask {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Accepts INITIAL state.
	 *
	 * @param state Current catapult state
	 * @return true if state is INITIAL, otherwise false
	 */
	@Override
	public boolean accept(final CatapultStateEnum state) {
		if (state.equals(CatapultStateEnum.INITIAL)) {
			return true;
		}
		return false;
	}

	/**
	 * Based on repository type Catapult template will be fetched from source repository. Context will be updated with this template.
	 *
	 * @param context Current Catapult context
	 */
	public void perform(final CatapultContext context) {
		switch (context.getRepositoryType()) {
			case BITBUCKET:
				CatapultFetchBitbucketTemplateTask bitbuckettask = new CatapultFetchBitbucketTemplateTask();
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
