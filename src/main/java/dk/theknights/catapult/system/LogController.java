package dk.theknights.catapult.system;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created by itsolg on 6/26/17.
 */
@Controller
public class LogController {

	private static final String LOG_FILE_NAME = "/usr/local/tomcat/catapult.log";

	@SuppressFBWarnings("DMI_HARDCODED_ABSOLUTE_FILENAME")
	@RequestMapping(value = "/log/view", method = RequestMethod.GET)
	public String genericGitLabWebhook(final HttpServletRequest request, final HttpServletResponse response, final Map<String, Object> model) {

		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(LOG_FILE_NAME);
			String everything = IOUtils.toString(inputStream);
			everything = everything.replaceAll("\\r?\\n", "<br/>");
			model.put("name", everything);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return "viewlog";
	}

	@SuppressFBWarnings("DMI_HARDCODED_ABSOLUTE_FILENAME")
	@RequestMapping(value = "/log/raw", method = RequestMethod.GET)
	public void genericGitLabWebhook(final HttpServletRequest request, final HttpServletResponse response) {
		try (FileInputStream fis = new FileInputStream(LOG_FILE_NAME)) {
			response.setContentType("txt/plain");

			IOUtils.copy(fis, response.getOutputStream());
			response.flushBuffer();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
