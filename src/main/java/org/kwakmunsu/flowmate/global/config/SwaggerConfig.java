package org.kwakmunsu.flowmate.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String JWT = "JWT";
    private static final String BEARER_SCHEME = "Bearer";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(getApiInfo())
                .addSecurityItem(getSecurityRequirement())
                .components(getComponents())
                .servers(List.of(getLocalDevServer()));
    }

        private Info getApiInfo() {
            return new Info()
                    .title("")
                    .description("FlowMate API 문서")
                    .version("1.0.0");
        }

    private SecurityRequirement getSecurityRequirement() {
        return new SecurityRequirement()
                .addList(JWT);
    }

    private Components getComponents() {
        return new Components()
                .addSecuritySchemes(JWT, getSecurityScheme());
    }

    private SecurityScheme getSecurityScheme() {
        return new SecurityScheme()
                .name(JWT)
                .type(SecurityScheme.Type.HTTP)
                .scheme(BEARER_SCHEME)
                .bearerFormat(JWT)
                .description("AccessToken을 입력해주세요. 형식: Bearer {token}");
    }

    private Server getLocalDevServer() {
        return new Server()
                .description("Development Server")
                .url("http://localhost:8080"); // TODO: 변경 예정
    }

}