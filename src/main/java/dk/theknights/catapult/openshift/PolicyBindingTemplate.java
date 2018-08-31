package dk.theknights.catapult.openshift;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.theknights.catapult.config.CatapultConfig;
import dk.theknights.catapult.config.CatapultProperties;
import dk.theknights.catapult.model.openshift.RoleBinding2;
import dk.theknights.catapult.model.openshift.Subject;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class will load a default policy binding template from filesystem or from a repository.
 * This default policy binding template is used to manage default permissions on namespaces that are created by the Catapult.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 11/20/17.
 */
@Component("policyBindingTemplate")
public class PolicyBindingTemplate {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String TEMPLATE_FILE_NAME = "defaultPolicyBindingTemplate.json";

	@Autowired
	private CatapultConfig catapultConfig = null;

	private String namespace;
	private String json;
	private ArrayList<String> securityGroups;

	/**
	 * Default constructor for this class. Initializes with an empty securityGroups.
	 */
	public PolicyBindingTemplate() {
		this.securityGroups = new ArrayList<>();
	}

	/**
	 * Set namespace for this template.
	 *
	 * @param namespace New namespace to set for this template
	 */
	public void setNamespace(final String namespace) {
		this.namespace = namespace;
	}

	/**
	 * Set Catapult configuration
	 *
	 * @param catapultConfig New Catapult configuration
	 */
	@Autowired
	public void setCatapultConfig(final CatapultConfig catapultConfig) {
		this.catapultConfig = catapultConfig;
	}

	/**
	 * Loads default policybinding template from local filesystem.
	 *
	 * @throws IOException
	 */
	public void loadFromLocal() throws IOException {
		CatapultProperties config = catapultConfig.getCatapultProperties();
		File templateFile = new File(config.getConfigPath() + "/" + TEMPLATE_FILE_NAME);
		logger.info("Loading local policytemplate file: " + templateFile.toURI());
		InputStream templateContent = new FileInputStream(templateFile);
		String policyJson = IOUtils.toString(templateContent);
		json = policyJson.replace("${NAMESPACE}", namespace);
	}

	/**
	 * Load default policybinding template from repository.
	 * THIS IS NOT IMPLEMENTED YET
	 *
	 * @param url Url to default policybinding template in repository
	 * @throws IOException
	 */
	public void loadFromRepository(final String url) throws IOException {
		throw new IOException("Template not found in reposiotry: " + url);
	}

	/**
	 * Add a new security group to list of security groups
	 *
	 * @param groupName New security group to add
	 */
	public void addSecurityGroup(final String groupName) {
		if (securityGroups.contains(groupName)) {
			securityGroups.add(groupName);
		}
	}

	/**
	 * Extract rolebinding from template.
	 *
	 * @return Serialized rolebinding part of this template
	 * @throws IOException
	 */
	public RoleBinding2 toRoleBinding() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		RoleBinding2 rolebinding = objectMapper.readValue(json, RoleBinding2.class);
		if (!securityGroups.isEmpty()) {
			List<String> grouplist = rolebinding.getGroupNames();
			grouplist.addAll(securityGroups);

			List<Subject> subjectlist = rolebinding.getSubjects();
			for (String group: securityGroups) {
				subjectlist.add(new Subject("Group", group, null));
			}
		}
		return rolebinding;
	}

	/**
	 * Convert whole template to json
	 *
	 * @return Template as json string
	 */
	public String toJson() {
		return json;
	}
}
