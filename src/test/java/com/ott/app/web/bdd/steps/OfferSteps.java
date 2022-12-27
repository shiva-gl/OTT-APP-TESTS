package com.ott.app.web.bdd.steps;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import com.ott.app.web.annotation.LazyAutowired;
import com.ott.app.web.browsermobproxy.BrowserMobProxy;
import com.ott.app.web.page.OfferPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import net.minidev.json.parser.JSONParser;
import org.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@CucumberContextConfiguration
@SpringBootTest
public class OfferSteps {

    @LazyAutowired
    private OfferPage offerPage;

    @Given("I am on ott website")
    public void launchSite() {
        this.offerPage.goTo();
    }

    @And("I enter invalid credentials")
    public void enterCredentials() {
        this.offerPage.setEmail("AAAAAAAAA");
        this.offerPage.setPassword("98989898");

    }

    @When("I capture network traffic from browermobproxy")
    public void networkTraffic() throws InterruptedException {
        BrowserMobProxy.proxy.newHar("offerFailureAttempt");
        this.offerPage.tapOfferbtn();
        BrowserMobProxy.waitTillEventTriggers("offerFailureAttempt");
    }

    @Then("I should see the event triggered with {string}")
    public void verifyEventTriggered(String msg) throws IOException {
        BrowserMobProxy.writeHarToFile("offerFailureAttempt");
        System.out.println(BrowserMobProxy.event);
        assertTrue(BrowserMobProxy.event.contains(msg));
    }

}
