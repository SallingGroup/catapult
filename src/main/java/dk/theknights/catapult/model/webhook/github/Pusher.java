package dk.theknights.catapult.model.webhook.github;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 8/24/18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pusher {

	@JsonProperty("name")
	private String name;

	@JsonProperty("email")
	private String email;

	/**
	 * Set status of response code in request.
	 *
	 * @param name http status code
	 */
	@JsonProperty("name")
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Get http status code
	 *
	 * @return http status code
	 */
	@JsonProperty("id")
	public String getName() {
		return name;
	}

	/**
	 * Set status of response code in request.
	 *
	 * @param email http status code
	 */
	@JsonProperty("email")
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * Get http status code
	 *
	 * @return http status code
	 */
	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

}
