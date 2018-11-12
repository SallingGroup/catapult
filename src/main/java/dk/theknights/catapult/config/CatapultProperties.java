package dk.theknights.catapult.config;

import dk.theknights.catapult.utility.EnvironmentUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Properties from application properties are available from this class.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 11/6/17.
 */
@PropertySource("classpath:application.properties")
@Component("catapultProperties")
public class CatapultProperties {

	public static final String CATAPULT_CONFIG_PATH = "CATAPULT_CONFIG_PATH";
	public static final String RELEASE_REGISTRY_SECRET_NAME = "RELEASE_REGISTRY_SECRET_NAME";

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${catapult.name}")
	String name;

	@Value("${catapult.config.path}")
	String configpath;

	@Value("${catapult.release.secret.name}")
	String releaseRegistrySecretName;

	@Value("${catapult.project.reserved.groups}")
	String projectReservedGroups;

	@Value("${catapult.project.mapping}")
	String projectMapping;

	@Value("${catapult.release.repository}")
	String releaseRepository;

	private ProxyProperties proxy = new ProxyProperties();

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		logger.info("name:" + name);
		this.name = name;
	}

	public void setConfigpath(final String configpath) {
		logger.info("configpath:" + configpath);
		this.configpath = configpath;
	}

	public String getConfigPath() {
		logger.info("configpath:" + configpath);
		return EnvironmentUtility.getEnv(CATAPULT_CONFIG_PATH, configpath);
	}

	public ProxyProperties getProxy() {
		return proxy;
	}

	public void setReleaseRegistrySecretName(final String releaseRegistrySecretName) {
		this.releaseRegistrySecretName = releaseRegistrySecretName;
	}

	public String getReleaseRegistrySecretName() {
		return EnvironmentUtility.getEnv(RELEASE_REGISTRY_SECRET_NAME, releaseRegistrySecretName);
	}

	public void setProjectReservedGroups(final String projectGroups) {
		logger.info("Setting project reserved groups: " + projectGroups);
		this.projectReservedGroups = projectGroups;
	}

	public String getProjectReservedGroups() {
		logger.info("Setting project reserved groups: " + projectReservedGroups);
		return this.projectReservedGroups;
	}

	public String getProjectMapping() {
		return projectMapping;
	}

	public String getReleaseRepository() {
		return releaseRepository;
	}
}
