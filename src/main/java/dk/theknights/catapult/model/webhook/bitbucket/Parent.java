
package dk.theknights.catapult.model.webhook.bitbucket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"hash",
		"type",
		"links"
})
public class Parent {

	@JsonProperty("hash")
	private String hash;
	@JsonProperty("type")
	private String type;
	@JsonProperty("links")
	private Links links;

	/**
	 *
	 * @return
	 *     The hash
	 */
	@JsonProperty("hash")
	public String getHash() {
		return hash;
	}

	/**
	 *
	 * @param hash
	 *     The hash
	 */
	@JsonProperty("hash")
	public void setHash(final String hash) {
		this.hash = hash;
	}

	/**
	 *
	 * @return
	 *     The type
	 */
	@JsonProperty("type")
	public String getType() {
		return type;
	}

	/**
	 *
	 * @param type
	 *     The type
	 */
	@JsonProperty("type")
	public void setType(final String type) {
		this.type = type;
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

}
