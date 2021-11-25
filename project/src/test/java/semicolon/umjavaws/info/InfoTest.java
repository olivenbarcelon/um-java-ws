package semicolon.umjavaws.info;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InfoTest {
	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	void info() {
		webTestClient.get().uri("/info").exchange().expectStatus().isOk().expectBody(Info.class);
	}
}
