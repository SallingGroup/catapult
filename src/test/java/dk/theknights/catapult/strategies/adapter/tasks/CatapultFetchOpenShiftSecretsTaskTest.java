package dk.theknights.catapult.strategies.adapter.tasks;

import com.openshift.internal.restclient.model.Secret;
import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.CatapultException;
import dk.theknights.catapult.CatapultTemplate;
import dk.theknights.catapult.strategies.StubbedOpenShiftService;
import dk.theknights.catapult.strategies.StubbedSecret;
import org.jboss.dmr.ModelNode;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;

public class CatapultFetchOpenShiftSecretsTaskTest {

	private static final String CATAPULT_TEMPLATE_SECRET_SOURCE = "{\"apiVersion\":\"v1\",\"kind\":\"Template\",\"metadata\":{\"name\":\"${NAME}\",\"annotations\":{\"" + CatapultFetchOpenShiftSecretsTask.CATAPULT_SECRET_SOURCE_TYPE + "\":\"secretsourcething\"}}}";
	private static final String CATAPULT_TEMPLATE_NO_SECRET_SOURCE = "{\"apiVersion\":\"v1\",\"kind\":\"Template\",\"metadata\":{\"name\":\"${NAME}\"}}";

	@Test
	public void testProcessWithExistingSecrets() throws IOException, CatapultException {
		// Arrange
		CatapultContext context = new CatapultContext();

		context.setCatapultTemplate(new CatapultTemplate(CATAPULT_TEMPLATE_SECRET_SOURCE));
		CatapultFetchOpenShiftSecretsTask spyTask = Mockito.spy(new CatapultFetchOpenShiftSecretsTask());

		StubbedOpenShiftService stubedOpenShiftService = Mockito.mock(StubbedOpenShiftService.class);
		List<Secret> secretList = new ArrayList<>();
		StubbedSecret secret1 = new StubbedSecret(new ModelNode(), null);
		secret1.setAnnotation(CatapultFetchOpenShiftSecretsTask.CATAPULT_SECRET_SOURCE_IDENTIFIER, "somevaluethat is not needed here");
		secretList.add(secret1);

		StubbedSecret secret2 = new StubbedSecret(new ModelNode(), null);
		secret2.setAnnotation(CatapultFetchOpenShiftSecretsTask.CATAPULT_SECRET_SOURCE_IDENTIFIER, "somevaluethat is not needed here");
		secretList.add(secret2);

		StubbedSecret secret3 = new StubbedSecret(new ModelNode(), null);
		secret3.setAnnotation(CatapultFetchOpenShiftSecretsTask.CATAPULT_SECRET_SOURCE_IDENTIFIER, "somevaluethat is not needed here");
		secretList.add(secret3);

		doReturn(secretList).when(stubedOpenShiftService).getSecrets(ArgumentMatchers.anyString());
		doReturn(stubedOpenShiftService).when(spyTask).getOpenShiftService();

		// Act
		spyTask.perform(context);

		// Assert
		assertThat(context.getSecrets(), hasSize(3));
	}

	@Test
	public void testProcessWithNoExistingSecrets() throws IOException, CatapultException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultTemplate(new CatapultTemplate(CATAPULT_TEMPLATE_NO_SECRET_SOURCE));
		CatapultFetchOpenShiftSecretsTask spyTask = Mockito.spy(new CatapultFetchOpenShiftSecretsTask());
		StubbedOpenShiftService stubedOpenShiftService = Mockito.mock(StubbedOpenShiftService.class);
		doReturn(stubedOpenShiftService).when(spyTask).getOpenShiftService();
		doReturn(new ArrayList<>()).when(stubedOpenShiftService).getSecrets(ArgumentMatchers.anyString());

		// Act
		spyTask.perform(context);

		// Assert
		assertThat(context.getSecrets(), hasSize(0));
	}

	@Test(expected = CatapultException.class)
	public void testProcessWithNoTemplate() throws IOException, CatapultException {
		// Arrange
		CatapultContext context = new CatapultContext();
		context.setCatapultTemplate(null);
		CatapultFetchOpenShiftSecretsTask spyTask = Mockito.spy(new CatapultFetchOpenShiftSecretsTask());
		StubbedOpenShiftService stubedOpenShiftService = Mockito.mock(StubbedOpenShiftService.class);
		doReturn(stubedOpenShiftService).when(spyTask).getOpenShiftService();
		doReturn(new ArrayList<>()).when(stubedOpenShiftService).getSecrets(ArgumentMatchers.anyString());

		// Act
		spyTask.perform(context);

		// Assert
		assertThat(context.getSecrets(), hasSize(0));
	}

}
