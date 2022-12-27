package com.ott.app.web.browsermobproxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ott.app.web.annotation.LazyConfiguration;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.Proxy;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Inet4Address;

import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;
import java.util.List;


@LazyConfiguration
public class BrowserMobProxy {

    @Value("${proxy.port}")
    private int port;

    @Value("${target_url}")
    private static String target_url;

    public static  BrowserMobProxyServer proxy;

    public static String event_url;

    public  static String event;

    public Proxy setup() {
        proxy = new BrowserMobProxyServer();
        proxy.start(this.port);
        this.event_url = this.target_url;
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        String hostIp = null;
        try {
            hostIp = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        seleniumProxy.setHttpProxy(hostIp + ":" + proxy.getPort());
        seleniumProxy.setSslProxy(hostIp + ":" + proxy.getPort());
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        EnumSet<CaptureType> captureTypes = CaptureType.getAllContentCaptureTypes();
        captureTypes.addAll(CaptureType.getCookieCaptureTypes());
        captureTypes.addAll(CaptureType.getHeaderCaptureTypes());
        captureTypes.addAll(CaptureType.getRequestCaptureTypes());
        captureTypes.addAll(CaptureType.getResponseCaptureTypes());
        proxy.setHarCaptureTypes(captureTypes);
        return seleniumProxy;
    }

    public static void writeHarToFile(String file_name) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        FileWriter writer = null;
        List<HarEntry> logs = proxy.getHar().getLog().getEntries();
        for ( HarEntry data : logs)
            if(data.getRequest().getUrl().contains("https://api-js.mixpanel.com/track")){
                Path currentPath = Paths.get(System.getProperty("user.dir"));
                Path filePath = Paths.get(currentPath.toString(),"target" ,"reports", file_name);
                writer = new FileWriter(filePath.toString()+".json");
                event = mapper.writeValueAsString(data.getRequest().getPostData().getParams());
                String json = mapper.writeValueAsString(data.getRequest());
                writer.write(json);
                writer.close();
            }
    }

    public  static void waitTillEventTriggers(String file_name) throws InterruptedException {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        Path filePath = Paths.get(currentPath.toString(),"target" ,"reports", file_name,".json");
        File eventFile = new File(filePath.toString());
        int wait_time = 0;
        while(wait_time <= 60000){
            if(eventFile.length() != 0) {
                break;
            }
            else {
                wait_time = wait_time + 1000;
                Thread.sleep(1000);
            }

        }
    }

}
