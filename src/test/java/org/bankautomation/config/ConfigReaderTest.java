package org.bankautomation.config;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ConfigReaderTest {

    @Test
    public void shouldLoadApplicationUrl() {

        String url = ConfigReader.get("base.url");

        Assert.assertEquals(
                url,
                "http://localhost:5173"
        );
    }

    @Test
    public void shouldLoadApiUrl() {

        String url = ConfigReader.get("api.base.url");

        Assert.assertEquals(
                url,
                "http://localhost:8080/api/v1"
        );
    }

    @Test
    public void shouldLoadBrowser() {

        String browser = ConfigReader.get("browser");

        Assert.assertEquals(
                browser,
                "chrome"
        );
    }

    @Test
    public void shouldLoadTimeout() {

        int timeout = ConfigReader.getInt("explicit.wait");

        Assert.assertEquals(
                timeout,
                15
        );
    }

    @Test
    public void shouldLoadHeadlessSetting() {

        boolean headless =
                ConfigReader.getBoolean("headless");

        Assert.assertFalse(headless);
    }
}