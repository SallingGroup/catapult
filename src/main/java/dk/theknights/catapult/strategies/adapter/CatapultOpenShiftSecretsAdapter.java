package dk.theknights.catapult.strategies.adapter;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.CatapultException;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultAdapterTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultCreateOpenShiftReleaseSecretTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultCreateOpenShiftSecretTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultCreateOpenShiftSourceSecretTask;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultFetchOpenShiftSecretsTask;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import dk.theknights.catapult.strategies.state.InvalidCatapultStateException;

import java.util.ArrayList;
import java.util.List;

/**
 * This adapter holds tasks that accept secret states.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class CatapultOpenShiftSecretsAdapter implements CatapultAdapter {

	private List<CatapultAdapterTask> tasks = new ArrayList<CatapultAdapterTask>();

	/**
	 *
	 */
	public CatapultOpenShiftSecretsAdapter() {
		tasks.add(new CatapultCreateOpenShiftSourceSecretTask());
		tasks.add(new CatapultCreateOpenShiftReleaseSecretTask());
		tasks.add(new CatapultFetchOpenShiftSecretsTask());
		tasks.add(new CatapultCreateOpenShiftSecretTask());
	}

	/**
	 * Only the following state is accepted: POLICY_BINDINGS_UPDATED.
	 *
	 * @param context Current catapult context
	 * @return return true if state is POLICY_BINDINGS_UPDATED, otherwise return false
	 */
	@Override
	public boolean accept(final CatapultContext context) {
		if (
			context.getCatapultState() == CatapultStateEnum.POLICY_BINDINGS_UPDATED
			) {
			return true;
		}
		return false;
	}

	protected List<CatapultAdapterTask> getTasks() {
		return tasks;
	}

	/**
	 * Execute tasks that apply to current Catapult context.
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
