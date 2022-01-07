package io.github.olivenbarcelon.umjavaws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.SpringVersion;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@Log4j2
@PropertySource("${property.source}")
public class UmJavaWsApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(UmJavaWsApplication.class, args);
		log.info("Spring Version: " + SpringVersion.getVersion());
	}
	
	@Bean
	public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
		ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
		initializer.setConnectionFactory(connectionFactory);
		initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("database/migrations/V1.0.0__user_account.sql")));
		return initializer;
	}
}
