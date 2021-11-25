package semicolon.umjavaws;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import semicolon.umjavaws.info.Info;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UmJavaWsApplicationTests {
	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	void contextLoads() {
		webTestClient.get().uri("/info").exchange().expectStatus().isOk().expectBody(Info.class);
	}
}
