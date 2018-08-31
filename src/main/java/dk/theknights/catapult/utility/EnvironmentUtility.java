package dk.theknights.catapult.utility;

import org.apache.commons.lang.StringUtils;

/**
 * This utility class gets an environment variable if it exists, otherwise it returns the specified default value.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 11/22/17.
 */
public class EnvironmentUtility {

	/**
	 * Get environment variable with specified name, if it exists, else return defaultValue.
	 *
	 * @param name Name of environment variable to find
	 * @param defaultValue Default value if environment variable is not found
	 * @return String with value of environment variable or defaultValue
	 */
	public static String getEnv(final String name, final String defaultValue) {
		String path = System.getenv(name);

		if (StringUtils.isBlank(path)) {
			return defaultValue;
		} else {
			return path;
		}
	}
}
