package org.wecancodeit.sketchflex;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.wecancodeit.sketchflex.storage.StorageProperties;
import org.wecancodeit.sketchflex.storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class SketchflexApplication {

	public static void main(String[] args) {
		SpringApplication.run(SketchflexApplication.class, args);
	}

	@Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
}
