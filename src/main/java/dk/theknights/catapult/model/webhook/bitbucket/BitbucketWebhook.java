package dk.theknights.catapult.model.webhook.bitbucket;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import dk.theknights.catapult.config.CatapultConfig;
import dk.theknights.catapult.model.webhook.AbstractWebHook;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

/**
 * Bitbucket webhook model. This is populated from json posted to the request controller.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 2/28/18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BitbucketWebhook extends AbstractWebHook {

	@JsonProperty("status")
	private String status;
	@JsonProperty("push")
	private Push push;
	@JsonProperty("repository")
	private Repository repository;
	@JsonProperty("branch")
	private String branch;
	@JsonProperty("pullrequest")
	private Pullrequest pullrequest;
	@JsonProperty("approval")
	private Approval approval;
	@JsonProperty("actor")
	private Actor actor;

	private CatapultConfig configuration;

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
	 * Get push from json in posted request
	 *
	 * @return push from json
	 */
	@JsonProperty("push")
	public Push getPush() {
		return push;
	}

	/**
	 * Set push from json posted in request.
	 *
	 * @param push data from json
	 */
	@JsonProperty("push")
	public void setPush(final Push push) {
		this.push = push;
	}

	/**
	 * Get repository from json posted in request.
	 *
	 * @return repository from json
	 */
	@JsonProperty("repository")
	public Repository getRepository() {
		return repository;
	}

	/**
	 * Set repository from json posted in request.
	 *
	 * @param repository data from json
	 */
	@JsonProperty("repository")
	public void setRepository(final Repository repository) {
		this.repository = repository;
	}

	/**
	 * Get branch from json posted in request.
	 *
	 * @return branch from json
	 */
	@JsonProperty("branch")
	public String getBranch() {
		return branch;
	}

	/**
	 * Set branch from json posted in request.
	 *
	 * @param branch data from json
	 */
	@JsonProperty("branch")
	public void setBranch(final String branch) {
		this.branch = branch;
	}

	/**
	 * Get pullrequest from json posted in request.
	 *
	 * @return pullrequest from json
	 */
	@JsonProperty("pullrequest")
	public Pullrequest getPullrequest() {
		return pullrequest;
	}

	/**
	 * Set pullrequest from json posted in request.
	 *
	 * @param pullrequest data from json
	 */
	@JsonProperty("pullrequest")
	public void setPullrequest(final Pullrequest pullrequest) {
		this.pullrequest = pullrequest;
	}

	/**
	 * Get approval from json posted request.
	 *
	 * @return approval from json
	 */
	@JsonProperty("approval")
	public Approval getApproval() {
		return approval;
	}

	/**
	 * Set approval from json posted request.
	 *
	 * @param approval approval from json
	 */
	@JsonProperty("approval")
	public void setApproval(final Approval approval) {
		this.approval = approval;
	}

	/**
	 * Get actor from json posted request.
	 *
	 * @return actor form json
	 */
	@JsonProperty("actor")
	public Actor getActor() {
		return actor;
	}

	/**
	 * Set actor from json posted request.
	 *
	 * @param actor actor from json
	 */
	@JsonProperty("actor")
	public void setActor(final Actor actor) {
		this.actor = actor;
	}

	/**
	 * Get webhook request repository url.
	 *
	 * @return url of request repository
	 */
	@JsonIgnore
	public String getRepositoryUrl() {
		return getRepository().getLinks().getHtml().getHref();
	}

	/**
	 * Get webhook request https url.
	 *
	 * @return https url of request repository
	 */
	@JsonIgnore
	public String getRepositoryHTTPSUrl() {
		return getRepository().getOwner().getLinks().getHtml().getHref();
	}

	/**
	 * Get webhook request repository ssh url.
	 *
	 * @return ssh url of request repository
	 */
	@JsonIgnore
	public String getRepositoryGitSshUrl() {
		return getPush().getChanges().get(0).getNew().getName();
	}

	/**
	 * Get repository source branch name. '.' are replaced with '-'
	 *
	 * @return branch name from source repository
	 */
	@JsonIgnore
	public String getBranchName() {
		String branchName = null;
		switch (getRequestType()) {
			case PULL_REQUEST:
				branchName = getPullrequest().getSource().getBranch().getName();
				break;
			default:
				branchName = getPushBranchName();
		}

		if (branchName.indexOf(".") > -1) {
			branchName = branchName.replaceAll("\\.", "-");
		}
		return branchName;
	}

	/**
	 * Get webhook request source branch commit hash.
	 *
	 * @return commit hash from source code branch
	 */
	@JsonIgnore
	public String getCommitId() {
		switch (getRequestType()) {
			case PULL_REQUEST:
				return getPullrequest().getSource().getCommit().getHash();
			default:
				Push push = getPush();
				if (push != null
					&& push.getChanges() != null
					&& push.getChanges().get(0) != null
					&& push.getChanges().get(0).getNew() != null) {
					return push.getChanges().get(0).getNew().getTarget().getHash();
				}
				throw new NoSuchElementException("New element in webhook payload is null");
		}
	}

	/**
	 * Get webhook request repository name.
	 *
	 * @return The repository name
	 */
	@JsonIgnore
	public String getRepositoryName() {
		return getRepository().getName();
	}

	/**
	 * Get OpenShift compatible projectname. If project name is in the safe group list then the configured project name is returned.
	 *
	 * @return Project name that is compatible with OpenShift
	 */
	@JsonIgnore
	@SuppressFBWarnings("UWF_FIELD_NOT_INITIALIZED_IN_CONSTRUCTOR")
	public String getOpenShiftProjectName() {

		String tmpProjectName = getRepository().getProject().getName();

		if (tmpProjectName != null) {
			String projectMappings = configuration.getCatapultProperties().getProjectMapping();
			if (projectMappings.indexOf(tmpProjectName) > -1) {
				String[] mappingSplit = projectMappings.split(",");
				boolean found = false;
				int index = 0;
				while (!found && index < mappingSplit.length) {
					if (mappingSplit[index].startsWith(tmpProjectName + "=")) {
						String mapping = mappingSplit[index];
						String value = mapping.split("=")[1];
						return value;
					}
					index++;
				}
			}
		}
		String projectName = getRepositoryName() + "-" + getBranchName();
		return projectName.toLowerCase(Locale.ROOT);
	}

	/**
	 * Get webhook request userid from Bitbucket.
	 *
	 * @return username from bitbucket
	 */
	@JsonIgnore
	public String getUserId() {
		return getActor().getUsername();
	}

	/**
	 * Get webhook request user display name.
	 *
	 * @return Display name of user that activated webhook request
	 */
	@JsonIgnore
	public String getUserDisplayName() {
		return getActor().getDisplayName();
	}

	/**
	 * Identify if webhook request contains a tag.
	 *
	 * @return true if request contains a tag
	 */
	@JsonIgnore
	public boolean isTag() {
		List<Change> changes = getPush().getChanges();
		for (Change change: changes) {
			if (change.getNew() != null && change.getNew().getType().equals("tag")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get repository branch tag.
	 *
	 * @return Get the new tag on repository branch if request is a push request, otherwise null is returned
	 */
	@JsonIgnore
	public String getTag() {
		if (isTag()) {
			return getPush().getChanges().get(0).getNew().getName();
		}
		return null;
	}

	/**
	 * Identify if request is a pull request.
	 *
	 * @return true if request is a pull request
	 */
	@JsonIgnore
	public boolean isPullRequest() {
		if (getPullrequest() != null && getPullrequest().getState() != null) {
			return true;
		}
		return false;
	}

	/**
	 * Get pull request status. Use this to determine if pull request is in state OPEN or MERGED.
	 *
	 * @return status of pull request. If request is not pull request then UNKNOWN is returned.
	 */
	@JsonIgnore
	public String getPullRequestStatus() {
		if (isPullRequest()) {
			if (getPullrequest().getState().equals("OPEN")) {
				return PULL_REQUEST_STATUS_OPEN;
			} else if (getPullrequest().getState().equals("MERGED")) {
				return PULL_REQUEST_STATUS_MERGED;
			}
		}
		return PULL_REQUEST_STATUS_UNKNOWN;
	}

	/**
	 * Get source branch name.
	 *
	 * @return if pullrequest then source branch is returned, otherwise null
	 */
	@JsonIgnore
	public String getPullRequestSourceName() {
		if (isPullRequest()) {
			return getPullrequest().getSource().getBranch().getName();
		}
		return null;
	}

	/**
	 * Set Catapult config
	 *
	 * @param configuration new catapult configuration to set
	 */
	public void setCatapultConfig(final CatapultConfig configuration) {
		this.configuration = configuration;
	}

	private String getPushBranchName() {
		String branchName = null;
		Change newchange = getPush().getChanges().get(0);
		if (newchange.getNew() == null) {
			branchName = getBranch();
		} else {
			branchName = newchange.getNew().getName();
		}
		return branchName;
	}

}
