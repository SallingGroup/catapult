package dk.theknights.catapult.model.webhook.bitbucket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"links",
		"type",
		"name",
		"key",
		"uuid"
})
public class Project {

	@JsonProperty("links")
	private Links links;
	@JsonProperty("type")
	private String type;
	@JsonProperty("name")
	private String name;
	@JsonProperty("key")
	private String key;
	@JsonProperty("uuid")
	private String uuid;

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
	 *     The name
	 */
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	/**
	 *
	 * @param name
	 *     The name
	 */
	@JsonProperty("name")
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 *
	 * @return
	 *     The key
	 */
	@JsonProperty("key")
	public String getKey() {
		return key;
	}

	/**
	 *
	 * @param key
	 *     The key
	 */
	@JsonProperty("key")
	public void setKey(final String key) {
		this.key = key;
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

}
