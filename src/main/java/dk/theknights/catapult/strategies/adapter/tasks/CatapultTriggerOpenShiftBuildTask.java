package dk.theknights.catapult.strategies.adapter.tasks;

import com.openshift.restclient.model.IBuild;
import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.services.OpenShiftService;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Annotated builds are triggered when catapult webhook is activated.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class CatapultTriggerOpenShiftBuildTask implements CatapultAdapterTask {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Accepts states: NO_CONFIGMAPS_FOUND, CONFIGMAPS_UPDATED, CATAPULT_TEMPLATE_NOT_CHANGED
	 * @param state Current catapult state
	 * @return true if state is NO_CONFIGMAPS_FOUND, CONFIGMAPS_UPDATED, CATAPULT_TEMPLATE_NOT_CHANGED, otherwise false
	 */
	@Override
	public boolean accept(final CatapultStateEnum state) {
		if (state.equals(CatapultStateEnum.CATAPULT_TEMPLATE_CHANGED)
			|| state.equals(CatapultStateEnum.CATAPULT_TEMPLATE_PROCESSED)
			|| state.equals(CatapultStateEnum.CATAPULT_TEMPLATE_NOT_CHANGED)) {
			return true;
		}
		return false;
	}

	/**
	 * Annoteted builds are triggered in OpenShift and the Catapult context is updated with builds that have been triggered.
	 *
	 * @param context Current Catapult context
	 */
	@Override
	public void perform(final CatapultContext context) {
		try {
			IBuild build = getOpenShiftService().triggerBuild(context.getWebhook().getRepositoryUrl(), context.getWebhook().getCommitId(), context.getOpenShiftProject());
			List<IBuild> buildsTriggered = new ArrayList<IBuild>();
			if (build != null) {
				logger.info(context.getId() + ": Triggering build (" + build.getName() + ") in project (" + context.getOpenShiftProject().getNamespace() + ")");
				buildsTriggered.add(build);
			}
			context.setBuildsTriggered(buildsTriggered);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	protected OpenShiftService getOpenShiftService() {
		return new OpenShiftService();
	}

}
