package dk.theknights.catapult.strategies.adapter.tasks;

import com.openshift.restclient.model.IProject;
import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.services.OpenShiftService;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * OpenShift project display name is updated to reflect the user that requested the project creation.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/12/18.
 */
public class CatapultUpdateOpenShiftProjectRequester implements CatapultAdapterTask {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Accepts state OPENSHIFT_PROJECT_NOT_FOUND
	 * @param state Current catapult state
	 * @return true if state is OPENSHIFT_PROJECT_NOT_FOUND, otherwise false
	 */
	@Override
	public boolean accept(final CatapultStateEnum state) {
		if (state.equals(CatapultStateEnum.OPENSHIFT_PROJECT_NOT_FOUND)) {
			return true;
		}
		return false;
	}

	/**
	 * Update OpenShift project display name with user information from webhook. Catapult context is updated to reflect this change.
	 *
	 * @param context Current Catapult context
	 */
	@Override
	public void perform(final CatapultContext context) {
		try {
			logger.info(context.getId() + ": Updating project (" + context.getOpenShiftProject().getNamespace() + ") requester (" + context.getWebhook().getRequester() + ")");
			OpenShiftService openshiftService = getOpenShiftService();
			openshiftService.setCatapultConfig(context.getCatapultConfig());
			openshiftService.updateProjectRequester(context.getOpenShiftProject(), context.getWebhook().getRequester());

			// get the updated project
			IProject project = openshiftService.getProject(context.getOpenShiftProject().getNamespace());
			context.setOpenShiftProject(project);
		} catch (IOException e) {
			logger.error(context.getId() + ": Unable to update project requester for project(" + context.getOpenShiftProject().getNamespace() + ")!", e);
		}
	}

	protected OpenShiftService getOpenShiftService() {
		return new OpenShiftService();
	}

}
