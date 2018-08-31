package dk.theknights.catapult.strategies.adapter.tasks.bitbucket;

import dk.theknights.catapult.CatapultContext;
import dk.theknights.catapult.bitbucket.BitbucketAPI;
import dk.theknights.catapult.strategies.StubbedBitbucketWebhook;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class BitbucketDetectTemplateChangeTaskTest {

	@Before
	public void setUp() {

	}

	@Test
	public void testProcessBitbucketWebhookWithTemplateChange() throws IOException {
		// Arrange
		BitbucketAPI bitbucketapi = Mockito.mock(BitbucketAPI.class);
		when(bitbucketapi.getRawCommit(ArgumentMatchers.anyString())).thenReturn("demotest openshift/templates/catapult.json demotest");

		CatapultContext context = new CatapultContext();
		context.setCatapultTemplateChanged(false);
		context.setWebhook(new StubbedBitbucketWebhook());
		BitbucketDetectTemplateChangeTask task = new BitbucketDetectTemplateChangeTask();
		BitbucketDetectTemplateChangeTask spyTask = Mockito.spy(task);
		when(spyTask.getBitbucketAPI(null)).thenReturn(bitbucketapi);

		// Act
		spyTask.perform(context);

		// Assert
		assertTrue("Catapult template file must have a change", context.isCatapultTemplateChanged());
	}

	@Test
	public void testProcessBitbucketWebhookWithNoTemplateChange() throws IOException {
		// Arrange
		BitbucketAPI bitbucketapi = Mockito.mock(BitbucketAPI.class);
		when(bitbucketapi.getRawCommit(ArgumentMatchers.anyString())).thenReturn("demotest openshift/templates/NOCHANGE.json demotest");

		CatapultContext context = new CatapultContext();
		context.setCatapultTemplateChanged(true);
		context.setWebhook(new StubbedBitbucketWebhook());
		BitbucketDetectTemplateChangeTask task = new BitbucketDetectTemplateChangeTask();
		BitbucketDetectTemplateChangeTask spyTask = Mockito.spy(task);
		when(spyTask.getBitbucketAPI(null)).thenReturn(bitbucketapi);

		// Act
		spyTask.perform(context);

		// Arrange
		assertFalse("Catapult template file must not have a change.", context.isCatapultTemplateChanged());
	}

}
