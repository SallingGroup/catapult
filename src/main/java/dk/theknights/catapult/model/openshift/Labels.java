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
	"app",
	"template"
})
public class Labels {

	@JsonProperty("app")
	private String app;
	@JsonProperty("template")
	private String template;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("app")
	public String getApp() {
		return app;
	}

	@JsonProperty("app")
	public void setApp(final String app) {
		this.app = app;
	}

	@JsonProperty("template")
	public String getTemplate() {
		return template;
	}

	@JsonProperty("template")
	public void setTemplate(final String template) {
		this.template = template;
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
