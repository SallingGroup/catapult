package dk.theknights.catapult.strategies.adapter.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openshift.restclient.model.authorization.IPolicyBinding;
import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.model.openshift.PolicyBinding;
import dk.theknights.catapult.model.openshift.RoleBinding2;
import dk.theknights.catapult.openshift.PolicyBindingTemplate;
import dk.theknights.catapult.services.OpenShiftService;
import dk.theknights.catapult.strategies.state.CatapultStateEnum;

import java.io.IOException;

/**
 * The default policy binding template configured for the Catapult is applied to the OpenShift project.
 *
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class CatapultOpenShiftApplyPolicyBindingTask implements CatapultAdapterTask {

	/**
	 * Accepts state OPENSHIFT_PROJECT_CREATED
	 * @param state Current catapult state
	 * @return true if state is OPENSHIFT_PROJECT_CREATED, otherwise false
	 */
	@Override
	public boolean accept(final CatapultStateEnum state) {
		if (state.equals(CatapultStateEnum.OPENSHIFT_PROJECT_CREATED)) {
			return true;
		}
		return false;
	}

	/**
	 * Default policy binding template is applied to the OpenShift project from current Catapult context.
	 *
	 * @param context Current Catapult context
	 */
	@Override
	public void perform(final CatapultContext context) {
		try {
			OpenShiftService openshiftService = getOpenShiftService();
			openshiftService.setCatapultConfig(context.getCatapultConfig());

			// Load local policy binding
			IPolicyBinding defaultPolicyBinding = openshiftService.getPolicyBinding(":default", context.getOpenShiftProject());
			String json = defaultPolicyBinding.toJson();

			ObjectMapper objectMapper = new ObjectMapper();
			PolicyBinding policyBinding = objectMapper.readValue(json, PolicyBinding.class);

			PolicyBindingTemplate policybindingTemplate = getPolicyBindingTemplate(context);
			policybindingTemplate.setNamespace(context.getOpenShiftProject().getNamespace());
			policybindingTemplate.setCatapultConfig(context.getCatapultConfig());
			policybindingTemplate.loadFromLocal();
			RoleBinding2 role = policybindingTemplate.toRoleBinding();

			policyBinding.getRoleBindings().add(role);

			openshiftService.update(objectMapper.writeValueAsString(policyBinding), context.getOpenShiftProject());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected PolicyBindingTemplate getPolicyBindingTemplate(final CatapultContext context) {
		PolicyBindingTemplate template = new PolicyBindingTemplate();
		template.setNamespace(context.getOpenShiftProject().getNamespace());
		return template;
	}

	protected OpenShiftService getOpenShiftService() {
		return new OpenShiftService();
	}

}
