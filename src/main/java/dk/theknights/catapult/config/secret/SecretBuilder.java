package dk.theknights.catapult.config.secret;

import com.openshift.restclient.IClient;
import com.openshift.restclient.ResourceKind;
import org.jboss.dmr.ModelNode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * This class builds secrets. It will create a secret class from a file which could be a mounted secret in a container.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class SecretBuilder {

	public static final String SECRET_SSH_PRIVATEKEY = "ssh-privatekey";
	public static final String SECRET_USERNAME = "username";
	public static final String SECRET_PASSWORD = "password";
	public static final String SECRET_ACCESSTOKEN = "accesstoken";

	private static final String KIND = "kind";
	private static final String APIVERSION = "apiVersion";

	private IClient openshiftClient = null;
	private ModelNode node = null;

	public SecretBuilder(final IClient openshiftClient) {
		this.openshiftClient = openshiftClient;
		node = new ModelNode();
		node.get(APIVERSION).set("v1");
		node.get(KIND).set(ResourceKind.SECRET);
	}

	/**
	 * Create a secret class based on a file.
	 *
	 * @param secretPath path to secret location
	 * @param secretName name of secret to load
	 * @param namespace namespace for secret
	 * @return a CatapultSecret created from a file
	 * @throws IOException
	 */
	public CatapultSecret build(final String secretPath, final String secretName, final String namespace) throws IOException {

		File secretLocation = new File(secretPath);
		if (secretLocation.exists()) {
			String sshauthSecret = secretPath + "/" + secretName + "/" + SECRET_SSH_PRIVATEKEY;
			String basicauthUsernameSecret = secretPath + "/" + secretName + "/" + SECRET_USERNAME;
			String basicauthPasswordSecret = secretPath + "/" + secretName + "/" + SECRET_PASSWORD;
			String accesstokenSecret = secretPath + "/" + secretName + "/" + SECRET_ACCESSTOKEN;
			if (new File(sshauthSecret).exists()) {
				CatapultSecret secret = buildFromSSHAuth(sshauthSecret);
				secret.setNamespace(namespace);
				secret.setName(secretName);
				return secret;
			} else if (new File(basicauthUsernameSecret).exists() && new File(basicauthPasswordSecret).exists()) {
				CatapultSecret secret = buildFromBasicAuth(basicauthUsernameSecret, basicauthPasswordSecret);
				secret.setNamespace(namespace);
				secret.setName(secretName);
				return secret;
			} else if (new File(accesstokenSecret).exists()) {
				CatapultSecret secret = buildFromAccessToken(accesstokenSecret);
				secret.setNamespace(namespace);
				secret.setName(secretName);
				return secret;
			}
			throw new UnsupportedEncodingException("Bad secret file " + secretPath + "/" + secretName);
		} else {
			throw new FileNotFoundException("File not found " + secretPath + "/" + secretName);
		}
	}

	private SSHAuthSecret buildFromSSHAuth(final String secretFileLocation) throws IOException {
		SSHAuthSecret sshauthSecret = new SSHAuthSecret(node, openshiftClient);

		File sshauthFile = new File(secretFileLocation);
		FileInputStream fis = new FileInputStream(sshauthFile);
		try {
			sshauthSecret.setType("Opaque");
			sshauthSecret.addData(SECRET_SSH_PRIVATEKEY, fis);
		} finally {
			fis.close();
		}

		return sshauthSecret;
	}

	private BasicAuthSecret buildFromBasicAuth(final String usernameFileLocation, final String passwordFileLocation) throws IOException {
		BasicAuthSecret basicauthSecret = new BasicAuthSecret(node, openshiftClient);

		FileInputStream usernamefis = new FileInputStream(new File(usernameFileLocation));
		try {
			basicauthSecret.addData(SECRET_USERNAME, usernamefis);
		} finally {
			usernamefis.close();
		}

		FileInputStream passwordfis = new FileInputStream(new File(passwordFileLocation));
		try {
			basicauthSecret.addData(SECRET_PASSWORD, passwordfis);
		} finally {
			passwordfis.close();
		}

		return basicauthSecret;
	}

	private AccessTokenSecret buildFromAccessToken(final String accesstokenFileLocation) throws IOException {
		AccessTokenSecret accesstokenSecret = new AccessTokenSecret(node, openshiftClient);

		FileInputStream accesstokenfis = new FileInputStream(new File(accesstokenFileLocation));
		try {
			accesstokenSecret.addData(SECRET_ACCESSTOKEN, accesstokenfis);
		} finally {
			accesstokenfis.close();
		}

		return accesstokenSecret;
	}

}
