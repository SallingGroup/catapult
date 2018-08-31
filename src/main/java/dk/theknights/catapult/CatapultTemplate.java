package dk.theknights.catapult;

/**
 * OpenShift Template that Catapult uses to bootstrap new namespaces that are created on OpenShift.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 11/22/17.
 */
public class CatapultTemplate {

	private String template;

	/**
	 * CatapultTemplate must be created with a string containing the OpenShift template.
	 *
	 * @param template String with the OpenShift template
	 */
	public CatapultTemplate(final String template) {
		this.template = template;
	}

	/**
	 * Get template as string.
	 *
	 * @return A string that contains the template
	 */
	@Override
	public String toString() {
		return template;
	}

}
