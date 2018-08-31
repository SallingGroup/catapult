package dk.theknights.catapult.strategies.adapter.tasks;

import com.openshift.internal.restclient.model.Secret;
import com.openshift.internal.restclient.model.template.Template;
import com.openshift.restclient.model.IResource;
import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.CatapultException;
import dk.theknights.catapult.services.OpenShiftService;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;
import org.jboss.dmr.ModelNode;

import java.io.IOException;
import java.util.Collection;

/**
 * Get secrets from OpenShift that are annotated for managemnet by the catapult.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class CatapultFetchOpenShiftSecretsTask implements CatapultAdapterTask {

	/** This defines the place where catapult will look for secrets to transfer to new projects. **/
	public static final String CATAPULT_SECRET_SOURCE_TYPE = "catapult/source-type";
	/** This defines the annotation that secrets myst have if they are to be transferred to new projects. **/
	public static final String CATAPULT_SECRET_SOURCE_IDENTIFIER = "catapult/source-identifier";

	/**
	 * Accept state POLICY_BINDINGS_UPDATED
	 * @param state Current catapult state
	 * @return true if state is POLICY_BINDINGS_UPDATED, otherwise false
	 */
	@Override
	public boolean accept(final CatapultStateEnum state) {
		if (state.equals(CatapultStateEnum.POLICY_BINDINGS_UPDATED)) {
			return true;
		}
		return false;
	}

	/**
	 * Secrets annotated with CATAPULT_SECRET_SOURCE_IDENTIFIER from CATAPULT_SECRET_SOURCE_TYPE are added to OpenShift
	 * and the Catapult context is updated to reflect this.
	 *
	 * @param context Current Catapult context
	 * @throws CatapultException If Catapult template is null or an Error from OpenShift
	 */
	public void perform(final CatapultContext context) throws CatapultException {
		if (context.getCatapultTemplate() == null) {
			throw new CatapultException("Template can not be null!");
		}
		try {
			ModelNode node = ModelNode.fromJSONString(context.getCatapultTemplate().toString());
			Template template = new Template(node, null, null); // We only do this to be able to fetch the annotation

			String secretSource = template.getAnnotation(CATAPULT_SECRET_SOURCE_TYPE);
			if (secretSource != null) {
				Collection<IResource> allSecrets = getOpenShiftService().getSecrets(secretSource);
				for (IResource secret : allSecrets) {
					String annotation = secret.getAnnotation(CATAPULT_SECRET_SOURCE_IDENTIFIER);
					if (annotation != null) {
						context.addSecret((Secret) secret);
					}
				}
			}
		} catch (IOException e) {
			throw new CatapultException(e);
		}
	}

	protected OpenShiftService getOpenShiftService() {
		return new OpenShiftService();
	}

}
