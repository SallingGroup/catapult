package dk.theknights.catapult.strategies.adapter.tasks;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Trigger custom project cleanup when an OpenShift project has been deleted.
 * This is a way for developers to do some housekeeping with their own projects.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 4/24/18.
 */
public class CleanupTriggerTask implements CatapultAdapterTask {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 *  Accepts sate OPENSHIFT_PROJECT_DELETED
	 * @param state Current catapult state
	 * @return true if state is OPENSHIFT_PROJECT_DELETED, otherwise false
	 */
	@Override
	public boolean accept(final CatapultStateEnum state) {
		if (state.equals(CatapultStateEnum.OPENSHIFT_PROJECT_DELETED)) {
			return true;
		}
		return false;
	}

	/**
	 * Trigger custom cleanup tasks because the OpenShift project has been deleted.
	 *
	 * @param context Current Catapult context
	 */
	@Override
	public void perform(final CatapultContext context) {
		logger.info("Trigger project cleanup tasks! ***** This is not implemented yet *****");
	}

}
