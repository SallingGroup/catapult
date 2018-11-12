package dk.theknights.catapult.strategies.adapter;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.CatapultException;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultAdapterTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultProcessTemplateChangeTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultProcessTemplateTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultStatisticsTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultTriggerOpenShiftBuildTask;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * This adapter holds tasks that executes on template states.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/7/18.
 */
public class CatapultTemplateAdapter implements CatapultAdapter {

	private List<CatapultAdapterTask> tasks = new ArrayList<CatapultAdapterTask>();

	/**
	 * Initializes with tasks that will execute on template states.
	 */
	public CatapultTemplateAdapter() {
		tasks.add(new CatapultProcessTemplateChangeTask());
		tasks.add(new CatapultProcessTemplateTask());
		tasks.add(new CatapultTriggerOpenShiftBuildTask());
		tasks.add(new CatapultStatisticsTask());
	}

	protected List<CatapultAdapterTask> getTasks() {
		return tasks;
	}

	/**
	 * Aceept the following states:
	 * CATAPULT_TEMPLATE_NOT_CHANGED,
	 * CATAPULT_TEMPLATE_CHANGED,
	 * NO_CONFIGMAPS_FOUND,
	 * CONFIGMAPS_UPDATED
	 *
	 * @param context Current catapult context
	 * @return return true if OpenshiftProject exists in the context and state is one of the following: CATAPULT_TEMPLATE_NOT_CHANGED, CATAPULT_TEMPLATE_CHANGED, NO_CONFIGMAPS_FOUND, CONFIGMAPS_UPDATED
	 */
	public boolean accept(final CatapultContext context) {
		if (
				(context.getCatapultState() == CatapultStateEnum.CATAPULT_TEMPLATE_NOT_CHANGED
				|| context.getCatapultState() == CatapultStateEnum.CATAPULT_TEMPLATE_CHANGED
				|| context.getCatapultState() == CatapultStateEnum.CATAPULT_TEMPLATE_PROCESSED
				|| context.getCatapultState() == CatapultStateEnum.NO_CONFIGMAPS_FOUND
				|| context.getCatapultState() == CatapultStateEnum.CONFIGMAPS_UPDATED)
				&& context.getOpenShiftProject() != null
			) {
			return true;
		}
		return false;
	}

	/**
	 * Execute tasks that apply to the template state.
	 *
	 * @param context Current Catapult context
	 */
	public void process(final CatapultContext context) {
		for (CatapultAdapterTask task: getTasks()) {
			if (task.accept(context.getCatapultState())) {
				try {
					task.perform(context);
				} catch (CatapultException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
