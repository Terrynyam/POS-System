package com.tenic.possystem.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Terrance Nyamfukudza
 * 30/5/2024
 */
@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI myOpenAPI() {

        Contact contact = new Contact();
        contact.setEmail("tenicedesigners@gmail.com");
        contact.setName("Tenic Engineering Team");
        contact.setUrl("https://www.tenicdesigners.co.zw/#/");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("POS System Service")
                .version("1.0")
                .contact(contact)
                .description("POS System Service").termsOfService("https://www.tenicdesigners.co.zw/#/")
                .license(mitLicense);

        return new OpenAPI().info(info);

    }
}
