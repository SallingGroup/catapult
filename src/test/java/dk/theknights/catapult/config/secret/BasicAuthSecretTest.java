package dk.theknights.catapult.config.secret;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Ole Gregersen on 11/28/17.
 */
public class BasicAuthSecretTest {

	@Test
	public void testGetBitbucketSourceSecretTest() throws Exception {
		// Arrange
		String username = null;
		String password = null;
		SecretBuilder secretBuilder = new SecretBuilder(null);
		String secretsPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "secrets";


		// Act
		BasicAuthSecret basicauthSecret = (BasicAuthSecret) secretBuilder.build(secretsPath, "basicauth-secret", null);
		username = basicauthSecret.getUsername();
		password = basicauthSecret.getPassword();

		// Assert
		assertNotNull("BasicAuth secret username must not be null", username);
		assertEquals("BasicAuth secret username must have value 'username'", username, "username");
		assertNotNull("BasicAuth secret password must not be null", password);
		assertEquals("BasicAuth secret password must have value 'password'", password, "password");
	}

}
