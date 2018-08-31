package dk.theknights.catapult.model.webhook.gitlab;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import dk.theknights.catapult.model.webhook.AbstractWebHook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"object_kind",
	"user",
	"object_attributes",
	"before",
	"after",
	"ref",
	"checkout_sha",
	"user_id",
	"user_name",
	"user_email",
	"user_avatar",
	"project_id",
	"project",
	"repository",
	"commits",
	"total_commits_count"
})
public class GitLabWebhook extends AbstractWebHook {

	@JsonProperty("status")
	private String status;
	@JsonProperty("object_kind")
	private String objectKind;
	@JsonProperty("user")
	private User user;
	@JsonProperty("object_attributes")
	private ObjectAttributes objectAttributes;
	@JsonProperty("before")
	private String before;
	@JsonProperty("after")
	private String after;
	@JsonProperty("ref")
	private String ref;
	@JsonProperty("checkout_sha")
	private String checkoutSha;
	@JsonProperty("user_id")
	private Integer userId;
	@JsonProperty("user_name")
	private String username;
	@JsonProperty("user_email")
	private String userEmail;
	@JsonProperty("user_avatar")
	private String userAvatar;
	@JsonProperty("project_id")
	private Integer projectId;
	@JsonProperty("project")
	private Project project;
	@JsonProperty("repository")
	private Repository repository;
	@JsonProperty("commits")
	private List<Commit> commits = null;
	@JsonProperty("total_commits_count")
	private Integer totalCommitsCount;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("status")
	public void setStatus(final String status) {
		this.status = status;
	}

	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	@JsonProperty("object_kind")
	public String getObjectKind() {
		return objectKind;
	}

	@JsonProperty("object_kind")
	public void setObjectKind(final String objectKind) {
		this.objectKind = objectKind;
	}

	@JsonProperty("user")
	public User getUser() {
		return user;
	}

	@JsonProperty("user")
	public void setUser(final User user) {
		this.user = user;
	}

	@JsonProperty("before")
	public String getBefore() {
		return before;
	}

	@JsonProperty("before")
	public void setBefore(final String before) {
		this.before = before;
	}

	@JsonProperty("after")
	public String getAfter() {
		return after;
	}

	@JsonProperty("after")
	public void setAfter(final String after) {
		this.after = after;
	}

	@JsonProperty("ref")
	public String getRef() {
		return ref;
	}

	@JsonProperty("ref")
	public void setRef(final String ref) {
		this.ref = ref;
	}

	@JsonProperty("checkout_sha")
	public String getCheckoutSha() {
		return checkoutSha;
	}

	@JsonProperty("checkout_sha")
	public void setCheckoutSha(final String checkoutSha) {
		this.checkoutSha = checkoutSha;
	}

	@JsonProperty("user_id")
	public void setUserId(final Integer userId) {
		this.userId = userId;
	}

	@JsonProperty("user_name")
	public String getUsername() {
		return username;
	}

	@JsonProperty("user_name")
	public void setUsername(final String username) {
		this.username = username;
	}

	@JsonProperty("user_email")
	public String getUserEmail() {
		return userEmail;
	}

	@JsonProperty("user_email")
	public void setUserEmail(final String userEmail) {
		this.userEmail = userEmail;
	}

	@JsonProperty("user_avatar")
	public String getUserAvatar() {
		return userAvatar;
	}

	@JsonProperty("user_avatar")
	public void setUserAvatar(final String userAvatar) {
		this.userAvatar = userAvatar;
	}

	@JsonProperty("project_id")
	public Integer getProjectId() {
		return projectId;
	}

	@JsonProperty("project_id")
	public void setProjectId(final Integer projectId) {
		this.projectId = projectId;
	}

	@JsonProperty("project")
	public Project getProject() {
		return project;
	}

	@JsonProperty("project")
	public void setProject(final Project project) {
		this.project = project;
	}

	@JsonProperty("repository")
	public Repository getRepository() {
		return repository;
	}

	@JsonProperty("repository")
	public void setRepository(final Repository repository) {
		this.repository = repository;
	}

	@JsonProperty("commits")
	public List<Commit> getCommits() {
		return commits;
	}

	@JsonProperty("commits")
	public void setCommits(final List<Commit> commits) {
		this.commits = commits;
	}

	@JsonProperty("total_commits_count")
	public Integer getTotalCommitsCount() {
		return totalCommitsCount;
	}

	@JsonProperty("total_commits_count")
	public void setTotalCommitsCount(final Integer totalCommitsCount) {
		this.totalCommitsCount = totalCommitsCount;
	}

	@JsonProperty("object_attributes")
	public ObjectAttributes getObjectAttributes() {
		return objectAttributes;
	}

	@JsonProperty("object_attributes")
	public void setObjectAttributes(final ObjectAttributes objectAttributes) {
		this.objectAttributes = objectAttributes;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(final String name, final Object value) {
		this.additionalProperties.put(name, value);
	}

	public String getRepositoryUrl() {
		return null;
	}

	public String getRepositoryHTTPSUrl() {
		String gitUrl = getRepository().getGitHttpUrl();
		if (gitUrl.indexOf(".git") > -1) {
			gitUrl = gitUrl.substring(0, gitUrl.indexOf(".git"));
		}
		return gitUrl;
	}

	public String getRepositoryGitSshUrl() {
		return getRepository().getGitSshUrl();
	}

	public String getBranchName() {
		return null;
	}

	public String getCommitId() {
		return null;
	}

	public String getRepositoryName() {
		return getObjectAttributes().getSource().getNamespace();
	}

	public String getOpenShiftProjectName() {
		return getRepository().getName();
	}

	public String getUserId() {
		if (userId != null) {
			return userId.toString();
		}
		return null;
	}

	public String getUserDisplayName() {
		return null;
	}

	public boolean isTag() {
		return false;
	}

	public String getTag() {
		return null;
	}

	public boolean isPullRequest() {
		if (getObjectAttributes().getMergeStatus() != null) {
			return true;
		}
		return false;
	}

	public String getPullRequestStatus() {
		if (getObjectAttributes().getMergeStatus().equalsIgnoreCase("merged")) {
			return AbstractWebHook.PULL_REQUEST_STATUS_MERGED;
		}
		return AbstractWebHook.PULL_REQUEST_STATUS_UNKNOWN;
	}

	public String getPullRequestSourceName() {
		return getObjectAttributes().getSource().getName();
	}

}
