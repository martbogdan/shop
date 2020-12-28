package com.smallshop.shop;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class WebAutoTests extends WebDriverSettings{

    @Test
    public void checkTitle() {
        driver.get("https://mysshop.herokuapp.com/");
        String title = driver.getTitle();

        Assert.assertEquals("Shop", title);
    }

}
