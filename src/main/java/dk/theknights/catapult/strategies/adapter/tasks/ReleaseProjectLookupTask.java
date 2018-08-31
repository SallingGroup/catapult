package dk.theknights.catapult.strategies.adapter.tasks;

import com.openshift.restclient.model.IProject;
import com.openshift.restclient.model.IResource;
import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.services.OpenShiftService;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * This task will lookup the special release project in OpenShift. If it is found the Catapult context is updated.
 * Since it is not possible to annotate or tag projects in OpenShift the displayName is modified to prefix RELEASE.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 5/1/18.
 */
public class ReleaseProjectLookupTask implements CatapultAdapterTask {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Accept state INITIAL
	 *
	 * @param state Current catapult state
	 * @return true if state is INITIAL, otherwise false
	 */
	@Override
	public boolean accept(final CatapultStateEnum state) {
		if (state == CatapultStateEnum.INITIAL) {
			return true;
		}
		return false;
	}

	/**
	 * Iterate through all projects in OpenShift and look for the special release project.
	 * If it is found the Catapult context will be updated.
	 *
	 * @param context Current Catapult context
	 */
	@Override
	public void perform(final CatapultContext context) {
		String name = context.getWebhook().getRepositoryName();

		OpenShiftService openshiftService = getOpenShiftService();
		try {
			Collection<IResource> allprojects = openshiftService.getAllProjects();
			Iterator<IResource> projectIt = allprojects.iterator();
			boolean found = false;
			while (projectIt.hasNext() && !found) {
				IProject nextProject = (IProject) projectIt.next();
				String projectDisplayName = nextProject.getDisplayName();
				if (projectDisplayName != null
						&& projectDisplayName.startsWith("RELEASE " + name)) {
					context.setOpenShiftProject(nextProject);
					found = true;
				}
			}
		} catch (IOException e) {
			logger.error("Unable to communicate with OpenShift", e);
		}
	}

	protected OpenShiftService getOpenShiftService() {
		return new OpenShiftService();
	}

}
