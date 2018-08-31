package dk.theknights.catapult.config.secret;

import com.openshift.internal.restclient.model.Secret;
import com.openshift.internal.restclient.model.properties.ResourcePropertiesRegistry;
import com.openshift.restclient.IClient;
import com.openshift.restclient.ResourceKind;
import org.jboss.dmr.ModelNode;

/**
 * CatapultSecret that wraps openshift java client secret.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class CatapultSecret extends Secret {

	public CatapultSecret(final ModelNode modelNode, final IClient openshiftClient) {
		super(modelNode, openshiftClient, ResourcePropertiesRegistry.getInstance().get("v1", ResourceKind.SECRET));
	}
}
