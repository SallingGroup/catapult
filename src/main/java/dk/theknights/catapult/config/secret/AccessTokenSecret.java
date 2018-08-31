package dk.theknights.catapult.config.secret;

import com.openshift.restclient.IClient;
import org.jboss.dmr.ModelNode;

import java.io.UnsupportedEncodingException;

/**
 * Class for modeling AccessToken secret.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class AccessTokenSecret extends CatapultSecret {

	private final static String DATA_NAME_ACCESS_TOKEN = "accesstoken";

	public AccessTokenSecret(final ModelNode modelNode, final IClient openshiftClient) {
		super(modelNode, openshiftClient);
	}

	/**
	 * get decoded access token.
	 * @return Access token as decoded string value
	 * @throws UnsupportedEncodingException
	 */
	public String getAccessToken() throws UnsupportedEncodingException {
		return new String(getData(DATA_NAME_ACCESS_TOKEN), "UTF-8");
	}

}
