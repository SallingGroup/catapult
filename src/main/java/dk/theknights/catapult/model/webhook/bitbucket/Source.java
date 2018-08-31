package dk.theknights.catapult.model.webhook.bitbucket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"branch"
})
public class Source {

	@JsonProperty("branch")
	private Branch branch;

	@JsonProperty("commit")
	private Commit commit;

	@JsonProperty("branch")
	public Branch getBranch() {
		return branch;
	}

	@JsonProperty("branch")
	public void setBranch(final Branch branch) {
		this.branch = branch;
	}

	@JsonProperty("commit")
	public void setCommit(final Commit commit) {
		this.commit = commit;
	}

	@JsonProperty("commit")
	public Commit getCommit() {
		return commit;
	}
}
