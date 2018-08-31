package dk.theknights.catapult.strategies.adapter.tasks;

import com.openshift.internal.restclient.model.Secret;
import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.services.OpenShiftService;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;

import java.io.IOException;
import java.util.List;

/**
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class CatapultCreateOpenShiftSecretTask implements CatapultAdapterTask {

	/**
	 * Accepts POLICY_BINDINGS_UPDATED state.
	 *
	 * @param state Current catapult state
	 * @return true if state is POLICY_BINDINGS_UPDATED, otherwise false.
	 */
	@Override
	public boolean accept(final CatapultStateEnum state) {
		if (state == CatapultStateEnum.POLICY_BINDINGS_UPDATED) {
			return true;
		}
		return false;
	}

	/**
	 * Creates secrets on OpenShift.
	 *
	 * @param context Current Catapult context
	 */
	@Override
	public void perform(final CatapultContext context) {
		try {
			List<Secret> secrets = context.getSecrets();
			for (Secret secret : secrets) {
				getOpenShiftService().createSecret(secret.getName(), context.getOpenShiftProject(), secret);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected OpenShiftService getOpenShiftService() {
		return new OpenShiftService();
	}

}
