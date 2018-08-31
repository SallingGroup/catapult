
package dk.theknights.catapult.model.webhook.bitbucket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"commits",
		"html"
})
public class Links {

	@JsonProperty("commits")
	private Commits commits;
	@JsonProperty("html")
	private Html html;

	/**
	 *
	 * @return
	 *     The commits
	 */
	@JsonProperty("commits")
	public Commits getCommits() {
		return commits;
	}

	/**
	 *
	 * @param commits
	 *     The commits
	 */
	@JsonProperty("commits")
	public void setCommits(final Commits commits) {
		this.commits = commits;
	}

	/**
	 *
	 * @return
	 *     The html
	 */
	@JsonProperty("html")
	public Html getHtml() {
		return html;
	}

	/**
	 *
	 * @param html
	 *     The html
	 */
	@JsonProperty("html")
	public void setHtml(final Html html) {
		this.html = html;
	}

}
