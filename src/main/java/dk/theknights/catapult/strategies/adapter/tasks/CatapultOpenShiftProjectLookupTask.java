package dk.theknights.catapult.strategies.adapter.tasks;

import com.openshift.restclient.model.IProject;
import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.services.OpenShiftService;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * This task will lookup a project in OpenShift. If it is found current context is updated.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class CatapultOpenShiftProjectLookupTask implements CatapultAdapterTask {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Accepts the following states: CATAPULT_TEMPLATE_FOUND
	 *
	 * @param state Current catapult state
	 * @return true if state is CATAPULT_TEMPLATE_FOUND, otherwise false
	 */
	@Override
	public boolean accept(final CatapultStateEnum state) {
		if (state.equals(CatapultStateEnum.CATAPULT_TEMPLATE_FOUND)) {
			return true;
		}
		return false;
	}

	/**
	 * Lookup project from Catapult context in OpenShift. If it is found context is updated.
	 *
	 * @param context Current Catapult context
	 */
	public void perform(final CatapultContext context) {
		try {
			String projectname = context.getWebhook().getOpenShiftProjectName();
			if (projectname != null) {
				OpenShiftService openshiftService = getOpenShiftService();
				IProject project = openshiftService.getProject(projectname);
				if (project != null) {
					context.setNewProject(false);
					logger.info(": Found project (" + project.getNamespace() + ")");
				} else {
					logger.info("Project (" + projectname + ") not found!");
				}
				context.setOpenShiftProject(project);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected OpenShiftService getOpenShiftService() {
		return new OpenShiftService();
	}

}
