package dk.theknights.catapult.config;

import dk.theknights.catapult.RepositoryType;
import dk.theknights.catapult.utility.EnvironmentUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Catapult config wrapper. It holds properties from application.properties and has helper methods to get configuration
 * that applies to Bitbucket, Gitlab or Github.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 11/23/17.
 */
@Configuration
@PropertySource("classpath:application.properties")
@Component("catapultConfig")
public class CatapultConfig {

	public static final String SECRET_SECRETS_PATH = "SECRET_SECRETS_PATH";
	public static final String BITBUCKET_SOURCE_REPOSITORY_SECRET_NAME = "BITBUCKET_SOURCE_REPOSITORY_SECRET_NAME";
	public static final String SECRET_BITBUCKET_PATH = "SECRET_BITBUCKET_PATH";
	public static final String SECRET_GITLAB_PATH = "SECRET_GITLAB_PATH";

	protected final String secretsPath = EnvironmentUtility.getEnv(SECRET_SECRETS_PATH, "/etc/secrets");
	protected final String bitbucketSourceSecretName = EnvironmentUtility.getEnv(BITBUCKET_SOURCE_REPOSITORY_SECRET_NAME, "bitbucket-pipeline-secret");
	protected String bitbucketSecretPath = EnvironmentUtility.getEnv(SECRET_BITBUCKET_PATH, "/etc/bitbucket");
	protected String gitlabSecretPath = EnvironmentUtility.getEnv(SECRET_GITLAB_PATH, "/etc/gitlab");

	@Autowired
	private CatapultProperties catapultProperties;

	@Autowired
	public CatapultConfig(final CatapultProperties catapult) {
		catapultProperties = catapult;
	}

	public CatapultProperties getCatapultProperties() {
		return catapultProperties;
	}

	public String getSecretsPath() {
		return secretsPath;
	}

	public String getBitbucketSecretPath() {
		return bitbucketSecretPath;
	}

	public String getGitlabSecretPath() {
		return gitlabSecretPath;
	}

	/**
	 * Helper method to get secret path that applies for Bitbucket, Gitlab or Github.
	 *
	 * @param repoType repository type of BITBUCKET, GITLAB or GITHUB
	 * @return secret path as string
	 */
	public String getRepositorySecretPath(final RepositoryType repoType) {
		switch (repoType) {
			case BITBUCKET:
				return getBitbucketSecretPath();
			case GITHUB:
				return getSecretsPath();
			case GITLAB:
				return getGitlabSecretPath();
			default:
				return getSecretsPath();
		}
	}

	/**
	 * Helper method to get source secret name that applies for Bitbucket, Gitlab or Github.
	 *
	 * @param repoType repository type of BITBUCKET, GITLAB or GITHUB
	 * @return source secret name as string
	 */
	public String getPipelineSourceSecretName(final RepositoryType repoType) {
		switch (repoType) {
			case BITBUCKET:
				return getBitbucketSourceSecretName();
			case GITHUB:
				return null;
			case GITLAB:
				return getGitLabSourceSecretName();
			default:
				return null;
		}
	}

	public String getBitbucketSourceSecretName() {
		return bitbucketSourceSecretName;
	}

	public String getGitLabSourceSecretName() {
		return null;
	}

}
