package dk.theknights.catapult.model.webhook.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import dk.theknights.catapult.model.webhook.AbstractWebHook;

/**
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 8/24/18.
 */
public class GitHubWebhook extends AbstractWebHook {

	@JsonProperty("status")
	private String status;

	@JsonProperty("repository")
	private Repository repository = new Repository();

	@JsonProperty("pusher")
	private Pusher pusher = new Pusher();

	@JsonProperty("commits")
	private Commits commits = new Commits();

	/**
	 * Set status of response code in request.
	 *
	 * @param status http status code
	 */
	@JsonProperty("status")
	public void setStatus(final String status) {
		this.status = status;
	}

	/**
	 * Get http status code
	 *
	 * @return http status code
	 */
	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	/**
	 * Set status of response code in request.
	 *
	 * @param repository http status code
	 */
	@JsonProperty("status")
	public void setStatus(final Repository repository) {
		this.repository = repository;
	}

	/**
	 * Get http status code
	 *
	 * @return http status code
	 */
	@JsonProperty("status")
	public Repository getRepository() {
		return repository;
	}

	/**
	 * Set status of response code in request.
	 *
	 * @param pusher http status code
	 */
	@JsonProperty("status")
	public void setPusher(final Pusher pusher) {
		this.pusher = pusher;
	}

	/**
	 * Get http status code
	 *
	 * @return http status code
	 */
	@JsonProperty("status")
	public Pusher getPusher() {
		return pusher;
	}

	/**
	 * Set status of response code in request.
	 *
	 * @param commits http status code
	 */
	@JsonProperty("commits")
	public void setCommits(final Commits commits) {
		this.commits = commits;
	}

	/**
	 * Get http status code
	 *
	 * @return http status code
	 */
	@JsonProperty("status")
	public Commits getCommits() {
		return commits;
	}

	@Override
	public String getRepositoryUrl() {
		return repository.getHTMLUrl();
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
