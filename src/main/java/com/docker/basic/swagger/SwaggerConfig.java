package com.docker.basic.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final Contact CUSTOM_CONTACT = new Contact("Jayendra Birtharia",
            "https://www.linkedin.com/in/jayendra-birtharia-oracle-certified-java-developer-119b11163/",
            "jbirtharia@gmail.com");


    private static final ApiInfo CUSTOM_API_INFO = new ApiInfo("Customer Api Documentation",
            "Api Documentation", "1.0",
            "urn:tos", CUSTOM_CONTACT,
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList());


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                /*Hide all the details on swagger ui except RestController class*/
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .build()
                .apiInfo(CUSTOM_API_INFO);
    }
}
