package dk.theknights.catapult.system;

import dk.theknights.catapult.model.webhook.bitbucket.BitbucketWebhook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * This controller is used for validating a webhook request. It is triggered in integration test pipeline where it is
 * validated that the json that was posted is also serialized back as response from this controller.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
@Controller
public class ValidateWebhookController {

	/**
	 * This endpoint is used to validate the bitbucket webhook.
	 *
	 * @param request httpservlet request
	 * @param response httpservlet response
	 * @param body Bitbucket webhook model
	 * @param model spring model
	 * @return Bitbucket webhook model as json
	 */
	@RequestMapping(value = "/bitbucket/validatewebhook", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody BitbucketWebhook genericBitbucketWebhook(final HttpServletRequest request, final HttpServletResponse response, final @RequestBody(required = true) BitbucketWebhook body, final Map<String, Object> model) {
		body.setStatus("200");
		return body;
	}

}
