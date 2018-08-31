package dk.theknights.catapult.strategies.adapter.tasks.bitbucket;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.CatapultException;
import dk.theknights.catapult.config.CatapultConfig;
import dk.theknights.catapult.config.secret.CatapultSecret;
import dk.theknights.catapult.services.OpenShiftService;
import dk.theknights.catapult.strategies.adapter.tasks.CatapultAdapterTask;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;

import java.io.IOException;

/**
 * This task will get the Bitbucket source secret and add it to the OpenShift project and the builder service account.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/13/18.
 */
public class CatapultCreateBitbucketSourceSecretTask implements CatapultAdapterTask {

	/**
	 * Accepts state POLICY_BINDINGS_UPDATED
	 *
	 * @param state Current catapult state
	 * @return true if state is POLICY_BINDINGS_UPDATED, otherwise false.
	 */
	@Override
	public boolean accept(final CatapultStateEnum state) {
		if (state.equals(CatapultStateEnum.POLICY_BINDINGS_UPDATED)) {
			return true;
		}

		return false;
	}

	/**
	 * Add Bitbucket source secret to OpenShift and to the builder service account.
	 *
	 * @param context Current Catapult context
	 */
	@Override
	public void perform(final CatapultContext context) {
		try {
			OpenShiftService openshiftService = getOpenShiftService();

			CatapultConfig configuration = context.getCatapultConfig();
			CatapultSecret pipelineSecret = openshiftService.createSecret(configuration.getSecretsPath(), configuration.getBitbucketSourceSecretName(), context.getOpenShiftProject().getNamespace());
			openshiftService.addSecretToServiceAccount(pipelineSecret, "builder");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CatapultException e) {
			e.printStackTrace();
		}
	}

	protected OpenShiftService getOpenShiftService() {
		return new OpenShiftService();
	}

}
