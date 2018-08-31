package dk.theknights.catapult.model.openshift;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PolicyBinding model class for reading and manipulating OpenShift PolicyBindings.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 * */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"kind",
	"apiVersion",
	"metadata",
	"lastModified",
	"policyRef",
	"roleBindings"
})
public class PolicyBinding {

	@JsonProperty("kind")
	private String kind;
	@JsonProperty("apiVersion")
	private String apiVersion;
	@JsonProperty("metadata")
	private Metadata metadata;
	@JsonProperty("lastModified")
	private String lastModified;
	@JsonProperty("policyRef")
	private PolicyRef policyRef;
	@JsonProperty("roleBindings")
	private List<RoleBinding2> roleBindings = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("kind")
	public String getKind() {
		return kind;
	}

	@JsonProperty("kind")
	public void setKind(final String kind) {
		this.kind = kind;
	}

	@JsonProperty("apiVersion")
	public String getApiVersion() {
		return apiVersion;
	}

	@JsonProperty("apiVersion")
	public void setApiVersion(final String apiVersion) {
		this.apiVersion = apiVersion;
	}

	@JsonProperty("metadata")
	public Metadata getMetadata() {
		return metadata;
	}

	@JsonProperty("metadata")
	public void setMetadata(final Metadata metadata) {
		this.metadata = metadata;
	}

	@JsonProperty("lastModified")
	public String getLastModified() {
		return lastModified;
	}

	@JsonProperty("lastModified")
	public void setLastModified(final String lastModified) {
		this.lastModified = lastModified;
	}

	@JsonProperty("policyRef")
	public PolicyRef getPolicyRef() {
		return policyRef;
	}

	@JsonProperty("policyRef")
	public void setPolicyRef(final PolicyRef policyRef) {
		this.policyRef = policyRef;
	}

	@JsonProperty("roleBindings")
	public List<RoleBinding2> getRoleBindings() {
		return roleBindings;
	}

	@JsonProperty("roleBindings")
	public void setRoleBindings(final List<RoleBinding2> roleBindings) {
		this.roleBindings = roleBindings;
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
