package dk.theknights.catapult.strategies;

import com.openshift.restclient.model.IBuild;
import com.openshift.restclient.model.IProject;
import com.openshift.restclient.model.authorization.IPolicyBinding;
import dk.theknights.catapult.model.UserLogin;
import dk.theknights.catapult.services.OpenShiftService;

import java.io.IOException;

public class StubbedOpenShiftService extends OpenShiftService {

	@Override
	public IProject getProject(final String projectName) throws IOException {
		return new StubbedOpenShiftProject();
	}

	@Override
	public IBuild triggerBuild(final String repositoryUrl, final String commitId, final IProject project) throws IOException {
		return null;
	}

	@Override
	public IProject createProject(final String namespace) throws IOException {
		return new StubbedOpenShiftProject();
	}

	@Override
	public IPolicyBinding getPolicyBinding(final String policyBindingName, final IProject project) throws IOException {
		return new StubbedPolicyBinding();
	}

	protected UserLogin getLogin(final String path) throws IOException {
		return new UserLogin("Stubed-username", "Stubed-password");
	}

}
