package dk.theknights.catapult.bitbucket;

import dk.theknights.catapult.RepositoryType;
import dk.theknights.catapult.config.CatapultConfig;
import dk.theknights.catapult.config.CatapultProperties;
import dk.theknights.catapult.model.UserLogin;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Base64;

/**
 * This class is responsible for the interface with BitBucket.
 * For now it is only used to fetch the raw commit of the catapult template from BitBucket.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class BitbucketAPI {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	private CatapultConfig configuration;

	/**
	 * Only constructor for BitbucketAPI.
	 *
	 * @param configuration The configuration is needed to fetch login information and proxy configuration.
	 */
	public BitbucketAPI(final CatapultConfig configuration) {
		this.configuration = configuration;
	}

	/**
	 * Get raw Bitbucket commit. Content of commit can be downloaded with this method.
	 *
	 * @param url Bitbucket url for getting raw commit.
	 * @return Http response as a plain string.
	 * @throws IOException
	 */
	public String getRawCommit(final String url) throws IOException {
		logger.info("raw commit url:" + url);
		HttpURLConnection connection = getHttpConnection(new URL(url));
		InputStream content = connection.getInputStream();
		return IOUtils.toString(content);
	}

	protected HttpURLConnection getHttpConnection(final URL url) throws IOException {
		CatapultProperties config = configuration.getCatapultProperties();
		Proxy.Type proxyType = null;
		if (config.getProxy().getType() != null && config.getProxy().getType().equalsIgnoreCase("HTTP")) {
			proxyType = Proxy.Type.HTTP;
		} else if (config.getProxy().getType() != null && config.getProxy().getType().equalsIgnoreCase("DIRECT")) {
			proxyType = Proxy.Type.DIRECT;
		} else if (config.getProxy().getType() != null && config.getProxy().getType().equalsIgnoreCase("SOCKS")) {
			proxyType = Proxy.Type.SOCKS;
		}

		Proxy proxy = null;
		if (config.getProxy().isEnabled()) {
			proxy = new Proxy(proxyType, new InetSocketAddress(config.getProxy().getHost(), config.getProxy().getPort()));
		} else {
			proxy = Proxy.NO_PROXY;
		}

		 logger.info("Using proxy: " + proxy);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setDoOutput(true);

		UserLogin login = getLogin(configuration.getRepositorySecretPath(RepositoryType.BITBUCKET));
		String encoding = new String(Base64.getEncoder().encode((login.getUsername() + ":" + login.getPassword()).getBytes("utf-8")), "UTF-8");
		connection.setRequestProperty("Authorization", "Basic " + encoding);

		return connection;
	}

	protected UserLogin getLogin(final String path) throws IOException {
		return new UserLogin(getUserName(path), getPassword(path));
	}

	private String getUserName(final String path) throws IOException {
		String filename = path + "/username";
		return IOUtils.toString(new FileInputStream(new File(filename)), "UTF-8");
	}

	private String getPassword(final String path) throws IOException {
		String filename = path + "/password";
		return IOUtils.toString(new FileInputStream(new File(filename)), "UTF-8");
	}

}
