package dk.theknights.catapult.config.secret;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Ole Gregersen on 11/23/17.
 */
public class SSHAuthSecretTest {

	private static final String PRIVATE_KEY_START = "-----BEGIN RSA PRIVATE KEY-----";
	private static final String PRIVATE_KEY_END = "-----END RSA PRIVATE KEY-----";

	@Test
	public void testGetBitbucketSourceSecretTest() throws Exception {
		// Arrange
		SecretBuilder secretBuilder = new SecretBuilder(null);
		String secretsPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "secrets";
		SSHAuthSecret bitbucketSecret = (SSHAuthSecret) secretBuilder.build(secretsPath, "sshauth-secret", null);

		// Act
		String privateKey = bitbucketSecret.getPrivateKey();
		assertNotNull("bitbucketSecret privateKey must not be null", privateKey);

		// Assert
		String keyBegin = privateKey.substring(0, PRIVATE_KEY_START.length());
		String keyEnd = getSignatureEndMarker(privateKey);
		assertEquals("Start must match", PRIVATE_KEY_START, keyBegin);
		assertEquals("End must match", PRIVATE_KEY_END, keyEnd);
	}

	private String getSignatureEndMarker(final String privateKey) {
		int indexStart = privateKey.length() - PRIVATE_KEY_END.length();
		int indexEnd = privateKey.length();
		return privateKey.substring(indexStart, indexEnd);
	}

}
