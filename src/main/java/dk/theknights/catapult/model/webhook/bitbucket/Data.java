package dk.theknights.catapult.model.webhook.bitbucket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"push",
		"actor",
		"repository"
})
public class Data {

	@JsonProperty("push")
	private Push push;
	@JsonProperty("actor")
	private Actor actor;
	@JsonProperty("repository")
	private Repository repository;

	/**
	 *
	 * @return
	 *     The push
	 */
	@JsonProperty("push")
	public Push getPush() {
		return push;
	}

	/**
	 *
	 * @param push
	 *     The push
	 */
	@JsonProperty("push")
	public void setPush(final Push push) {
		this.push = push;
	}

	/**
	 *
	 * @return
	 *     The actor
	 */
	@JsonProperty("actor")
	public Actor getActor() {
		return actor;
	}

	/**
	 *
	 * @param actor
	 *     The actor
	 */
	@JsonProperty("actor")
	public void setActor(final Actor actor) {
		this.actor = actor;
	}

	/**
	 *
	 * @return
	 *     The repository
	 */
	@JsonProperty("repository")
	public Repository getRepository() {
		return repository;
	}

	/**
	 *
	 * @param repository
	 *     The repository
	 */
	@JsonProperty("repository")
	public void setRepository(final Repository repository) {
		this.repository = repository;
	}

}
