package dk.theknights.catapult.strategies.adapter.tasks;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.model.openshift.RoleBinding2;
import dk.theknights.catapult.openshift.PolicyBindingTemplate;
import dk.theknights.catapult.strategies.StubbedOpenShiftProject;
import dk.theknights.catapult.strategies.StubbedOpenShiftService;
import dk.theknights.catapult.strategies.StubbedPolicyBinding;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CatapultOpenShiftApplyPolicyBindingTaskTest {

	@Test
	public void testProcess() throws IOException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setOpenShiftProject(new StubbedOpenShiftProject());

		PolicyBindingTemplate policybindingTemplate = Mockito.mock(PolicyBindingTemplate.class);
		doNothing().when(policybindingTemplate).loadFromLocal();
		doReturn(new RoleBinding2()).when(policybindingTemplate).toRoleBinding();
		StubbedOpenShiftService stubedOpenShiftService = Mockito.spy(new StubbedOpenShiftService());
		when(stubedOpenShiftService.getPolicyBinding(ArgumentMatchers.anyString(), ArgumentMatchers.any(StubbedOpenShiftProject.class))).thenReturn(new StubbedPolicyBinding());
		doNothing().when(stubedOpenShiftService).update(ArgumentMatchers.anyString(), ArgumentMatchers.any());

		CatapultOpenShiftApplyPolicyBindingTask spyTask = Mockito.spy(new CatapultOpenShiftApplyPolicyBindingTask());
		doReturn(stubedOpenShiftService).when(spyTask).getOpenShiftService();
		doReturn(policybindingTemplate).when(spyTask).getPolicyBindingTemplate(ArgumentMatchers.any(CatapultContext.class));

		// Act
		spyTask.perform(context);

		// Assert
		verify(stubedOpenShiftService).update(ArgumentMatchers.anyString(), ArgumentMatchers.any(StubbedOpenShiftProject.class));
	}

}
