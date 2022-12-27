package com.ott.app.web;

import org.openqa.selenium.remote.DesiredCapabilities;

public class WebCaps {

    public DesiredCapabilities capabilities ;
    static WebCaps caps_refrence ;

    public void setWebCaps(DesiredCapabilities caps, WebCaps refernce){
         this.capabilities = caps;
         this.caps_refrence = refernce;
    }

    public static WebCaps getWebCaps(){
        return caps_refrence;
    }
}
