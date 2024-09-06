package com.app.agreement.configurations;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfiguration {

    @Value("${cloudName}")
    private String cloudName;
    @Value("${cloudApiKey}")
    private String cloudApiKey;
    @Value("${cloudApiSecret}")
    private String cloudApiSecret;

    @Bean
    public Cloudinary getCloudinary() {

        Map config = new HashMap();
        config.put("cloud_name", cloudName);
        config.put("api_key", cloudApiKey);
        config.put("api_secret", cloudApiSecret);
        config.put("secure", true);

         return new Cloudinary(config);
    }
}
