package dk.theknights.catapult.strategies.adapter.tasks;

import com.openshift.internal.restclient.model.Secret;
import com.openshift.internal.restclient.model.ServiceAccount;
import com.openshift.restclient.model.IProject;
import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.CatapultException;
import dk.theknights.catapult.services.OpenShiftService;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Adds the release secret to OpenShift and to the default service account.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class CatapultCreateOpenShiftReleaseSecretTask implements CatapultAdapterTask {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

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
	 * Adds the release secret to OpenShift and to the default service account.
	 *
	 * @param context Current Catapult context
	 */
	@Override
	public void perform(final CatapultContext context) {
		List<Secret> addedSecrets = new ArrayList<>();
		List<ServiceAccount> modifiedServiceAccounts = new ArrayList<>();
		try {
			OpenShiftService openshiftService = getOpenShiftService();

			File releaseSecretFile = getReleaseSecretFile(context);
			IProject project = context.getOpenShiftProject();
			if (releaseSecretFile.exists()) {
				Secret releasesecret = openshiftService.createDockerSecret(context.getCatapultConfig().getCatapultProperties().getReleaseRegistrySecretName(), releaseSecretFile.getPath(), project);
				try {
					ServiceAccount defaultServiceAccount = openshiftService.addImagePullSecretToServiceAccount(releasesecret, "default", project);
					modifiedServiceAccounts.add(defaultServiceAccount);
					ServiceAccount builderServiceAccount = openshiftService.addSecretToServiceAccount(releasesecret, "builder");
					modifiedServiceAccounts.add(builderServiceAccount);
					addedSecrets.add(releasesecret);
				} catch (CatapultException e) {
					logger.warn("Skipping secret (" + releasesecret.getName() + ") on service account (builder) in project (" + project.getDisplayName() + ")");
				}
			} else {
				logger.error("Catapult Configuration ERROR ! No " + context.getCatapultConfig().getCatapultProperties().getReleaseRegistrySecretName() + " secret found! OpenShift project will not be able to do releases ...");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		context.setModifiedServiceAccounts(modifiedServiceAccounts);
		context.setSecretsAdded(addedSecrets);
	}

	protected File getReleaseSecretFile(final CatapultContext context) {
		return Paths.get(context.getCatapultConfig().getSecretsPath(), context.getCatapultConfig().getCatapultProperties().getReleaseRegistrySecretName()).toFile();
	}

	protected OpenShiftService getOpenShiftService() {
		return new OpenShiftService();
	}

}
