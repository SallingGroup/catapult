package dk.theknights.catapult.strategies.adapter.tasks;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.CatapultTemplate;
import dk.theknights.catapult.bitbucket.BitbucketAPI;
import dk.theknights.catapult.strategies.StubbedBitbucketWebhook;
import dk.theknights.catapult.strategies.adapter.tasks.bitbucket.CatapultFetchBitbucketTemplateTask;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

/**
 * Created by Ole Gregersen (ole.gregersen@sallinggroup.com) on 3/9/18.
 */
public class CatapultFetchTemplateTaskTest {

	@Test
	public void testPerformWithValidTemplate() throws IOException {
		// Arrange
		CatapultContext context = new CatapultContext();
		StubbedBitbucketWebhook webhook = new StubbedBitbucketWebhook();
		context.setWebhook(webhook);
		context.setCatapultTemplate(null);

		BitbucketAPI mockedBitbucketAPI = Mockito.mock(BitbucketAPI.class);
		CatapultFetchBitbucketTemplateTask task = new CatapultFetchBitbucketTemplateTask();
		CatapultFetchBitbucketTemplateTask spyTask = Mockito.spy(task);
		when(spyTask.getCatapultTemplate(ArgumentMatchers.anyString())).thenReturn(new CatapultTemplate("demotest openshift/templates/catapult.json demotest"));
		doReturn(mockedBitbucketAPI).when(spyTask).getBitbucketAPI(ArgumentMatchers.any());
		doReturn("").when(mockedBitbucketAPI).getRawCommit(ArgumentMatchers.any());

		// Act
		spyTask.perform(context);

		// Assert
		assertNotNull("context must have template.", context.getCatapultTemplate());
	}

	@Test
	public void testPerformWithInValidTemplate() throws IOException {
		// Arrange
		BitbucketAPI bitbucketapi = Mockito.mock(BitbucketAPI.class);
		when(bitbucketapi.getRawCommit(ArgumentMatchers.anyString())).thenThrow(new IOException());

		CatapultContext context = new CatapultContext();
		context.setCatapultTemplate(new CatapultTemplate("testing testing testing"));
		StubbedBitbucketWebhook webhook = new StubbedBitbucketWebhook();
		context.setWebhook(webhook);

		CatapultFetchBitbucketTemplateTask task = new CatapultFetchBitbucketTemplateTask();
		CatapultFetchBitbucketTemplateTask spyTask = Mockito.spy(task);
		when(spyTask.getBitbucketAPI(null)).thenReturn(bitbucketapi);

		// Act
		spyTask.perform(context);

		// Assert
		assertNull("context must NOT have template.", context.getCatapultTemplate());
	}

}
