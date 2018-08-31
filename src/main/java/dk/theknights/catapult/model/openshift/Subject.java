
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
	"kind",
	"name",
	"namespace"
})
public class Subject {

	@JsonProperty("kind")
	private String kind;
	@JsonProperty("name")
	private String name;
	@JsonProperty("namespace")
	private String namespace;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Subject(final String kind, final String name, final String namespace) {
		this.kind = kind;
		this.name = name;
		this.namespace = namespace;
	}

	@JsonProperty("kind")
	public String getKind() {
		return kind;
	}

	@JsonProperty("kind")
	public void setKind(final String kind) {
		this.kind = kind;
	}

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

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(final String name, final Object value) {
		this.additionalProperties.put(name, value);
	}

}
