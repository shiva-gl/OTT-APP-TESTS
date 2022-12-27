package com.ott.app.web.browsermobproxy;

import com.ott.app.web.annotation.LazyConfiguration;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;

@LazyConfiguration
public class ProxyWebCaps {

    @Autowired
    protected BrowserMobProxy proxySetup;

    public DesiredCapabilities proxyCap(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.acceptInsecureCerts();
        capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS,true);
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capabilities.setCapability(CapabilityType.PROXY, this.proxySetup.setup());
        return capabilities;
    }
}
