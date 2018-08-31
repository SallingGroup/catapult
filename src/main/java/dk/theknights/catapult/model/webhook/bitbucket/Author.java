package dk.theknights.catapult.model.webhook.bitbucket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"raw",
		"user"
})
public class Author {

	@JsonProperty("raw")
	private String raw;
	@JsonProperty("user")
	private User user;

	/**
	 *
	 * @return
	 *     The raw
	 */
	@JsonProperty("raw")
	public String getRaw() {
		return raw;
	}

	/**
	 *
	 * @param raw
	 *     The raw
	 */
	@JsonProperty("raw")
	public void setRaw(final String raw) {
		this.raw = raw;
	}

	/**
	 *
	 * @return
	 *     The user
	 */
	@JsonProperty("user")
	public User getUser() {
		return user;
	}

	/**
	 *
	 * @param user
	 *     The user
	 */
	@JsonProperty("user")
	public void setUser(final User user) {
		this.user = user;
	}

}
