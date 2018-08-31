package dk.theknights.catapult.strategies;

import com.openshift.restclient.IClient;
import com.openshift.restclient.IOpenShiftWatchListener;
import com.openshift.restclient.IResourceFactory;
import com.openshift.restclient.IWatcher;
import com.openshift.restclient.UnsupportedVersionException;
import com.openshift.restclient.api.ITypeFactory;
import com.openshift.restclient.authorization.IAuthorizationContext;
import com.openshift.restclient.capability.CapabilityVisitor;
import com.openshift.restclient.capability.ICapability;
import com.openshift.restclient.model.IList;
import com.openshift.restclient.model.IResource;
import com.openshift.restclient.model.JSONSerializeable;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/14/18.
 */
public class StubbedOpenShiftClient implements IClient {
	@Override
	public IWatcher watch(final String s, final IOpenShiftWatchListener iOpenShiftWatchListener, final String... strings) {
		return null;
	}

	@Override
	public IWatcher watch(final IOpenShiftWatchListener iOpenShiftWatchListener, final String... strings) {
		return null;
	}

	@Override
	public <T extends IResource> List<T> list(final String s) {
		return null;
	}

	@Override
	public <T extends IResource> List<T> list(final String s, final Map<String, String> map) {
		return null;
	}

	@Override
	public <T extends IResource> List<T> list(final String s, final String s1) {
		return null;
	}

	@Override
	public <T extends IResource> List<T> list(final String s, final String s1, final Map<String, String> map) {
		return null;
	}

	@Override
	public <T extends IResource> List<T> list(final String s, final String s1, final String s2) {
		return null;
	}

	@Override
	public <T extends IResource> T get(final String s, final String s1, final String s2) {
		return null;
	}

	@Override
	public IList get(final String s, final String s1) {
		return null;
	}

	@Override
	public <T extends IResource> T create(final T t) {
		return null;
	}

	@Override
	public <T extends IResource> T create(final T t, final String s) {
		return null;
	}

	@Override
	public <T extends IResource> T create(final String s, final String s1, final String s2, final String s3, final IResource iResource) {
		return null;
	}

	@Override
	public Collection<IResource> create(final IList iList, final String s) {
		return null;
	}

	@Override
	public <T extends IResource> T update(final T t) {
		return null;
	}

	@Override
	public <T extends IResource> void delete(final T t) {

	}

	@Override
	public <T extends IResource> T execute(final String s, final String s1, final String s2, final String s3, final String s4, final IResource iResource) {
		return null;
	}

	@Override
	public <T extends IResource> T execute(final String s, final String s1, final String s2, final String s3, final String s4, final IResource iResource, final Map<String, String> map) {
		return null;
	}

	@Override
	public <T extends IResource> T execute(final String s, final String s1, final String s2, final String s3, final String s4, final IResource iResource, final String s5) {
		return null;
	}

	@Override
	public <T> T execute(final ITypeFactory iTypeFactory, final String s, final String s1, final String s2, final String s3, final String s4, final String s5, final JSONSerializeable jsonSerializeable, final Map<String, String> map) {
		return null;
	}

	@Override
	public URL getBaseURL() {
		return null;
	}

	@Override
	public String getResourceURI(final IResource iResource) {
		return null;
	}

	@Override
	public String getOpenShiftAPIVersion() throws UnsupportedVersionException {
		return null;
	}

	@Override
	public IAuthorizationContext getAuthorizationContext() {
		return null;
	}

	@Override
	public IResourceFactory getResourceFactory() {
		return null;
	}

	@Override
	public <T> T adapt(final Class<T> klass) {
		return null;
	}

	@Override
	public String getServerReadyStatus() {
		return null;
	}

	@Override
	public String getOpenshiftMasterVersion() {
		return null;
	}

	@Override
	public String getKubernetesMasterVersion() {
		return null;
	}

	@Override
	public IClient clone() {
		return null;
	}

	@Override
	public <T extends ICapability> T getCapability(final Class<T> aClass) {
		return null;
	}

	@Override
	public boolean supports(final Class<? extends ICapability> aClass) {
		return false;
	}

	@Override
	public <T extends ICapability, R> R accept(final CapabilityVisitor<T, R> capabilityVisitor, final R r) {
		return null;
	}
}
