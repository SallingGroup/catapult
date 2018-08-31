
package dk.theknights.catapult.model.webhook.gitlab;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"id",
	"message",
	"timestamp",
	"url",
	"author",
	"added",
	"modified",
	"removed"
})
public class Commit {

	@JsonProperty("id")
	private String id;
	@JsonProperty("message")
	private String message;
	@JsonProperty("timestamp")
	private String timestamp;
	@JsonProperty("url")
	private String url;
	@JsonProperty("author")
	private Author author;
	@JsonProperty("added")
	private List<String> added = null;
	@JsonProperty("modified")
	private List<String> modified = null;
	@JsonProperty("removed")
	private List<Object> removed = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(final String id) {
		this.id = id;
	}

	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	@JsonProperty("message")
	public void setMessage(final String message) {
		this.message = message;
	}

	@JsonProperty("timestamp")
	public String getTimestamp() {
		return timestamp;
	}

	@JsonProperty("timestamp")
	public void setTimestamp(final String timestamp) {
		this.timestamp = timestamp;
	}

	@JsonProperty("url")
	public String getUrl() {
		return url;
	}

	@JsonProperty("url")
	public void setUrl(final String url) {
		this.url = url;
	}

	@JsonProperty("author")
	public Author getAuthor() {
		return author;
	}

	@JsonProperty("author")
	public void setAuthor(final Author author) {
		this.author = author;
	}

	@JsonProperty("added")
	public List<String> getAdded() {
		return added;
	}

	@JsonProperty("added")
	public void setAdded(final List<String> added) {
		this.added = added;
	}

	@JsonProperty("modified")
	public List<String> getModified() {
		return modified;
	}

	@JsonProperty("modified")
	public void setModified(final List<String> modified) {
		this.modified = modified;
	}

	@JsonProperty("removed")
	public List<Object> getRemoved() {
		return removed;
	}

	@JsonProperty("removed")
	public void setRemoved(final List<Object> removed) {
		this.removed = removed;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(final String name, final Object value) {
		this.additionalProperties.put(name, value);
	}

}
