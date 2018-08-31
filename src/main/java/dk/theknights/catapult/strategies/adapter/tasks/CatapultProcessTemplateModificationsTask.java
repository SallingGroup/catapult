package dk.theknights.catapult.strategies.adapter.tasks;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Applies Catapult template modification to OpenShoft project.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 4/20/18.
 */
public class CatapultProcessTemplateModificationsTask implements CatapultAdapterTask {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Accepts no states
	 *
	 * @param state Current catapult state
	 * @return false
	 */
	@Override
	public boolean accept(final CatapultStateEnum state) {
		return false;
	}

	/**
	 * Not implemented yet
	 * @param context Current Catapult context
	 */
	@Override
	public void perform(final CatapultContext context) {
		logger.info("Not implemented!");
	}
}
