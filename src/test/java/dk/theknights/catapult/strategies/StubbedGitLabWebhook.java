package dk.theknights.catapult.strategies;

import dk.theknights.catapult.model.webhook.AbstractWebHook;

/**
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class StubbedGitLabWebhook extends AbstractWebHook {
		@Override
		public String getRepositoryUrl() {
			return "https://gitlab.com/fakerepo";
		}

		@Override
		public String getRepositoryHTTPSUrl() {
			return null;
		}

		@Override
		public String getRepositoryGitSshUrl() {
			return null;
		}

		@Override
		public String getBranchName() {
			return null;
		}

		@Override
		public String getCommitId() {
			return null;
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

