package org.automation.base;

import static org.automation.config.DriverFactory.clearCookies;
import static org.automation.config.DriverFactory.getDriver;
import static org.automation.logger.Log.error;
import static org.automation.logger.Log.info;
import static org.openqa.selenium.OutputType.BYTES;

import org.openqa.selenium.TakesScreenshot;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class LoggingHooks {

	@Before
	public void setup(Scenario sc) {
		info("Scenario [" + sc.getName() + "] execution started");
	}

	@After
	public void teardown(Scenario sc) {
		clearCookies();
		if (sc.isFailed()) {
			error("Scenario [" + sc.getName() + "] " + sc.getStatus(), null);
			byte[] data = ((TakesScreenshot) getDriver()).getScreenshotAs(BYTES);
			sc.attach(data, "image/png", "Failure");
		} else {
			info("Scenario [" + sc.getName() + "] " + sc.getStatus());
		}
	}

}
