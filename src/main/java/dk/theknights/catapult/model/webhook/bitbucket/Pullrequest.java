package dk.theknights.catapult.model.webhook.bitbucket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"type",
		"source",
		"description",
		"links",
		"title",
		"state",
		"comment_count",
		"task_count",
		"reason",
		"author",
		"repository"
})
public class Pullrequest {

	@JsonProperty("type")
	private String type;

	@JsonProperty("source")
	private Source source;

	@JsonProperty("description")
	private String description;

	@JsonProperty("links")
	private Links links;

	@JsonProperty("title")
	private String title;

	@JsonProperty("state")
	private String state;

	@JsonProperty("comment_count")
	private Integer commentcount;

	@JsonProperty("task_count")
	private Integer taskcount;

	@JsonProperty("reason")
	private String reason;

	@JsonProperty("author")
	private Author author;

	@JsonProperty("repository")
	private Repository repository;
	/**
	 * Use this to find out if it is an approve request
	"participants": [
					 {
					   "type": "participant",
					   "role": "PARTICIPANT",
					   "user": {
						 "username": "itsolg",
						 "display_name": "Ole Gregersen",
						 "type": "user",
						 "uuid": "{14df09c7-75e1-4d56-9357-1a6dd2207bcf}",
						 "links": {
						   "self": {
							 "href": "https://api.bitbucket.org/2.0/users/itsolg"
						   },
						   "html": {
							 "href": "https://bitbucket.org/itsolg/"
						   },
						   "avatar": {
							 "href": "https://bitbucket.org/account/itsolg/avatar/32/"
						   }
						 }
					   },
					   "approved": true
					 }
				   ]
	 **/
	@JsonProperty("type")
	public String getType() {
		return type;
	}

	@JsonProperty("type")
	public void setType(final String type) {
		this.type = type;
	}

	@JsonProperty("source")
	public Source getSource() {
			return source;
		}

	@JsonProperty("source")
	public void setSource(final Source source) {
			this.source = source;
		}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(final String description) {
		this.description = description;
	}

	@JsonProperty("links")
	public Links getLinks() {
		return links;
	}

	@JsonProperty("links")
	public void setLinks(final Links links) {
		this.links = links;
	}

	@JsonProperty("title")
	public String getTitle() {
		return title;
	}

	@JsonProperty("title")
	public void setTitle(final String title) {
		this.title = title;
	}

	@JsonProperty("state")
	public String getState() {
		return state;
	}

	@JsonProperty("state")
	public void setState(final String state) {
		this.state = state;
	}

	@JsonProperty("comment_count")
	public Integer getCommentcount() {
		return commentcount;
	}

	@JsonProperty("comment_count")
	public void setCommentcount(final Integer commentcount) {
		this.commentcount = commentcount;
	}

	@JsonProperty("task_count")
	public Integer getTask_count() {
		return taskcount;
	}

	@JsonProperty("task_count")
	public void setTask_count(final Integer taskcount) {
		this.taskcount = taskcount;
	}

	@JsonProperty("reason")
	public String getReason() {
		return reason;
	}

	@JsonProperty("reason")
	public void setReason(final String reason) {
		this.reason = reason;
	}

	@JsonProperty("author")
	public Author getAuthor() {
		return author;
	}

	@JsonProperty("author")
	public void setAuthor(final Author author) {
		this.author = author;
	}

	@JsonProperty("repository")
	public Repository getRepository() {
		return repository;
	}

	@JsonProperty("repository")
	public void setRepository(final Repository repository) {
		this.repository = repository;
	}

}
