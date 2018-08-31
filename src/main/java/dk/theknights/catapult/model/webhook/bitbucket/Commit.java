package dk.theknights.catapult.model.webhook.bitbucket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"hash",
		"links",
		"author",
		"parents",
		"date",
		"message",
		"type"
})
public class Commit {

	@JsonProperty("hash")
	private String hash;
	@JsonProperty("links")
	private Links links;
	@JsonProperty("author")
	private Author author;
	@JsonProperty("parents")
	private List<Parent> parents = new ArrayList<Parent>();
	@JsonProperty("date")
	private String date;
	@JsonProperty("message")
	private String message;
	@JsonProperty("type")
	private String type;

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
	 *     The author
	 */
	@JsonProperty("author")
	public Author getAuthor() {
		return author;
	}

	/**
	 *
	 * @param author
	 *     The author
	 */
	@JsonProperty("author")
	public void setAuthor(final Author author) {
		this.author = author;
	}

	/**
	 *
	 * @return
	 *     The parents
	 */
	@JsonProperty("parents")
	public List<Parent> getParents() {
		return parents;
	}

	/**
	 *
	 * @param parents
	 *     The parents
	 */
	@JsonProperty("parents")
	public void setParents(final List<Parent> parents) {
		this.parents = parents;
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

	/**
	 *
	 * @return
	 *     The message
	 */
	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	/**
	 *
	 * @param message
	 *     The message
	 */
	@JsonProperty("message")
	public void setMessage(final String message) {
		this.message = message;
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

}
