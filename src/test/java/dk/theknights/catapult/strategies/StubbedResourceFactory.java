package dk.theknights.catapult.strategies;

import com.openshift.restclient.IClient;
import com.openshift.restclient.IResourceFactory;
import com.openshift.restclient.model.IResource;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/14/18.
 */
public class StubbedResourceFactory implements IResourceFactory {
	@Override
	public List<IResource> createList(final String s, final String s1) {
		return null;
	}

	@Override
	public <T extends IResource> T create(final String s) {
		return null;
	}

	@Override
	public <T extends IResource> T create(final InputStream inputStream) {
		return null;
	}

	@Override
	public <T extends IResource> T create(final String s, final String s1) {
		return null;
	}

	@Override
	public <T extends IResource> T create(final String s, final String s1, final String s2) {
		return null;
	}

	@Override
	public <T extends IResource> T stub(final String s, final String s1) {
		return null;
	}

	@Override
	public <T extends IResource> T stub(final String s, final String s1, final String s2) {
		return null;
	}

	@Override
	public void setClient(final IClient iClient) {

	}

	@Override
	public Object createInstanceFrom(final String s) {
		return null;
	}

	@Override
	public Object stubKind(final String s, final Optional<String> optional, final Optional<String> optional1) {
		return null;
	}
}
