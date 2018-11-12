package dk.theknights.catapult.strategies.adapter.tasks;

import com.openshift.restclient.model.IProject;
import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.CatapultException;
import dk.theknights.catapult.services.OpenShiftService;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import dk.theknights.catapult.strategies.state.InvalidCatapultStateException;

import java.io.IOException;

/**
 * This task is responsible for creating OpenShift projects.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class CatapultCreateOpenShiftProjectTask implements CatapultAdapterTask {

	/**
	 * Accepts Catapult state OPENSHIFT_PROJECT_NOT_FOUND.
	 *
	 * @param state Current catapult state
	 * @return true if current Catapult state is OPENSHIFT_PROJECT_NOT_FOUND, otherwise false
	 */
	@Override
	public boolean accept(final CatapultStateEnum state) {
		if (state.equals(CatapultStateEnum.OPENSHIFT_PROJECT_NOT_FOUND)) {
			return true;
		}
		return false;
	}

	/**
	 * Create OpenShift project and update current Catapult context.
	 * Current Catapult context can not have a project already, if so this task will not do anything.
	 *
	 * @param context Current Catapult context
	 */
	@Override
	public void perform(final CatapultContext context) throws CatapultException {
		if (context.getOpenShiftProject() == null) {
			try {
				OpenShiftService openshiftService = getOpenShiftService();
				openshiftService.setCatapultConfig(context.getCatapultConfig());
				IProject project = openshiftService.createProject(context.getWebhook().getOpenShiftProjectName());

				context.setOpenShiftProject(project);
				context.setNewProject(true);
			} catch (IOException e) {
				context.setCatapultState(CatapultStateEnum.CATAPULT_DONE);
				throw new InvalidCatapultStateException(e);
			}
		}
	}

	protected OpenShiftService getOpenShiftService() {
		return new OpenShiftService();
	}

}
