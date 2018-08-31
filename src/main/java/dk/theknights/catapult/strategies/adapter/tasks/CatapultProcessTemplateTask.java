package dk.theknights.catapult.strategies.adapter.tasks;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.services.OpenShiftService;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This task will process the template from the Catapult context. The template will be processed in OpenShift through the
 * OpenShift service.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class CatapultProcessTemplateTask implements CatapultAdapterTask {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Accepts states CONFIGMAPS_UPDATED, NO_CONFIGMAPS_FOUND
	 * @param state Current catapult state
	 * @return
	 */
	@Override
	public boolean accept(final CatapultStateEnum state) {
		if (state.equals(CatapultStateEnum.CONFIGMAPS_UPDATED)
			|| state.equals(CatapultStateEnum.NO_CONFIGMAPS_FOUND)) {
			return true;
		}
		return false;
	}

	/**
	 * Process template from Catapult context.
	 * Before the template is processed, the parameter SOURCE_REPOSITORY_SECRET is replaced with the pipeline source secret
	 * configured for the Catapult. The following parameters are also updated. SOURCE_REPOSITORY_REF is updated with the
	 * repository url from the webhook. NAMESPACE is replaced with the namespace from the Catapult context project namespace.
	 *
	 * @param context Current Catapult context
	 */
	@Override
	public void perform(final CatapultContext context) {
		if (context.isNewProject()) {
			try {
				OpenShiftService openshiftService = getOpenShiftService();
				openshiftService.setCatapultConfig(context.getCatapultConfig());

				String jsonTemplate = context.getCatapultTemplate().toString();
				List<Pair<String, String>> parameters = new ArrayList<>();
				parameters.add(Pair.of("SOURCE_REPOSITORY_REF", context.getWebhook().getRepositoryGitSshUrl()));
				parameters.add(Pair.of("NAMESPACE", context.getOpenShiftProject().getNamespace()));

				JSONObject json = new JSONObject(jsonTemplate);

				JSONArray jsonArray = json.getJSONArray("parameters");
				JSONObject sourcereposecret = new JSONObject("{name: \"SOURCE_REPOSITORY_SECRET\", value: \"" + context.getCatapultConfig().getPipelineSourceSecretName(context.getRepositoryType()) + "\"}");

				jsonArray.put(sourcereposecret);
				jsonTemplate = json.toString();

				openshiftService.processTemplate(jsonTemplate, context.getOpenShiftProject(), parameters);
			} catch (IOException e) {
				logger.error(context.getId() + ": Unable to process catapult template for project(" + context.getOpenShiftProject().getNamespace() + ")!", e);
			}
		} else {
			logger.warn("I will only process the complete template if project is new!");
		}
	}

	protected OpenShiftService getOpenShiftService() {
		return new OpenShiftService();
	}

}
