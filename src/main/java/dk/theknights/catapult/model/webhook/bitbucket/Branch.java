package dk.theknights.catapult.model.webhook.bitbucket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"name"
})
public class Branch {

	@JsonProperty("name")
	private String name;

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(final String name) {
		this.name = name;
	}

}
