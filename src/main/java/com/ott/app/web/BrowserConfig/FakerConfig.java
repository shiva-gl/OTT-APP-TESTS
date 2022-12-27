package com.ott.app.web.BrowserConfig;
import com.github.javafaker.Faker;
import com.ott.app.web.annotation.LazyConfiguration;
import org.springframework.context.annotation.Bean;

@LazyConfiguration
public class FakerConfig {

    @Bean
    public Faker getFaker(){
        return new Faker();
    }

}
