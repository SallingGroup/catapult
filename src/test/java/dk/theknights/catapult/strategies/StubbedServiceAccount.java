package dk.theknights.catapult.strategies;

import com.openshift.internal.restclient.model.ServiceAccount;
import com.openshift.restclient.IClient;
import org.jboss.dmr.ModelNode;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/13/18.
 */
public class StubbedServiceAccount extends ServiceAccount {
	public StubbedServiceAccount(final ModelNode node, final IClient client, final Map<String, String[]> propertyKeys) {
		super(node, client, propertyKeys);
	}

	@Override
	public Collection<String> getSecrets() {
		return null;
	}

	@Override
	public void addSecret(final String secret) {

	}

	@Override
	public Collection<String> getImagePullSecrets() {
		return null;
	}

	@Override
	public void addImagePullSecret(final String imagePullSecret) {

	}
}
