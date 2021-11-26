package semicolon.umjavaws.info;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InfoTest {
    @Autowired
    private WebTestClient webTestClient;
    
    @Test
    public void testInfo() {
        webTestClient.get().uri("/api/info").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk().expectHeader()
            .contentType(MediaType.APPLICATION_JSON).expectBodyList(Info.class);
    }
}
