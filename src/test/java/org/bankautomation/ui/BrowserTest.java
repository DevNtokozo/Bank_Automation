package org.bankautomation.ui;

import org.testng.Assert;
import org.testng.annotations.Test;

public class BrowserTest extends BaseTest {

    @Test
    public void shouldOpenDevBank() {

        String title = driver.getTitle();

        System.out.println(
                "Dev Bank page title: " + title
        );

        Assert.assertNotNull(title);
    }
}