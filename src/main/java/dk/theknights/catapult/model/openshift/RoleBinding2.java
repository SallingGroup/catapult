
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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"metadata",
	"userNames",
	"groupNames",
	"subjects",
	"roleRef"
})
public class RoleBinding2 {

	@JsonProperty("metadata")
	private Metadata2 metadata;
	@JsonProperty("userNames")
	private Object userNames;
	@JsonProperty("groupNames")
	private List<String> groupNames = null;
	@JsonProperty("subjects")
	private List<Subject> subjects = null;
	@JsonProperty("roleRef")
	private RoleRef roleRef;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("metadata")
	public Metadata2 getMetadata() {
		return metadata;
	}

	@JsonProperty("metadata")
	public void setMetadata(final Metadata2 metadata) {
		this.metadata = metadata;
	}

	@JsonProperty("userNames")
	public Object getUserNames() {
		return userNames;
	}

	@JsonProperty("userNames")
	public void setUserNames(final Object userNames) {
		this.userNames = userNames;
	}

	@JsonProperty("groupNames")
	public List<String> getGroupNames() {
		return groupNames;
	}

	@JsonProperty("groupNames")
	public void setGroupNames(final List<String> groupNames) {
		this.groupNames = groupNames;
	}

	@JsonProperty("subjects")
	public List<Subject> getSubjects() {
		return subjects;
	}

	@JsonProperty("subjects")
	public void setSubjects(final List<Subject> subjects) {
		this.subjects = subjects;
	}

	@JsonProperty("roleRef")
	public RoleRef getRoleRef() {
		return roleRef;
	}

	@JsonProperty("roleRef")
	public void setRoleRef(final RoleRef roleRef) {
		this.roleRef = roleRef;
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
