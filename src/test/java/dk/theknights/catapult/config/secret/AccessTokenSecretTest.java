package dk.theknights.catapult.config.secret;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Ole Gregersen on 11/28/17.
 */
public class AccessTokenSecretTest {

	@Test
	public void testAccessToken() throws Exception {
		// Arrange
		SecretBuilder secretBuilder = new SecretBuilder(null);
		String secretsPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "secrets";

		// Act
		AccessTokenSecret accesstokenSecret = (AccessTokenSecret) secretBuilder.build(secretsPath, "accesstoken-secret", null);

		// Assert
		assertNotNull("BasicAuth secret username must not be null", accesstokenSecret);
		assertEquals("BasicAuth secret username must have value 'username'", accesstokenSecret.getAccessToken(), "SomeVerySecretAccessToken");
	}

}
