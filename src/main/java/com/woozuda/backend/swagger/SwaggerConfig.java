package com.woozuda.backend.swagger;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(
        servers = {
                @Server(url = "http://localhost:8080", description = "로컬 서버"),
                @Server(url = "https://woozuda_release.shop", description = "배포 서버")
        })
public class SwaggerConfig {

}
