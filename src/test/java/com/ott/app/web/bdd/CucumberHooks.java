package com.ott.app.web.bdd;

import com.ott.app.web.WebCaps;
import com.ott.app.web.annotation.LazyAutowired;
import com.ott.app.web.browsermobproxy.BrowserMobProxy;
import com.ott.app.web.browsermobproxy.ProxyWebCaps;
import com.ott.app.web.service.ScreenshotService;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.context.ApplicationContext;

public class CucumberHooks {

    @LazyAutowired
    private ScreenshotService screenshotService;

    @LazyAutowired
    private ApplicationContext applicationContext;

    @LazyAutowired
    private ProxyWebCaps proxyCaps;

    @Before
    public void beforeScenario(Scenario scenario){
        WebCaps caps = new WebCaps();
        if(scenario.getSourceTagNames().contains("@event")){
            caps.setWebCaps(proxyCaps.proxyCap(),caps);
        }
        else {
            caps.setWebCaps(new DesiredCapabilities(),caps);
        }
    }

    @AfterStep
    public void afterStep(Scenario scenario){
        if(scenario.isFailed()){
            scenario.attach(this.screenshotService.getScreenshot(), "image/png", scenario.getName());
            //scenario.embed(this.screenshotService.getScreenshot(), "image/png", scenario.getName());
        }
    }

    @After
    public void afterScenario(){
        this.applicationContext.getBean(WebDriver.class).quit();
        BrowserMobProxy.proxy.stop();
    }

}
