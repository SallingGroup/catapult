package dk.theknights.catapult.strategies.adapter.tasks;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.CatapultException;
import dk.theknights.catapult.strategies.adapter.tasks.bitbucket.CatapultCreateBitbucketSourceSecretTask;
import dk.theknights.catapult.strategies.adapter.tasks.github.CatapultCreateGitHubSourceSecretTask;
import dk.theknights.catapult.strategies.adapter.tasks.gitlab.CatapultCreateGitLabSourceSecretTask;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;

/**
 * This task will create the repository source secret and add it to the builder service account in OpenShift.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class CatapultCreateOpenShiftSourceSecretTask implements CatapultAdapterTask {

	/**
	 * Accepts POLICY_BINDINGS_UPDATED state.
	 *
	 * @param state Current catapult state
	 * @return true if state is POLICY_BINDINGS_UPDATED, otherwise false.
	 */
	@Override
	public boolean accept(final CatapultStateEnum state) {
		if (state.equals(CatapultStateEnum.POLICY_BINDINGS_UPDATED)) {
			return true;
		}

		return false;
	}

	/**
	 * Add repository source secret to OpenShift and the builder account.
	 *
	 * @param context Current Catapult context
	 */
	@Override
	public void perform(final CatapultContext context) {
		try {
			CatapultAdapterTask task = null;
			switch (context.getRepositoryType()) {
				case BITBUCKET:
					task = new CatapultCreateBitbucketSourceSecretTask();
					break;
				case GITHUB:
					task = new CatapultCreateGitHubSourceSecretTask();
					break;
				case GITLAB:
					task = new CatapultCreateGitLabSourceSecretTask();
					break;
				default:
					break;
			}
			if (task != null && task.accept(context.getCatapultState())) {
				task.perform(context);
			}
		} catch (CatapultException e) {
			e.printStackTrace();
		}
	}

}
