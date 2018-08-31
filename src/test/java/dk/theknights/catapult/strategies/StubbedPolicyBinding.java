package dk.theknights.catapult.strategies;

import com.openshift.restclient.capability.CapabilityVisitor;
import com.openshift.restclient.capability.ICapability;
import com.openshift.restclient.model.IProject;
import com.openshift.restclient.model.authorization.IPolicyBinding;

import java.util.Map;
import java.util.Set;

public class StubbedPolicyBinding implements IPolicyBinding {
	@Override
	public Map<String, String> getMetadata() {
		return null;
	}

	@Override
	public Set<Class<? extends ICapability>> getCapabilities() {
		return null;
	}

	@Override
	public String getCreationTimeStamp() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getNamespace() {
		return null;
	}

	@Override
	public IProject getProject() {
		return null;
	}

	@Override
	public Map<String, String> getLabels() {
		return null;
	}

	@Override
	public void addLabel(final String s, final String s1) {

	}

	@Override
	public boolean isAnnotatedWith(final String s) {
		return false;
	}

	@Override
	public String getAnnotation(final String s) {
		return null;
	}

	@Override
	public void setAnnotation(final String s, final String s1) {

	}

	@Override
	public void removeAnnotation(final String s) {

	}

	@Override
	public Map<String, String> getAnnotations() {
		return null;
	}

	@Override
	public String getResourceVersion() {
		return null;
	}

	@Override
	public String getApiVersion() {
		return null;
	}

	@Override
	public String getKind() {
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

	@Override
	public String toJson() {
		return "{\"kind\": \"PolicyBinding\", \"apiVersion\": \"v1\", \"metadata\": {\"creationTimestamp\": \"2018-03-08T12:45:09Z\"}, \"roleRef\": {\"name\": \"admin\"}, \"roleBindings\": [{\"metadata\": {\"creationTimestamp\": \"2018-03-08T12:45:09Z\",\"name\": \"admin\"}}]}";
	}
}
