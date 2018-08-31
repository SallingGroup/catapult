package dk.theknights.catapult.config.secret;

import com.openshift.restclient.IClient;
import org.jboss.dmr.ModelNode;

import java.io.UnsupportedEncodingException;

/**
 * Class for modeling a BasicAuth secret
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class BasicAuthSecret extends CatapultSecret {

	public BasicAuthSecret(final ModelNode modelNode, final IClient openshiftClient) {
		super(modelNode, openshiftClient);
	}

	/**
	 * Get decoded username value.
	 *
	 * @return decoded username as a string
	 * @throws UnsupportedEncodingException
	 */
	public String getUsername() throws UnsupportedEncodingException {
		return new String(getData("username"), "UTF-8");
	}

	/**
	 * Get decoded password value.
	 *
	 * @return decoded password as a string
	 * @throws UnsupportedEncodingException
	 */
	public String getPassword() throws UnsupportedEncodingException  {
		return new String(getData("password"), "UTF-8");
	}

}
