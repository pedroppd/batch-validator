package br.com.oi.batchvalidation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Slf4j
@EnableJpaRepositories
@EnableConfigurationProperties
public class BatchValidationApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BatchValidationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Validation Started...");
	}
}
