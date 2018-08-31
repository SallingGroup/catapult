package dk.theknights.catapult.strategies;

import com.openshift.restclient.capability.CapabilityVisitor;
import com.openshift.restclient.capability.ICapability;
import com.openshift.restclient.images.DockerImageURI;
import com.openshift.restclient.model.IBuild;
import com.openshift.restclient.model.IProject;
import com.openshift.restclient.model.build.IBuildSource;
import com.openshift.restclient.model.build.IBuildStatus;
import com.openshift.restclient.model.build.IBuildStrategy;

import java.util.Map;
import java.util.Set;

public class StubbedBuild implements IBuild {
	@Override
	public String getStatus() {
		return null;
	}

	@Override
	public String getMessage() {
		return null;
	}

	@Override
	public String getPodName() {
		return null;
	}

	@Override
	public boolean cancel() {
		return false;
	}

	@Override
	public DockerImageURI getOutputTo() {
		return null;
	}

	@Override
	public String getOutputKind() {
		return null;
	}

	@Override
	public <T extends IBuildSource> T getBuildSource() {
		return null;
	}

	@Override
	public <T extends IBuildStrategy> T getBuildStrategy() {
		return null;
	}

	@Override
	public String getPushSecret() {
		return null;
	}

	@Override
	public IBuildStatus getBuildStatus() {
		return null;
	}

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
		return null;
	}
}
