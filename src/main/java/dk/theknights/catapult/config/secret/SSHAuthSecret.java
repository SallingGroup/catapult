package dk.theknights.catapult.config.secret;

import com.openshift.restclient.IClient;
import org.jboss.dmr.ModelNode;

import java.io.UnsupportedEncodingException;

/**
 * Class for modelling SSHAuth secret.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class SSHAuthSecret extends CatapultSecret {

	public SSHAuthSecret(final ModelNode modelNode, final IClient openshiftClient) {
		super(modelNode, openshiftClient);
	}

	/**
	 * Get decoded privatekey.
	 *
	 * @return decoded privatekey as a string
	 * @throws UnsupportedEncodingException
	 */
	public String getPrivateKey() throws UnsupportedEncodingException {
		return new String(getData("ssh-privatekey"), "UTF-8");
	}
}
