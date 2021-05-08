package com.isfood;

import com.isfood.infrastructure.repository.spec.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class IsfoodApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsfoodApiApplication.class, args);
	}

}

