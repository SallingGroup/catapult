package dk.theknights.catapult.strategies.adapter;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.CatapultException;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultAdapterTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultCreateOpenShiftProjectTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultDetectTemplateChangeTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultOpenShiftApplyPolicyBindingTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultOpenShiftProjectLookupTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultStatisticsTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultUpdateOpenShiftProjectRequester;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * This adapter holds tasks that will be activated on OpenShift project states.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/7/18.
 */
public class CatapultOpenShiftProjectAdapter implements CatapultAdapter {

	private List<CatapultAdapterTask> tasks = new ArrayList<CatapultAdapterTask>();

	/**
	 * Initializes with tasks that need to run on OpenShiftProject states.
	 */
	public CatapultOpenShiftProjectAdapter() {
		tasks.add(new CatapultDetectTemplateChangeTask());
		tasks.add(new CatapultOpenShiftProjectLookupTask());
		tasks.add(new CatapultCreateOpenShiftProjectTask());
		tasks.add(new CatapultUpdateOpenShiftProjectRequester());
		tasks.add(new CatapultOpenShiftApplyPolicyBindingTask());
		tasks.add(new CatapultStatisticsTask());
	}

	protected List<CatapultAdapterTask> getTasks() {
		return tasks;
	}

	/**
	 * Determine if state is acceptable.
	 *
	 * @param context Current catapult context
	 * @return true if state is one of the following: OPENSHIFT_PROJECT_NOT_FOUND, OPENSHIFT_PROJECT_FOUND, OPENSHIFT_PROJECT_CREATED, CATAPULT_TEMPLATE_FOUND
	 * otherwise return false
	 */
	public boolean accept(final CatapultContext context) {
		if (
			context.getCatapultState() == CatapultStateEnum.OPENSHIFT_PROJECT_NOT_FOUND
			|| context.getCatapultState() == CatapultStateEnum.OPENSHIFT_PROJECT_FOUND
			|| context.getCatapultState() == CatapultStateEnum.OPENSHIFT_PROJECT_CREATED
			|| context.getCatapultState() == CatapultStateEnum.CATAPULT_TEMPLATE_FOUND
			) {
			return true;
		}
		return false;
	}

	/**
	 * Process tasks that need to execute on the following states:
	 * OPENSHIFT_PROJECT_NOT_FOUND
	 * OPENSHIFT_PROJECT_FOUND
	 * OPENSHIFT_PROJECT_CREATED
	 * CATAPULT_TEMPLATE_FOUND
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
