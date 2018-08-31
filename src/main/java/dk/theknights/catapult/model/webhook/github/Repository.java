package dk.theknights.catapult.model.webhook.github;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 8/24/18.
 */
public class Repository {

	@JsonProperty("id")
	private String id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("html_url")
	private String htmlurl;

	/**
	 * Set status of response code in request.
	 *
	 * @param id http status code
	 */
	@JsonProperty("id")
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * Get http status code
	 *
	 * @return http status code
	 */
	@JsonProperty("id")
	public String getId() {
		return id;
	}

	/**
	 *
	 * @param name
	 */
	@JsonProperty("name")
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 *
	 * @return
	 */
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("html_url")
	public void setHTMLUrl(final String htmlurl) {
		this.htmlurl = htmlurl;
	}

	@JsonProperty("html_url")
	public String getHTMLUrl() {
		return htmlurl;
	}

}
