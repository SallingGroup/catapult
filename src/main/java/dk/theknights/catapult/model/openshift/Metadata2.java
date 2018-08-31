
package dk.theknights.catapult.model.openshift;

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
	"namespace",
	"uid",
	"resourceVersion",
	"creationTimestamp",
	"labels"
})
public class Metadata2 {

	@JsonProperty("name")
	private String name;
	@JsonProperty("namespace")
	private String namespace;
	@JsonProperty("uid")
	private String uid;
	@JsonProperty("resourceVersion")
	private String resourceVersion;
	@JsonProperty("creationTimestamp")
	private String creationTimestamp;
	@JsonProperty("labels")
	private Labels labels;
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

	@JsonProperty("namespace")
	public String getNamespace() {
		return namespace;
	}

	@JsonProperty("namespace")
	public void setNamespace(final String namespace) {
		this.namespace = namespace;
	}

	@JsonProperty("uid")
	public String getUid() {
		return uid;
	}

	@JsonProperty("uid")
	public void setUid(final String uid) {
		this.uid = uid;
	}

	@JsonProperty("resourceVersion")
	public String getResourceVersion() {
		return resourceVersion;
	}

	@JsonProperty("resourceVersion")
	public void setResourceVersion(final String resourceVersion) {
		this.resourceVersion = resourceVersion;
	}

	@JsonProperty("creationTimestamp")
	public String getCreationTimestamp() {
		return creationTimestamp;
	}

	@JsonProperty("creationTimestamp")
	public void setCreationTimestamp(final String creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	@JsonProperty("labels")
	public Labels getLabels() {
		return labels;
	}

	@JsonProperty("labels")
	public void setLabels(final Labels labels) {
		this.labels = labels;
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
