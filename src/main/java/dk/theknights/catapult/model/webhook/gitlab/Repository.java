
package dk.theknights.catapult.model.webhook.gitlab;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"name",
	"url",
	"description",
	"homepage",
	"git_http_url",
	"git_ssh_url",
	"visibility_level"
})
public class Repository {

	@JsonProperty("name")
	private String name;
	@JsonProperty("url")
	private String url;
	@JsonProperty("description")
	private String description;
	@JsonProperty("homepage")
	private String homepage;
	@JsonProperty("git_http_url")
	private String gitHttpUrl;
	@JsonProperty("git_ssh_url")
	private String gitSshUrl;
	@JsonProperty("visibility_level")
	private Integer visibilityLevel;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(final String name) {
		this.name = name;
	}

	@JsonProperty("url")
	public String getUrl() {
		return url;
	}

	@JsonProperty("url")
	public void setUrl(final String url) {
		this.url = url;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(final String description) {
		this.description = description;
	}

	@JsonProperty("homepage")
	public String getHomepage() {
		return homepage;
	}

	@JsonProperty("homepage")
	public void setHomepage(final String homepage) {
		this.homepage = homepage;
	}

	@JsonProperty("git_http_url")
	public String getGitHttpUrl() {
		return gitHttpUrl;
	}

	@JsonProperty("git_http_url")
	public void setGitHttpUrl(final String gitHttpUrl) {
		this.gitHttpUrl = gitHttpUrl;
	}

	@JsonProperty("git_ssh_url")
	public String getGitSshUrl() {
		return gitSshUrl;
	}

	@JsonProperty("git_ssh_url")
	public void setGitSshUrl(final String gitSshUrl) {
		this.gitSshUrl = gitSshUrl;
	}

	@JsonProperty("visibility_level")
	public Integer getVisibilityLevel() {
		return visibilityLevel;
	}

	@JsonProperty("visibility_level")
	public void setVisibilityLevel(final Integer visibilityLevel) {
		this.visibilityLevel = visibilityLevel;
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
