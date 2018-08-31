package dk.theknights.catapult.strategies.adapter.tasks;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.services.OpenShiftService;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Prefix the OpenShift display name with RELEASE.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 5/4/18.
 */
public class ReleaseProjectUpdateDisplayNameTask implements CatapultAdapterTask {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Accept state OPENSHIFT_PROJECT_CREATED.
	 *
	 * @param state Current catapult state
	 * @return true if state is OPENSHIFT_PROJECT_CREATED, otherwise false
	 */
	@Override
	public boolean accept(final CatapultStateEnum state) {
		if (state.equals(CatapultStateEnum.OPENSHIFT_PROJECT_CREATED)) {
			return true;
		}
		return false;
	}

	/**
	 * Prefix the OpenShift display name with RELEASE.
	 *
	 * @param context Current Catapult context
	 */
	@Override
	public void perform(final CatapultContext context) {
		try {
			logger.info(context.getId() + ": Updating project (" + context.getOpenShiftProject().getNamespace() + ") displayName (" + context.getOpenShiftProject().getDisplayName() + ")");
			OpenShiftService openshiftService = getOpenShiftService();
			openshiftService.setCatapultConfig(context.getCatapultConfig());
			String newDisplayName = "RELEASE " + context.getOpenShiftProject().getNamespace();
			openshiftService.updateProjectDisplayName(context.getOpenShiftProject(), newDisplayName);
		} catch (IOException e) {
			logger.error(context.getId() + ": Unable to update project displayName for project(" + context.getOpenShiftProject().getNamespace() + ")!", e);
		}
	}

	protected OpenShiftService getOpenShiftService() {
		return new OpenShiftService();
	}

}
