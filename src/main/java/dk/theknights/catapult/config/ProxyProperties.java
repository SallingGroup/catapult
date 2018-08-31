package dk.theknights.catapult.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Modelling of proxy configuration. Proxy can be switched on or off. If proxy is used you must specify proxy host and port.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "catapult.proxy")
@Component
public class ProxyProperties {

	@Value("${catapult.proxy.enabled}")
	private boolean enabled = false;

	@Value("${catapult.proxy.host}")
	private String host = null;

	@Value("${catapult.proxy.port}")
	private Integer port = null;

	@Value("${catapult.proxy.type}")
	private String type = null;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	public String getHost() {
		return host;
	}

	public Integer getPort() {
		return port;
	}

	public String getType() {
		return type;
	}

	public void setHost(final String host) {
		this.host = host;
	}

	public void setPort(final Integer port) {
		this.port = port;
	}

}
