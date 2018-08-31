
package dk.theknights.catapult.model.webhook.bitbucket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"username",
		"display_name",
		"type",
		"uuid",
		"links"
})
public class Owner {

	@JsonProperty("username")
	private String username;
	@JsonProperty("display_name")
	private String displayName;
	@JsonProperty("type")
	private String type;
	@JsonProperty("uuid")
	private String uuid;
	@JsonProperty("links")
	private Links links;

	/**
	 *
	 * @return
	 *     The username
	 */
	@JsonProperty("username")
	public String getUsername() {
		return username;
	}

	/**
	 *
	 * @param username
	 *     The username
	 */
	@JsonProperty("username")
	public void setUsername(final String username) {
		this.username = username;
	}

	/**
	 *
	 * @return
	 *     The displayName
	 */
	@JsonProperty("display_name")
	public String getDisplayName() {
		return displayName;
	}

	/**
	 *
	 * @param displayName
	 *     The display_name
	 */
	@JsonProperty("display_name")
	public void setDisplayName(final String displayName) {
		this.displayName = displayName;
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
	 *     The uuid
	 */
	@JsonProperty("uuid")
	public String getUuid() {
		return uuid;
	}

	/**
	 *
	 * @param uuid
	 *     The uuid
	 */
	@JsonProperty("uuid")
	public void setUuid(final String uuid) {
		this.uuid = uuid;
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
