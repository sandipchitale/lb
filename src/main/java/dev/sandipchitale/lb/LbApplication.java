package dev.sandipchitale.lb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class LbApplication {

	public static void main(String[] args) {
		SpringApplication.run(LbApplication.class, args);
	}

	@RestController
	public static class WhichPortController {


		private final ServerProperties serverProperties;

        public WhichPortController(ServerProperties serverProperties) {
            this.serverProperties = serverProperties;
        }

        @GetMapping("/")
		public String index() {
			return "Hello from " + serverProperties.getPort();
		}
	}

}
