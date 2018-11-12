package dk.theknights.catapult.strategies.adapter;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.CatapultException;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultAdapterTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultDeleteOpenShiftProjectTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultDetectTemplateChangeTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultFetchTemplateTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultStatisticsTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultUpdateOpenShiftProjectRequester;
import dk.theknights.catapult.strategies.adapter.tasks.ReleaseProjectLookupTask;
import dk.theknights.catapult.strategies.adapter.tasks.ReleaseProjectUpdateDisplayNameTask;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import dk.theknights.catapult.strategies.state.InvalidCatapultStateException;

import java.util.ArrayList;
import java.util.List;

/**
 * This adapter holds all tasks that need to execute in the tag strategy.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 4/20/18.
 */
public class TagAdapter implements CatapultAdapter {

	private List<CatapultAdapterTask> tasks = new ArrayList<CatapultAdapterTask>();

	/**
	 * Initialize with tasks that need to execute in the tag strategy
	 */
	public TagAdapter() {
		tasks.add(new CatapultStatisticsTask());
		tasks.add(new ReleaseProjectLookupTask());
		tasks.add(new CatapultDeleteOpenShiftProjectTask());
		tasks.add(new CatapultFetchTemplateTask());
		tasks.add(new CatapultDetectTemplateChangeTask());
		tasks.add(new CatapultUpdateOpenShiftProjectRequester());
		tasks.add(new ReleaseProjectUpdateDisplayNameTask());
	}

	public List<CatapultAdapterTask> getTasks() { // this is only public because of TagAdapterTest
		return tasks;
	}

	/**
	 * Accept the following states:
	 * INITIAL
	 * CATAPULT_TEMPLATE_FOUND
	 * CATAPULT_TEMPLATE_NOT_FOUND
	 * CATAPULT_TEMPLATE_CHANGED
	 * CATAPULT_TEMPLATE_PROCESS_ERROR
	 * OPENSHIFT_PROJECT_CREATED
	 * SECRETS_UPDATED
	 * CONFIGMAPS_UPDATED
	 * RELEASE_PROJECT_NOT_FOUND
	 * RELEASE_PROJECT_FOUND
	 * NO_CONFIGMAPS_FOUND
	 * OPENSHIFT_PROJECT_NOT_FOUND
	 * CATAPULT_TEMPLATE_PROCESSED
	 * POLICY_BINDINGS_UPDATED
	 *
	 * @param context Current catapult context
	 * @return return true if state is in an acceptable list otherwise return false
	 */
	@Override
	public boolean accept(final CatapultContext context) {
		if (context.getCatapultState() == CatapultStateEnum.INITIAL
			|| context.getCatapultState() == CatapultStateEnum.CATAPULT_TEMPLATE_FOUND
			|| context.getCatapultState() == CatapultStateEnum.CATAPULT_TEMPLATE_NOT_FOUND
			|| context.getCatapultState() == CatapultStateEnum.CATAPULT_TEMPLATE_CHANGED
			|| context.getCatapultState() == CatapultStateEnum.CATAPULT_TEMPLATE_NOT_CHANGED
			|| context.getCatapultState() == CatapultStateEnum.CATAPULT_TEMPLATE_PROCESS_ERROR
			|| context.getCatapultState() == CatapultStateEnum.OPENSHIFT_PROJECT_CREATED
			|| context.getCatapultState() == CatapultStateEnum.SECRETS_UPDATED
			|| context.getCatapultState() == CatapultStateEnum.CONFIGMAPS_UPDATED
			|| context.getCatapultState() == CatapultStateEnum.RELEASE_PROJECT_NOT_FOUND
			|| context.getCatapultState() == CatapultStateEnum.RELEASE_PROJECT_FOUND
			|| context.getCatapultState() == CatapultStateEnum.NO_CONFIGMAPS_FOUND
			|| context.getCatapultState() == CatapultStateEnum.OPENSHIFT_PROJECT_NOT_FOUND
			|| context.getCatapultState() == CatapultStateEnum.CATAPULT_TEMPLATE_PROCESSED
			|| context.getCatapultState() == CatapultStateEnum.POLICY_BINDINGS_UPDATED) {
			return true;
		}
		return false;
	}

	/**
	 * Execute all tasks that apply to current context.
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
