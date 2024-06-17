package com.nina.montrack;

import com.nina.montrack.config.RSAKeyConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RSAKeyConfigProperties.class)
public class MontrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(MontrackApplication.class, args);
	}

}
