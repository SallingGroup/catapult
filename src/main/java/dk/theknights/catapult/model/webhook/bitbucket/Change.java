package dk.theknights.catapult.model.webhook.bitbucket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"forced",
		"old",
		"links",
		"created",
		"commits",
		"truncated",
		"closed",
		"new"
})
public class Change {

	@JsonProperty("forced")
	private Boolean forced;
	@JsonProperty("old")
	private Object old;
	@JsonProperty("links")
	private Links links;
	@JsonProperty("created")
	private Boolean created;
	@JsonProperty("commits")
	private List<Commit> commits = new ArrayList<Commit>();
	@JsonProperty("truncated")
	private Boolean truncated;
	@JsonProperty("closed")
	private Boolean closed;
	@JsonProperty("new")
	private New anew;

	/**
	 *
	 * @return
	 *     The forced
	 */
	@JsonProperty("forced")
	public Boolean getForced() {
		return forced;
	}

	/**
	 *
	 * @param forced
	 *     The forced
	 */
	@JsonProperty("forced")
	public void setForced(final Boolean forced) {
		this.forced = forced;
	}

	/**
	 *
	 * @return
	 *     The old
	 */
	@JsonProperty("old")
	public Object getOld() {
		return old;
	}

	/**
	 *
	 * @param old
	 *     The old
	 */
	@JsonProperty("old")
	public void setOld(final Object old) {
		this.old = old;
	}

	/**
	 *
	 * @return
	 *     The links
	 */
	@JsonProperty("links")
	public Links getLinks() {
		return links;
	}

	/**
	 *
	 * @param links
	 *     The links
	 */
	@JsonProperty("links")
	public void setLinks(final Links links) {
		this.links = links;
	}

	/**
	 *
	 * @return
	 *     The created
	 */
	@JsonProperty("created")
	public Boolean getCreated() {
		return created;
	}

	/**
	 *
	 * @param created
	 *     The created
	 */
	@JsonProperty("created")
	public void setCreated(final Boolean created) {
		this.created = created;
	}

	/**
	 *
	 * @return
	 *     The commits
	 */
	@JsonProperty("commits")
	public List<Commit> getCommits() {
		return commits;
	}

	/**
	 *
	 * @param commits
	 *     The commits
	 */
	@JsonProperty("commits")
	public void setCommits(final List<Commit> commits) {
		this.commits = commits;
	}

	/**
	 *
	 * @return
	 *     The truncated
	 */
	@JsonProperty("truncated")
	public Boolean getTruncated() {
		return truncated;
	}

	/**
	 *
	 * @param truncated
	 *     The truncated
	 */
	@JsonProperty("truncated")
	public void setTruncated(final Boolean truncated) {
		this.truncated = truncated;
	}

	/**
	 *
	 * @return
	 *     The closed
	 */
	@JsonProperty("closed")
	public Boolean getClosed() {
		return closed;
	}

	/**
	 *
	 * @param closed
	 *     The closed
	 */
	@JsonProperty("closed")
	public void setClosed(final Boolean closed) {
		this.closed = closed;
	}

	/**
	 *
	 * @return
	 *     The _new
	 */
	@JsonProperty("new")
	public New getNew() {
		return anew;
	}

	/**
	 *
	 * @param _new
	 *     The new
	 */
	@JsonProperty("new")
	public void setNew(final New anew) {
		this.anew = anew;
	}

}
