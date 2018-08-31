
package dk.theknights.catapult.model.webhook.gitlab;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"id",
	"target_branch",
	"source_branch",
	"source_project_id",
	"author_id",
	"assignee_id",
	"title",
	"created_at",
	"updated_at",
	"st_commits",
	"st_diffs",
	"milestone_id",
	"state",
	"merge_status",
	"target_project_id",
	"iid",
	"description",
	"source",
	"target",
	"last_commit",
	"work_in_progress",
	"url",
	"action",
	"assignee"
})
public class ObjectAttributes {

	@JsonProperty("id")
	private Integer id;
	@JsonProperty("target_branch")
	private String targetBranch;
	@JsonProperty("source_branch")
	private String sourceBranch;
	@JsonProperty("source_project_id")
	private Integer sourceProjectId;
	@JsonProperty("author_id")
	private Integer authorId;
	@JsonProperty("assignee_id")
	private Integer assigneeId;
	@JsonProperty("title")
	private String title;
	@JsonProperty("created_at")
	private String createdAt;
	@JsonProperty("updated_at")
	private String updatedAt;
	@JsonProperty("st_commits")
	private Object stCommits;
	@JsonProperty("st_diffs")
	private Object stDiffs;
	@JsonProperty("milestone_id")
	private Object milestoneId;
	@JsonProperty("state")
	private String state;
	@JsonProperty("merge_status")
	private String mergeStatus;
	@JsonProperty("target_project_id")
	private Integer targetProjectId;
	@JsonProperty("iid")
	private Integer iid;
	@JsonProperty("description")
	private String description;
	@JsonProperty("source")
	private Source source;
	@JsonProperty("target")
	private Target target;
	@JsonProperty("last_commit")
	private LastCommit lastCommit;
	@JsonProperty("work_in_progress")
	private Boolean workInProgress;
	@JsonProperty("url")
	private String url;
	@JsonProperty("action")
	private String action;
	@JsonProperty("assignee")
	private Assignee assignee;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("id")
	public Integer getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(final Integer id) {
		this.id = id;
	}

	@JsonProperty("target_branch")
	public String getTargetBranch() {
		return targetBranch;
	}

	@JsonProperty("target_branch")
	public void setTargetBranch(final String targetBranch) {
		this.targetBranch = targetBranch;
	}

	@JsonProperty("source_branch")
	public String getSourceBranch() {
		return sourceBranch;
	}

	@JsonProperty("source_branch")
	public void setSourceBranch(final String sourceBranch) {
		this.sourceBranch = sourceBranch;
	}

	@JsonProperty("source_project_id")
	public Integer getSourceProjectId() {
		return sourceProjectId;
	}

	@JsonProperty("source_project_id")
	public void setSourceProjectId(final Integer sourceProjectId) {
		this.sourceProjectId = sourceProjectId;
	}

	@JsonProperty("author_id")
	public Integer getAuthorId() {
		return authorId;
	}

	@JsonProperty("author_id")
	public void setAuthorId(final Integer authorId) {
		this.authorId = authorId;
	}

	@JsonProperty("assignee_id")
	public Integer getAssigneeId() {
		return assigneeId;
	}

	@JsonProperty("assignee_id")
	public void setAssigneeId(final Integer assigneeId) {
		this.assigneeId = assigneeId;
	}

	@JsonProperty("title")
	public String getTitle() {
		return title;
	}

	@JsonProperty("title")
	public void setTitle(final String title) {
		this.title = title;
	}

	@JsonProperty("created_at")
	public String getCreatedAt() {
		return createdAt;
	}

	@JsonProperty("created_at")
	public void setCreatedAt(final String createdAt) {
		this.createdAt = createdAt;
	}

	@JsonProperty("updated_at")
	public String getUpdatedAt() {
		return updatedAt;
	}

	@JsonProperty("updated_at")
	public void setUpdatedAt(final String updatedAt) {
		this.updatedAt = updatedAt;
	}

	@JsonProperty("st_commits")
	public Object getStCommits() {
		return stCommits;
	}

	@JsonProperty("st_commits")
	public void setStCommits(final Object stCommits) {
		this.stCommits = stCommits;
	}

	@JsonProperty("st_diffs")
	public Object getStDiffs() {
		return stDiffs;
	}

	@JsonProperty("st_diffs")
	public void setStDiffs(final Object stDiffs) {
		this.stDiffs = stDiffs;
	}

	@JsonProperty("milestone_id")
	public Object getMilestoneId() {
		return milestoneId;
	}

	@JsonProperty("milestone_id")
	public void setMilestoneId(final Object milestoneId) {
		this.milestoneId = milestoneId;
	}

	@JsonProperty("state")
	public String getState() {
		return state;
	}

	@JsonProperty("state")
	public void setState(final String state) {
		this.state = state;
	}

	@JsonProperty("merge_status")
	public String getMergeStatus() {
		return mergeStatus;
	}

	@JsonProperty("merge_status")
	public void setMergeStatus(final String mergeStatus) {
		this.mergeStatus = mergeStatus;
	}

	@JsonProperty("target_project_id")
	public Integer getTargetProjectId() {
		return targetProjectId;
	}

	@JsonProperty("target_project_id")
	public void setTargetProjectId(final Integer targetProjectId) {
		this.targetProjectId = targetProjectId;
	}

	@JsonProperty("iid")
	public Integer getIid() {
		return iid;
	}

	@JsonProperty("iid")
	public void setIid(final Integer iid) {
		this.iid = iid;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(final String description) {
		this.description = description;
	}

	@JsonProperty("source")
	public Source getSource() {
		return source;
	}

	@JsonProperty("source")
	public void setSource(final Source source) {
		this.source = source;
	}

	@JsonProperty("target")
	public Target getTarget() {
		return target;
	}

	@JsonProperty("target")
	public void setTarget(final Target target) {
		this.target = target;
	}

	@JsonProperty("last_commit")
	public LastCommit getLastCommit() {
		return lastCommit;
	}

	@JsonProperty("last_commit")
	public void setLastCommit(final LastCommit lastCommit) {
		this.lastCommit = lastCommit;
	}

	@JsonProperty("work_in_progress")
	public Boolean getWorkInProgress() {
		return workInProgress;
	}

	@JsonProperty("work_in_progress")
	public void setWorkInProgress(final Boolean workInProgress) {
		this.workInProgress = workInProgress;
	}

	@JsonProperty("url")
	public String getUrl() {
		return url;
	}

	@JsonProperty("url")
	public void setUrl(final String url) {
		this.url = url;
	}

	@JsonProperty("action")
	public String getAction() {
		return action;
	}

	@JsonProperty("action")
	public void setAction(final String action) {
		this.action = action;
	}

	@JsonProperty("assignee")
	public Assignee getAssignee() {
		return assignee;
	}

	@JsonProperty("assignee")
	public void setAssignee(final Assignee assignee) {
		this.assignee = assignee;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(final String name, final Object value) {
		this.additionalProperties.put(name, value);
	}

}
