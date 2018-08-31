package dk.theknights.catapult.model;

/**
 * This class holds user login information like login and password.
 *
 * Created by Bardur Simmonsen (bsi.its@dsg.dk) on 1/31/17.
 */
public class UserLogin {

	private String username;
	private String password;

	/**
	 * Initialize UserLogin class with username and password
	 * @param username the username to be used for logging in
	 * @param password the password to be used for logging in
	 */
	public UserLogin(final String username, final String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Set username to use for logging in.
	 *
	 * @param username the username to be used for logging in
	 */
	public void setUsername(final String username) {
		this.username = username;
	}

	/**
	 * Get username used for logging in
	 *
	 * @return the username to be used for logging in
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set the password used for logging in.
	 *
	 * @param password the password to be used for logging in
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * Get password used for logging in.
	 *
	 * @return the password to be used for logging in
	 */
	public String getPassword() {
		return password;
	}

}
