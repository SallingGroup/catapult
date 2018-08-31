package dk.theknights.catapult.model.webhook;

/**
 * This abstract class defines common methods for extracting information from webhooks posted from Bitbucket, GitHub or GitLab.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 2/28/18.
 */
public abstract class AbstractWebHook {

	public static final String PULL_REQUEST_STATUS_OPEN = "OPEN";
	public static final String PULL_REQUEST_STATUS_MERGED = "MERGED";
	public static final String PULL_REQUEST_STATUS_UNKNOWN = "UNKNOWN";

	/**
	 * Get source repository url
	 *
	 * @return url to source repository
	 */
	public abstract String getRepositoryUrl();

	/**
	 * Get https url to source repository.
	 *
	 * @return The https url to webhook repository
	 */
	public abstract String getRepositoryHTTPSUrl();

	/**
	 * Get ssh url to source repository.
	 *
	 * @return The ssh url to webhook repository
	 */
	public abstract String getRepositoryGitSshUrl();

	/**
	 * Get the name of the branch in the webhook request.
	 *
	 * @return Name of branch from webhook
	 */
	public abstract String getBranchName();

	/**
	 * Get the id of current commit.
	 *
	 * @return The current commit hash
	 */
	public abstract String getCommitId();

	/**
	 * Get source repository name.
	 *
	 * @return The name of the repository
	 */
	public abstract String getRepositoryName();

	/**
	 * Get OpenShift compatible project name.
	 *
	 * @return A project name that is compatible with OpenShift.
	 */
	public abstract String getOpenShiftProjectName();

	/**
	 * Get id of user that activated webhook request.
	 *
	 * @return Id of user that activated request
	 */
	public abstract String getUserId();

	/**
	 * Get user display name.
	 *
	 * @return
	 */
	public abstract String getUserDisplayName();

	/**
	 * Identify if request is a tag.
	 *
	 * @return true if request is a tag
	 */
	public abstract boolean isTag();

	public abstract String getTag();

	/**
	 * Identify if request is a pull request.
	 *
	 * @return true if request is a pull request
	 */
	public abstract boolean isPullRequest();

	/**
	 * Get status of webhook request. Status can be OPEN, MERGED or UNKNOWN
	 *
	 * @return status of webhook (OPEN, MERGED or UNKNOWN)
	 */
	public abstract String getPullRequestStatus();

	/**
	 * Get request source name.
	 *
	 * @return The name of the request source
	 */
	public abstract String getPullRequestSourceName();

	/**
	 * Identify webhook request type. Can be of Push-, Pull- or Release request.
	 *
	 * @return Request type (PUSH_REQUEST, PULL_REQUEST, RELEASE_REQUEST)
	 */
	public RequestTypeEnum getRequestType() {
		if (isPullRequest()) {
			return RequestTypeEnum.PULL_REQUEST;
		} else if (isTag()) {
			return RequestTypeEnum.RELEASE_REQUEST;
		} else {
			return RequestTypeEnum.PUSH_REQUEST;
		}
	}

	/**
	 * Get requester formatted as "userid ( user display name )"
	 *
	 * @return formatted requester information
	 */
	public String getRequester() {
		String requester = getUserId();
		if (getUserDisplayName() != null) {
			requester += " (" + getUserDisplayName() + ")";
		}
		return requester;
	}

}
