package dk.theknights.catapult.strategies.adapter.tasks.github;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.config.CatapultConfig;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultAdapterTask;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;

/**
 * This task will get the GitHub source secret and add it to the OpenShift project and the builder service account.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/13/18.
 */
public class CatapultCreateGitHubSourceSecretTask implements CatapultAdapterTask {

	/**
	 * Does not accept any states
	 *
	 * @param state Current catapult state
	 * @return false
	 */
	@Override
	public boolean accept(final CatapultStateEnum state) {
		return false;
	}

	/**
	 * Does nothing yet.
	 *
	 * @param context Current Catapult context
	 */
	@Override
	public void perform(final CatapultContext context) {

	}

	protected CatapultConfig getConfiguration() {
		return null;
	}

}
