package dk.theknights.catapult.strategies.adapter.tasks;

import com.openshift.restclient.OpenShiftException;
import com.openshift.restclient.model.IProject;
import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.config.CatapultConfig;
import dk.theknights.catapult.model.webhook.AbstractWebHook;
import dk.theknights.catapult.model.webhook.RequestTypeEnum;
import dk.theknights.catapult.services.OpenShiftService;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * This task will remove an OpenShift project and update the current Catapult context.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class CatapultDeleteOpenShiftProjectTask implements CatapultAdapterTask {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Accepts the following states: OPENSHIFT_PROJECT_FOUND, RELEASE_PROJECT_FOUND
	 *
	 * @param state Current catapult state
	 * @return true if state is OPENSHIFT_PROJECT_FOUND or RELEASE_PROJECT_FOUND, otherwise false
	 */
	@Override
	public boolean accept(final CatapultStateEnum state) {
		if (state == CatapultStateEnum.OPENSHIFT_PROJECT_FOUND
			|| state == CatapultStateEnum.RELEASE_PROJECT_FOUND) {
			return true;
		}
		return false;
	}

	/**
	 * Remove OpenShift project. Target project is taken from Catapult context.
	 * This task will wait for OpenShift to completely remove the project.
	 *
	 * @param context Current Catapult context
	 */
	@Override
	public void perform(final CatapultContext context) {
		AbstractWebHook webhook = context.getWebhook();
		if (webhook.getRequestType() == RequestTypeEnum.RELEASE_REQUEST
			|| webhook.getRequestType() == RequestTypeEnum.PULL_REQUEST) {
			try {
				OpenShiftService openshiftService = getOpenShiftService(context.getCatapultConfig());
				openshiftService.deleteProject(context.getOpenShiftProject());

				// wait for project to be gone!
				boolean gone = false;
				while (!gone) {
					try {
						IProject project = openshiftService.getProject(context.getOpenShiftProject().getNamespace());
						if (project == null) {
							gone = true;
						}
					} catch (IOException ioe) {
						gone = true;
					}
				}

				logger.info("Project (" + context.getOpenShiftProject().getNamespace() + ") deleted.");
				context.setOpenShiftProject(null);
			} catch (IOException e) {
				logger.error("Unable to delete project (" + context.getOpenShiftProject().getNamespace() + ")", e);
			} catch (OpenShiftException ope) {
				logger.warn("Project (" + context.getOpenShiftProject().getNamespace() + ") is already marked for deletion");
			}
		} else {
			logger.warn("I am only doing delete project for pull- and release requests");
		}
	}

	protected OpenShiftService getOpenShiftService(final  CatapultConfig configuration) {
		OpenShiftService service = new OpenShiftService();
		service.setCatapultConfig(configuration);
		return service;
	}

}
