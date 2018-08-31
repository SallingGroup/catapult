package dk.theknights.catapult.strategies.adapter;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.CatapultException;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultAdapterTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultDeleteOpenShiftProjectTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultFetchTemplateTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultOpenShiftProjectLookupTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultStatisticsTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultValidateConfigTask;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import dk.theknights.catapult.strategies.state.InvalidCatapultStateException;

import java.util.ArrayList;
import java.util.List;

/**
 * This adapter holds all tasks that need to execute in the entire pull request strategy.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 4/19/18.
 */
public class PullRequestAdapter implements CatapultAdapter {

	private List<CatapultAdapterTask> tasks = new ArrayList<CatapultAdapterTask>();

	/**
	 * Initializes with all tasks that are used in the pull request strategy.
	 */
	public PullRequestAdapter() {
		tasks.add(new CatapultStatisticsTask());
		tasks.add(new CatapultValidateConfigTask());
		tasks.add(new CatapultFetchTemplateTask());
		tasks.add(new CatapultOpenShiftProjectLookupTask());
		tasks.add(new CatapultDeleteOpenShiftProjectTask());
		tasks.add(new CatapultStatisticsTask());
	}

	protected List<CatapultAdapterTask> getTasks() {
		return tasks;
	}

	/**
	 * Accept the following states:
	 * INITIAL,
	 * OPENSHIFT_PROJECT_NOT_FOUND,
	 * OPENSHIFT_PROJECT_FOUND,
	 * CATAPULT_TEMPLATE_FOUND
	 *
	 * @param context Current catapult context
	 * @return return true if state is one of the following: INITIAL, OPENSHIFT_PROJECT_NOT_FOUND, OPENSHIFT_PROJECT_FOUND, CATAPULT_TEMPLATE_FOUND
	 * otherwise return false
	 */
	@Override
	public boolean accept(final CatapultContext context) {
		if (context.getCatapultState() == CatapultStateEnum.INITIAL
			|| context.getCatapultState() == CatapultStateEnum.OPENSHIFT_PROJECT_NOT_FOUND
			|| context.getCatapultState() == CatapultStateEnum.OPENSHIFT_PROJECT_FOUND
			|| context.getCatapultState() == CatapultStateEnum.CATAPULT_TEMPLATE_FOUND) {
			return true;
		}
		return false;
	}

	/**
	 * Execute tasks that apply to the pull request strategy.
	 *
	 * @param context Current Catapult context
	 * @throws InvalidCatapultStateException
	 */
	@Override
	public void process(final CatapultContext context) throws InvalidCatapultStateException {
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
