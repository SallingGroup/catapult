package dk.theknights.catapult;

import dk.theknights.catapult.strategies.StubbedBitbucketWebhook;
import dk.theknights.catapult.strategies.StubbedGitLabWebhook;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class CatapultContextTest {

	@Before
	public void setUp() {

	}

	@Test
	public void testRepositoryTypeBitbucket() {
		// Arrange
		CatapultContext context = new CatapultContext();
		StubbedBitbucketWebhook webhook = new StubbedBitbucketWebhook();
		context.setWebhook(webhook);

		// Act
		RepositoryType repositoryType = context.getRepositoryType();

		// Assert
		assertEquals("repositoryType must be type BITBUCKET", repositoryType, RepositoryType.BITBUCKET);
	}

	@Test
	public void testRepositoryTypeGitLab() {
		// Arrange
		CatapultContext context = new CatapultContext();
		StubbedGitLabWebhook webhook = new StubbedGitLabWebhook();
		context.setWebhook(webhook);

		// Act
		RepositoryType repositoryType = context.getRepositoryType();

		// Assert
		assertEquals("repositoryType must be type GITLAB", repositoryType, RepositoryType.GITLAB);
	}

	@Test
	public void testRepositoryTypeGitHub() {
		// Arrange
		CatapultContext context = new CatapultContext();
		StubbedBitbucketWebhook webhook = Mockito.mock(StubbedBitbucketWebhook.class);
		context.setWebhook(webhook);
		when(webhook.getRepositoryUrl()).thenReturn("https://github.org/fakerepo");

		// Act
		RepositoryType repositoryType = context.getRepositoryType();

		// Assert
		assertEquals("repositoryType must be type GITHUB", repositoryType, RepositoryType.GITHUB);
	}

}
