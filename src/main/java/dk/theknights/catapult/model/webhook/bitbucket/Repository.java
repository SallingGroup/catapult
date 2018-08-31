
package dk.theknights.catapult.model.webhook.bitbucket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"scm",
		"website",
		"uuid",
		"links",
		"project",
		"full_name",
		"owner",
		"type",
		"is_private",
		"name"
})
public class Repository {

	@JsonProperty("scm")
	private String scm;
	@JsonProperty("website")
	private String website;
	@JsonProperty("uuid")
	private String uuid;
	@JsonProperty("links")
	private Links links;
	@JsonProperty("project")
	private Project project;
	@JsonProperty("full_name")
	private String fullName;
	@JsonProperty("owner")
	private Owner owner;
	@JsonProperty("type")
	private String type;
	@JsonProperty("is_private")
	private Boolean isPrivate;
	@JsonProperty("name")
	private String name;

	/**
	 *
	 * @return
	 *     The scm
	 */
	@JsonProperty("scm")
	public String getScm() {
		return scm;
	}

	/**
	 *
	 * @param scm
	 *     The scm
	 */
	@JsonProperty("scm")
	public void setScm(final String scm) {
		this.scm = scm;
	}

	/**
	 *
	 * @return
	 *     The website
	 */
	@JsonProperty("website")
	public String getWebsite() {
		return website;
	}

	/**
	 *
	 * @param website
	 *     The website
	 */
	@JsonProperty("website")
	public void setWebsite(final String website) {
		this.website = website;
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
	 *     The project
	 */
	@JsonProperty("project")
	public Project getProject() {
		return project;
	}

	/**
	 *
	 * @param project
	 *     The project
	 */
	@JsonProperty("project")
	public void setProject(final Project project) {
		this.project = project;
	}

	/**
	 *
	 * @return
	 *     The fullName
	 */
	@JsonProperty("full_name")
	public String getFullName() {
		return fullName;
	}

	/**
	 *
	 * @param fullName
	 *     The full_name
	 */
	@JsonProperty("full_name")
	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	/**
	 *
	 * @return
	 *     The owner
	 */
	@JsonProperty("owner")
	public Owner getOwner() {
		return owner;
	}

	/**
	 *
	 * @param owner
	 *     The owner
	 */
	@JsonProperty("owner")
	public void setOwner(final Owner owner) {
		this.owner = owner;
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
	 *     The isPrivate
	 */
	@JsonProperty("is_private")
	public Boolean getIsPrivate() {
		return isPrivate;
	}

	/**
	 *
	 * @param isPrivate
	 *     The is_private
	 */
	@JsonProperty("is_private")
	public void setIsPrivate(final Boolean isPrivate) {
		this.isPrivate = isPrivate;
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

}
