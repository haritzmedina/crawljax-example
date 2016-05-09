package com.haritzmedina.crawling;

import com.crawljax.browser.EmbeddedBrowser;
import com.crawljax.core.CrawljaxRunner;
import com.crawljax.core.configuration.BrowserConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.core.configuration.Form;
import com.crawljax.core.configuration.InputSpecification;
import com.crawljax.plugins.crawloverview.CrawlOverview;

import java.util.concurrent.TimeUnit;

/**
 * Created by Haritz Medina on 09/05/2016.
 */
public class CrawljaxExample {

    private static final long WAIT_TIME_AFTER_EVENT = 500;
    private static final long WAIT_TIME_AFTER_RELOAD = 50;
    private static final String URL = "http://kaixomundua.haritzmedina.com/";

    public static void main(String[] args){
        CrawljaxConfiguration.CrawljaxConfigurationBuilder builder = CrawljaxConfiguration.builderFor(URL);
        builder.crawlRules().insertRandomDataInInputForms(false);
        builder.crawlRules().setInputSpec(getInputSpecification());

        // click these elements
        builder.crawlRules().clickDefaultElements();
        builder.crawlRules().click("div");

        builder.setMaximumStates(10);
        builder.setMaximumDepth(3);
        builder.crawlRules().clickElementsInRandomOrder(false);

        // Set timeouts
        builder.crawlRules().waitAfterReloadUrl(WAIT_TIME_AFTER_RELOAD, TimeUnit.MILLISECONDS);
        builder.crawlRules().waitAfterEvent(WAIT_TIME_AFTER_EVENT, TimeUnit.MILLISECONDS);

        // We want to use two browsers simultaneously.
        builder.setBrowserConfig(new BrowserConfiguration(EmbeddedBrowser.BrowserType.FIREFOX, 1));

        // CrawlOverview
        builder.addPlugin(new CrawlOverview());

        CrawljaxRunner crawljax = new CrawljaxRunner(builder.build());
        crawljax.call();
    }

    private static InputSpecification getInputSpecification() {
        InputSpecification input = new InputSpecification();
        Form contactForm = new Form();
        contactForm.field("username").setValues("haritz", "pepito", "admin");
        contactForm.field("password").setValues("pepito", "juanito"); // TODO set password
        input.setValuesInForm(contactForm).beforeClickElement("input").withText("Login");
        return input;
    }
}
