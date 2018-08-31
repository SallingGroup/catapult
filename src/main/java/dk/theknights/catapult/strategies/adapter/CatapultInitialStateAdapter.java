package dk.theknights.catapult.strategies.adapter;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.CatapultException;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultAdapterTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultDetectTemplateChangeTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultFetchTemplateTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultStatisticsTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultValidateConfigTask;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds all tasks that can be activated on initial state.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class CatapultInitialStateAdapter implements CatapultAdapter {

	private List<CatapultAdapterTask> tasks = new ArrayList<CatapultAdapterTask>();

	/**
	 * Initializes with tasks that apply to initial state.
	 */
	public CatapultInitialStateAdapter() {
		tasks.add(new CatapultStatisticsTask());
		tasks.add(new CatapultValidateConfigTask());
		tasks.add(new CatapultFetchTemplateTask());
		tasks.add(new CatapultDetectTemplateChangeTask());
	}

	protected List<CatapultAdapterTask> getTasks() {
		return tasks;
	}

	/**
	 * Initial state is accepted.
	 * @param context Current catapult context
	 * @return true if Catapult state is initial otherwise return false
	 */
	public boolean accept(final CatapultContext context) {
		if (context.getCatapultState() == CatapultStateEnum.INITIAL) {
			return true;
		}
		return false;
	}

	/**
	 * Activate tasks that need to execute on initial state.
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
