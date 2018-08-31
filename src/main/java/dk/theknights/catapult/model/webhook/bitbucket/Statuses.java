package dk.theknights.catapult.model.webhook.bitbucket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"href"
})
public class Statuses {

	@JsonProperty("href")
	private String href;

	/**
	 *
	 * @return
	 *     The href
	 */
	@JsonProperty("href")
	public String getHref() {
		return href;
	}

	/**
	 *
	 * @param href
	 *     The href
	 */
	@JsonProperty("href")
	public void setHref(final String href) {
		this.href = href;
	}

}
