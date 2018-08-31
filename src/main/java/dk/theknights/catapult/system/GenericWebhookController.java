package dk.theknights.catapult.system;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.config.CatapultConfig;
import dk.theknights.catapult.model.webhook.bitbucket.BitbucketWebhook;
import dk.theknights.catapult.model.webhook.github.GitHubWebhook;
import dk.theknights.catapult.model.webhook.gitlab.GitLabWebhook;
import dk.theknights.catapult.strategies.PullRequestStrategy;
import dk.theknights.catapult.strategies.PushStrategy;
import dk.theknights.catapult.strategies.ReleaseStrategy;
import dk.theknights.catapult.strategies.state.InvalidCatapultStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * This controller is the entrypoint for the webhooks. It has bitbucket and gitlab webhooks implemneted and will start the Catapult state machine when activated.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
@Controller
class GenericWebhookController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CatapultConfig configuration = null;

	/**
	 * Bitbucket webhook entrypoint.
	 *
	 * @param request HttpServletRequest that activated this controller
	 * @param response HttpServletResponse from this controller
	 * @param body BitbucketWebhook serialized from the posted json from Bitbucket
	 * @param model Model for passing data to the response
	 * @return genericwebhook page
	 */
	@RequestMapping(value = "/bitbucket/genericwebhook", method = RequestMethod.POST)
	public @ResponseBody BitbucketWebhook genericBitbucketWebhook(final HttpServletRequest request, final HttpServletResponse response, @RequestBody(required = true) final BitbucketWebhook body, final Map<String, Object> model) {

		body.setCatapultConfig(configuration);
		CatapultContext context = new CatapultContext();
		context.setCatapultConfig(configuration);
		context.setWebhook(body);
		logger.info(context.getId() + ": X-FORWARDED-FOR: " + request.getHeader("X-FORWARDED-FOR"));
		logger.info(context.getId() + ": remoteAddr: " + request.getRemoteAddr());

		try {
			switch (body.getRequestType()) {
				case PUSH_REQUEST:
					logger.info("Handle pushrequest webhook.getRequestType (" + body.getRequestType() + ")");
					PushStrategy pushstrategy = new PushStrategy();
					pushstrategy.execute(context);
					body.setStatus("200");
					break;
				case PULL_REQUEST:
					logger.info("Handle pullrequest webhook.getRequestType (" + body.getRequestType() + ")");
					PullRequestStrategy strategy = new PullRequestStrategy();
					strategy.execute(context);
					body.setStatus("200");
					break;
				case RELEASE_REQUEST:
					logger.error("Handle release webhook.getRequestType (" + body.getRequestType() + ")");
					ReleaseStrategy releaseStrategy = new ReleaseStrategy();
					releaseStrategy.execute(context);
					body.setStatus("200");
					break;
				default:
					logger.error("Invalid request posted. I choose to ignore this!");
					body.setStatus("500");
					break;
			}
		} catch (InvalidCatapultStateException e) {
			logger.error("Problem with catapult transitions", e);
			body.setStatus("500");
		}

		return body;
	}

	/**
	 * Webhook for GitLab.
	 *
	 * @param request HttpServletRequest that activated this controller
	 * @param response HttpServletResponse that will be written to
	 * @param body GitLabWebhook serialized from the posted json from GitLab
	 * @param model Model for passing data to the response
	 * @return genericwebhook page
	 */
	@RequestMapping(value = "/gitlab/genericwebhook", method = RequestMethod.POST)
	public String genericGitLabWebhook(final HttpServletRequest request, final HttpServletResponse response, @RequestBody(required = true) final GitLabWebhook body, final Map<String, Object> model) {
		CatapultContext context = new CatapultContext();
		context.setCatapultConfig(configuration);
		context.setWebhook(body);
		logger.info("X-FORWARDED-FOR: " + request.getHeader("X-FORWARDED-FOR"));
		logger.info("remoteAddr: " + request.getRemoteAddr());

		try {
			switch (body.getRequestType()) {
				case PUSH_REQUEST:
					logger.info("Handle pushrequest webhook.getRequestType (" + body.getRequestType() + ")");
					PushStrategy pushstrategy = new PushStrategy();
					pushstrategy.execute(context);
					body.setStatus("200");
					break;
				case PULL_REQUEST:
					logger.info("Handle pullrequest webhook.getRequestType (" + body.getRequestType() + ")");
					PullRequestStrategy strategy = new PullRequestStrategy();
					strategy.execute(context);
					body.setStatus("200");
					break;
				case RELEASE_REQUEST:
					logger.error("Handle release webhook.getRequestType (" + body.getRequestType() + ")");
					ReleaseStrategy releaseStrategy = new ReleaseStrategy();
					releaseStrategy.execute(context);
					body.setStatus("200");
					break;
				default:
					logger.error("Invalid request posted. I choose to ignore this!");
					body.setStatus("500");
					break;
			}
		} catch (InvalidCatapultStateException e) {
			e.printStackTrace();
			body.setStatus("500");
		}

		return "genericwebhook";
	}

	/**
	 * Webhook for GitHUb. This is not implemented yet.
	 *
	 * @param request HttpServletRequest that activated this controller
	 * @param response HttpServletResponse from this controller
	 * @param body GitHubWebhook serialized from the posted json from GitHub
	 * @param model Model for passing data to the response
	 * @return genericwebhook page
	 */
	@RequestMapping(value = "/github/genericwebhook", method = RequestMethod.POST)
	public String genericGitHubWebhook(final HttpServletRequest request, final HttpServletResponse response, @RequestBody(required = true) final GitHubWebhook body, final Map<String, Object> model) {
		logger.warn("genericwebhook for github not implemented...");

		logger.info("X-FORWARDED-FOR: " + request.getHeader("X-FORWARDED-FOR"));
		logger.info("remoteAddr: " + request.getRemoteAddr());
		return "genericwebhook";
	}

}
