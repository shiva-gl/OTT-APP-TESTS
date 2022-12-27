package com.ott.app.web.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

public abstract class BasePage {
    @Autowired
    protected WebDriver driver;

    @Autowired
    protected WebDriverWait wait;

    @PostConstruct
    private void init(){
        PageFactory.initElements(this.driver, this);
    }

    @Value("${application.url}")
    private String url;

    public void goTo(){
        this.driver.get(url);
    }

    public void close(){
        this.driver.quit();
    }

    public abstract boolean isAt();
}
