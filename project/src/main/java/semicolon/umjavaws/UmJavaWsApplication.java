package semicolon.umjavaws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@Log4j2
public class UmJavaWsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UmJavaWsApplication.class, args);
		log.info("Spring Version: " + SpringVersion.getVersion());
	}
}
