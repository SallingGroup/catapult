
package dk.theknights.catapult.model.webhook.bitbucket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"changes"
})
public class Push {

	@JsonProperty("changes")
	private List<Change> changes = new ArrayList<Change>();

	/**
	 *
	 * @return
	 *     The changes
	 */
	@JsonProperty("changes")
	public List<Change> getChanges() {
		return changes;
	}

	/**
	 *
	 * @param changes
	 *     The changes
	 */
	@JsonProperty("changes")
	public void setChanges(final List<Change> changes) {
		this.changes = changes;
	}

}
