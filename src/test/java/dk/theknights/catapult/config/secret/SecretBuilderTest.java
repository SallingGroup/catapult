package dk.theknights.catapult.config.secret;

import dk.theknights.catapult.config.CatapultConfig;
import dk.theknights.catapult.config.CatapultProperties;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Ole Gregersen on 11/27/17.
 */
public class SecretBuilderTest {

	private CatapultConfig configuration;

	@Before
	public void setUp() {
		configuration = new CatapultConfig(new CatapultProperties());
	}

	@Test
	public void testSecretBuilderBuild() throws IOException {
		// Arrange
		SecretBuilder secretBuilder = new SecretBuilder(null);

		// Act
		String secretsPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "secrets";
		CatapultSecret secret = secretBuilder.build(secretsPath, configuration.getBitbucketSourceSecretName(), null);

		// Assert
		assertNotNull("Catapult secret must not be null!", secret);
		assertNotNull("Catapult secret must have a name!", secret.getName());
		assertNotEquals("Catapult secret name can not be empty!", secret.getName(), "");
		assertNotNull("Catapult secret must be a sshauth secret!", secret.getData(SecretBuilder.SECRET_SSH_PRIVATEKEY));
		assertNull("Catapult secret must not have a basicauth password!", secret.getData(SecretBuilder.SECRET_PASSWORD));
		assertNull("Catapult secret must not have a basicauth username!", secret.getData(SecretBuilder.SECRET_USERNAME));
		assertNull("Catapult secret must not have a accesstoken!", secret.getData(SecretBuilder.SECRET_ACCESSTOKEN));

	}

	@Test
	public void testSecretBuilderBuildWithSSHAuth() throws IOException {
		// Arrange
		SecretBuilder secretBuilder = new SecretBuilder(null);

		// Act
		String secretsPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "secrets";
		CatapultSecret secret = secretBuilder.build(secretsPath, "sshauth-secret", null);

		// Assert
		assertNotNull("Catapult secret must not be null!", secret);
		assertNotNull("Catapult secret must have a name!", secret.getName());
		assertNotEquals("Catapult secret name can not be empty!", secret.getName(), "");
		assertNotNull("Catapult secret must have a sshauth!", secret.getData(SecretBuilder.SECRET_SSH_PRIVATEKEY));
		assertNull("Catapult secret must not have a basicauth password!", secret.getData(SecretBuilder.SECRET_PASSWORD));
		assertNull("Catapult secret must not have a basicauth username!", secret.getData(SecretBuilder.SECRET_USERNAME));
		assertNull("Catapult secret must not have a accesstoken!", secret.getData(SecretBuilder.SECRET_ACCESSTOKEN));

	}

	@Test
	public void testSecretBuilderBuildWithSSHAuthAndCustomPath() throws IOException {
		// Arrange
		SecretBuilder secretBuilder = new SecretBuilder(null);

		// Act
		String sshauthSecret = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "secrets";
		CatapultSecret secret = secretBuilder.build(sshauthSecret, "sshauth-secret", null);

		// Assert
		assertNotNull("Catapult secret must not be null!", secret);
		assertNotNull("Catapult secret must have a name!", secret.getName());
		assertNotEquals("Catapult secret name can not be empty!", secret.getName(), "");
		assertNotNull("Catapult secret must have a sshauth!", secret.getData(SecretBuilder.SECRET_SSH_PRIVATEKEY));
		assertNull("Catapult secret must not have a basicauth password!", secret.getData(SecretBuilder.SECRET_PASSWORD));
		assertNull("Catapult secret must not have a basicauth username!", secret.getData(SecretBuilder.SECRET_USERNAME));
		assertNull("Catapult secret must not have a accesstoken!", secret.getData(SecretBuilder.SECRET_ACCESSTOKEN));
	}

	@Test(expected = IOException.class)
	public void testSecretBuilderBuildWithSSHAuthAndBadSecretPath() throws IOException {
		// Arrange
		SecretBuilder secretBuilder = new SecretBuilder(null);
		String secretsPath = "/wrong/path/to/secrets";

		// Act
		CatapultSecret secret = secretBuilder.build(secretsPath, "sshauth-secret", null);

		// Assert
		assertNull("Secret must now be null", secret);
	}

	@Test(expected = UnsupportedEncodingException.class)
	public void testSecretBuilderBuildWithUnsupportedSecret() throws IOException {
		// Arrange
		SecretBuilder secretBuilder = new SecretBuilder(null);

		// Act
		String sshauthSecret = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "secrets";
		CatapultSecret secret = secretBuilder.build(sshauthSecret, "unsupported-secret", null);

		// Assert
		assertNull("Secret must now be null", secret);
	}

	@Test
	public void testSecretBuilderBuildWithBasicAuth() throws IOException {
		// Arrange
		SecretBuilder secretBuilder = new SecretBuilder(null);

		// Act
		String secretsPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "secrets";
		CatapultSecret secret = secretBuilder.build(secretsPath, "basicauth-secret", null);

		// Assert
		assertNotNull("Catapult secret must not be null!", secret);
		assertNotNull("Catapult secret must have a name!", secret.getName());
		assertNotEquals("Catapult secret name can not be empty!", secret.getName(), "");
		assertNotNull("Catapult secret must have a basicauth username!", secret.getData(SecretBuilder.SECRET_USERNAME));
		assertNotNull("Catapult secret must have a basicauth password!", secret.getData(SecretBuilder.SECRET_PASSWORD));
		assertNull("Catapult secret must not have a sshauth!", secret.getData(SecretBuilder.SECRET_SSH_PRIVATEKEY));
		assertNull("Catapult secret must not have a accesstoken!", secret.getData(SecretBuilder.SECRET_ACCESSTOKEN));

	}

	@Test
	public void testSecretBuilderBuildWithBasicAuthAndCustomPath() throws IOException {
		// Arrange
		SecretBuilder secretBuilder = new SecretBuilder(null);

		// Act
		String basicauthSecret = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "secrets";
		CatapultSecret secret = secretBuilder.build(basicauthSecret, "basicauth-secret", null);

		// Assert
		assertNotNull("Catapult secret must not be null!", secret);
		assertNotNull("Catapult secret must have a name!", secret.getName());
		assertNotEquals("Catapult secret name can not be empty!", secret.getName(), "");
		assertNotNull("Catapult secret must have a basicauth username!", secret.getData(SecretBuilder.SECRET_USERNAME));
		assertNotNull("Catapult secret must have a basicauth password!", secret.getData(SecretBuilder.SECRET_PASSWORD));
		assertNull("Catapult secret must not have a sshauth!", secret.getData(SecretBuilder.SECRET_SSH_PRIVATEKEY));
		assertNull("Catapult secret must not have a accesstoken!", secret.getData(SecretBuilder.SECRET_ACCESSTOKEN));
	}

	@Test(expected = IOException.class)
	public void testSecretBuilderBuildWithBasicAuthAndBadSecretPath() throws IOException {
		// Arrange
		SecretBuilder secretBuilder = new SecretBuilder(null);
		String secretsPath = "/wrong/path/to/secrets";
		// Act
		CatapultSecret secret = secretBuilder.build(secretsPath, "basicauth-secret", null);

		// Assert
		assertNull("Secret must now be null", secret);
	}

	@Test
	public void testSecretBuilderBuildWithAccessTokenAndCustomPath() throws IOException {
		// Arrange
		SecretBuilder secretBuilder = new SecretBuilder(null);

		// Act
		String accesstokenSecret = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "secrets";
		CatapultSecret secret = secretBuilder.build(accesstokenSecret, "accesstoken-secret", null);

		// Assert
		// assertNotNulll(secret)
		// assert(notNullOrEmpty(secret.name());
		// assertHasAccessToken(secret);
		assertNotNull("Catapult secret must not be null!", secret);
		assertNotNull("Catapult secret must have a name!", secret.getName());
		assertNotEquals("Catapult secret name can not be empty!", secret.getName(), "");
		assertNull("Catapult secret must not have a basicauth username!", secret.getData(SecretBuilder.SECRET_USERNAME));
		assertNull("Catapult secret must not have a basicauth password!", secret.getData(SecretBuilder.SECRET_PASSWORD));
		assertNull("Catapult secret must not have a sshauth!", secret.getData(SecretBuilder.SECRET_SSH_PRIVATEKEY));
		assertNotNull("Catapult secret must have a accesstoken!", secret.getData(SecretBuilder.SECRET_ACCESSTOKEN));
	}

	@Test(expected = IOException.class)
	public void testSecretBuilderBuildWithAccessTokenAndBadSecretPath() throws IOException {
		// Arrange
		SecretBuilder secretBuilder = new SecretBuilder(null);
		String secretsPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "secrets";

		// Act
		CatapultSecret secret = secretBuilder.build(secretsPath, "accesstoken", null);

		// Assert
		assertNull("Secret must now be null", secret);
	}

	@Test
	public void testSecretBuilderBuildWithAccessToken() throws IOException {
		// Arrange
		SecretBuilder secretBuilder = new SecretBuilder(null);

		// Act
		String secretsPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "secrets";
		CatapultSecret secret = secretBuilder.build(secretsPath, "accesstoken-secret", null);

		// Assert
		assertNotNull("Catapult secret must not be null!", secret);
		assertNotNull("Catapult secret must have a name!", secret.getName());
		assertNotEquals("Catapult secret name can not be empty!", secret.getName(), "");
		assertNull("Catapult secret must not have a basicauth username!", secret.getData(SecretBuilder.SECRET_USERNAME));
		assertNull("Catapult secret must not have a basicauth password!", secret.getData(SecretBuilder.SECRET_PASSWORD));
		assertNull("Catapult secret must not have a sshauth!", secret.getData(SecretBuilder.SECRET_SSH_PRIVATEKEY));
		assertNotNull("Catapult secret must have a accesstoken!", secret.getData(SecretBuilder.SECRET_ACCESSTOKEN));
	}

}
