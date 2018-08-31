package dk.theknights.catapult.model.webhook.bitbucket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"user",
		"date"
})
public class Approval {

	@JsonProperty("user")
	private User user;

	@JsonProperty("date")
	private String date;

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

	/**
	 *
	 * @return
	 *     The date
	 */
	@JsonProperty("date")
	public String getDate() {
		return date;
	}

	/**
	 *
	 * @param date
	 *     The date
	 */
	@JsonProperty("date")
	public void setDate(final String date) {
		this.date = date;
	}

}
