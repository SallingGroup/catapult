
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
	"description",
	"web_url",
	"avatar_url",
	"git_ssh_url",
	"git_http_url",
	"namespace",
	"visibility_level",
	"path_with_namespace",
	"default_branch",
	"homepage",
	"url",
	"ssh_url",
	"http_url"
})
public class Source {

	@JsonProperty("name")
	private String name;
	@JsonProperty("description")
	private String description;
	@JsonProperty("web_url")
	private String webUrl;
	@JsonProperty("avatar_url")
	private Object avatarUrl;
	@JsonProperty("git_ssh_url")
	private String gitSshUrl;
	@JsonProperty("git_http_url")
	private String gitHttpUrl;
	@JsonProperty("namespace")
	private String namespace;
	@JsonProperty("visibility_level")
	private Integer visibilityLevel;
	@JsonProperty("path_with_namespace")
	private String pathWithNamespace;
	@JsonProperty("default_branch")
	private String defaultBranch;
	@JsonProperty("homepage")
	private String homepage;
	@JsonProperty("url")
	private String url;
	@JsonProperty("ssh_url")
	private String sshUrl;
	@JsonProperty("http_url")
	private String httpUrl;
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

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(final String description) {
		this.description = description;
	}

	@JsonProperty("web_url")
	public String getWebUrl() {
		return webUrl;
	}

	@JsonProperty("web_url")
	public void setWebUrl(final String webUrl) {
		this.webUrl = webUrl;
	}

	@JsonProperty("avatar_url")
	public Object getAvatarUrl() {
		return avatarUrl;
	}

	@JsonProperty("avatar_url")
	public void setAvatarUrl(final Object avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	@JsonProperty("git_ssh_url")
	public String getGitSshUrl() {
		return gitSshUrl;
	}

	@JsonProperty("git_ssh_url")
	public void setGitSshUrl(final String gitSshUrl) {
		this.gitSshUrl = gitSshUrl;
	}

	@JsonProperty("git_http_url")
	public String getGitHttpUrl() {
		return gitHttpUrl;
	}

	@JsonProperty("git_http_url")
	public void setGitHttpUrl(final String gitHttpUrl) {
		this.gitHttpUrl = gitHttpUrl;
	}

	@JsonProperty("namespace")
	public String getNamespace() {
		return namespace;
	}

	@JsonProperty("namespace")
	public void setNamespace(final String namespace) {
		this.namespace = namespace;
	}

	@JsonProperty("visibility_level")
	public Integer getVisibilityLevel() {
		return visibilityLevel;
	}

	@JsonProperty("visibility_level")
	public void setVisibilityLevel(final Integer visibilityLevel) {
		this.visibilityLevel = visibilityLevel;
	}

	@JsonProperty("path_with_namespace")
	public String getPathWithNamespace() {
		return pathWithNamespace;
	}

	@JsonProperty("path_with_namespace")
	public void setPathWithNamespace(final String pathWithNamespace) {
		this.pathWithNamespace = pathWithNamespace;
	}

	@JsonProperty("default_branch")
	public String getDefaultBranch() {
		return defaultBranch;
	}

	@JsonProperty("default_branch")
	public void setDefaultBranch(final String defaultBranch) {
		this.defaultBranch = defaultBranch;
	}

	@JsonProperty("homepage")
	public String getHomepage() {
		return homepage;
	}

	@JsonProperty("homepage")
	public void setHomepage(final String homepage) {
		this.homepage = homepage;
	}

	@JsonProperty("url")
	public String getUrl() {
		return url;
	}

	@JsonProperty("url")
	public void setUrl(final String url) {
		this.url = url;
	}

	@JsonProperty("ssh_url")
	public String getSshUrl() {
		return sshUrl;
	}

	@JsonProperty("ssh_url")
	public void setSshUrl(final String sshUrl) {
		this.sshUrl = sshUrl;
	}

	@JsonProperty("http_url")
	public String getHttpUrl() {
		return httpUrl;
	}

	@JsonProperty("http_url")
	public void setHttpUrl(final String httpUrl) {
		this.httpUrl = httpUrl;
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
