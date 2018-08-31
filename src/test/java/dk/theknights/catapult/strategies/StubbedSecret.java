package dk.theknights.catapult.strategies;

import com.openshift.restclient.IClient;
import dk.theknights.catapult.config.secret.CatapultSecret;
import org.jboss.dmr.ModelNode;

import java.io.InputStream;

/**
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/13/18.
 */
public class StubbedSecret extends CatapultSecret {

	public StubbedSecret(final ModelNode modelNode, final IClient openshiftClient) {
		super(modelNode, openshiftClient);
	}

	@Override
	public String getNamespace() {
		return "stubbed-namespace";
	}

	@Override
	public void addData(final String key, final InputStream data) {

	}

	@Override
	public void addData(final String key, final byte[] data) {

	}

	@Override
	public byte[] getData(final String key) {
		return null;
	}

	@Override
	public void setType(final String type) {

	}

	@Override
	public String getType() {
		return null;
	}

	@Override
	public String toJson() {
		return "{\"apiVersion\": \"v1\",\"data\": {\"accesstoken\": \"cHNMc2FURzVCRlZjNjlUWFdyZmg=\",\"password\": \"YWx3YXlzb3Jhbmdlcw==\",\"username\": \"YWxleGFuZGVyamtlbm5lZHk=\"},\"kind\": \"Secret\",\"metadata\": {\"creationTimestamp\": \"2017-11-06T12:17:55Z\",\"name\": \"alexgitlab\",\"namespace\": \"catapult-master\",\"resourceVersion\": \"55459550\",\"selfLink\": \"/api/v1/namespaces/catapult-master/secrets/alexgitlab\",\"uid\": \"84946dcb-c2ec-11e7-8a8e-fa163e5b30d6\"},\"type\": \"kubernetes.io/basic-auth\"}";
	}
}
