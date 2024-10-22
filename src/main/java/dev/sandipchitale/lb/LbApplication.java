package dev.sandipchitale.lb;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.gateway.server.mvc.common.MvcUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RequestPredicates.path;

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
        public String index(HttpServletRequest httpServletRequest) {
            String gatewayPort = httpServletRequest.getHeader("gatewayport");
            return "Hello from "
                    + serverProperties.getPort() +
                    (gatewayPort != null ?  " From gateway at port: " + gatewayPort : "");
        }
    }

    @Configuration
    public static class MicroserviceConfiguration {

        private final ServerProperties serverProperties;

        public MicroserviceConfiguration(ServerProperties serverProperties) {
            this.serverProperties = serverProperties;
        }

        @Bean
        RouterFunction<ServerResponse> microservice() {
            return route("microservice")
                    .route(path("/microservice"), http())
                    .before((ServerRequest serverRequest) -> {
                        ServerRequest request = ServerRequest
                                .from(serverRequest)
                                .uri(URI.create("http://localhost:8085/"))
                                .header("gatewayport", String.valueOf(serverProperties.getPort()))
                                .build();
                        MvcUtils.setRequestUrl(request, request.uri());
                        return request;
                    })
                    .build();
        }

    }
}
