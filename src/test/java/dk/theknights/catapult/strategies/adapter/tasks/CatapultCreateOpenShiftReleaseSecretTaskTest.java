package dk.theknights.catapult.strategies.adapter.tasks;

import com.openshift.internal.restclient.model.Secret;
import com.openshift.restclient.model.IProject;
import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.CatapultException;
import dk.theknights.catapult.config.CatapultConfig;
import dk.theknights.catapult.config.CatapultProperties;
import dk.theknights.catapult.strategies.StubbedBitbucketWebhook;
import dk.theknights.catapult.strategies.StubbedOpenShiftProject;
import dk.theknights.catapult.strategies.StubbedOpenShiftService;
import dk.theknights.catapult.strategies.StubbedSecret;
import dk.theknights.catapult.strategies.StubbedServiceAccount;
import org.jboss.dmr.ModelNode;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doReturn;

/**
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/13/18.
 */
public class CatapultCreateOpenShiftReleaseSecretTaskTest {

	@Test
	public void testProcess() throws IOException, CatapultException {
		// Arrange
		CatapultContext context = Mockito.spy(new CatapultContext());
		context.setWebhook(new StubbedBitbucketWebhook());
		context.setOpenShiftProject(new StubbedOpenShiftProject());

		StubbedOpenShiftService stubedOpenShiftService = Mockito.spy(new StubbedOpenShiftService());
		StubbedSecret stubedSecret = Mockito.mock(StubbedSecret.class);
		doReturn(stubedSecret).when(stubedOpenShiftService).createDockerSecret(ArgumentMatchers.anyString(), anyString(), any());
		doReturn(new StubbedServiceAccount(new ModelNode(), null, null)).when(stubedOpenShiftService).addImagePullSecretToServiceAccount(ArgumentMatchers.any(Secret.class), ArgumentMatchers.matches("default"), any(IProject.class));
		doReturn(new StubbedServiceAccount(new ModelNode(), null, null)).when(stubedOpenShiftService).addSecretToServiceAccount(ArgumentMatchers.any(Secret.class), anyString());
		CatapultProperties catapultProperties = new CatapultProperties();
		catapultProperties.setReleaseRegistrySecretName("some-secret-name");
		CatapultConfig catapultConfig = Mockito.mock(CatapultConfig.class);
		doReturn(catapultConfig).when(context).getCatapultConfig();
		doReturn(catapultProperties).when(catapultConfig).getCatapultProperties();

		CatapultCreateOpenShiftReleaseSecretTask spyTask = Mockito.spy(new CatapultCreateOpenShiftReleaseSecretTask());
		doReturn(new File("/")).when(spyTask).getReleaseSecretFile(ArgumentMatchers.any());
		doReturn(stubedOpenShiftService).when(spyTask).getOpenShiftService();

		// Act
		spyTask.perform(context);

		// Assert
		assertEquals(1, context.getSecretsAdded().size());
		assertEquals(2, context.getModifiedServiceAccounts().size());
	}

}
