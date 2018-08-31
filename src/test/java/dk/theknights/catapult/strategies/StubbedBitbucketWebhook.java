package dk.theknights.catapult.strategies;

import dk.theknights.catapult.model.webhook.AbstractWebHook;

/**
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/8/18.
 */
public class StubbedBitbucketWebhook extends AbstractWebHook {
	@Override
	public String getRepositoryUrl() {
		return "https://bitbucket.org/fakerepo";
	}

	@Override
	public String getRepositoryHTTPSUrl() {
		return "https://bitbucket.org/fakerepo/catapult";
	}

	@Override
	public String getRepositoryGitSshUrl() {
		return null;
	}

	@Override
	public String getBranchName() {
		return "some-fake-branch";
	}

	@Override
	public String getCommitId() {
		return "0112383d9daf888432a26b6187b4ede334acab19";
	}

	@Override
	public String getRepositoryName() {
		return null;
	}

	@Override
	public String getOpenShiftProjectName() {
		return null;
	}

	@Override
	public String getUserId() {
		return null;
	}

	@Override
	public String getUserDisplayName() {
		return null;
	}

	@Override
	public boolean isTag() {
		return false;
	}

	@Override
	public String getTag() {
		return null;
	}

	@Override
	public boolean isPullRequest() {
		return false;
	}

	@Override
	public String getPullRequestStatus() {
		return null;
	}

	@Override
	public String getPullRequestSourceName() {
		return null;
	}
}
