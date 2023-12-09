package com.kevinAri.example.config;//package com.template.restweb.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.*;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.Collections;
//import java.util.List;
//
//
//@Configuration
//@EnableSwagger2
//@Import(springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class)
//public class Swagger2Config {
//    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";
//
//    // URL untuk akses swagger: http://localhost:8080/swagger-ui.html
//    @Bean
//    public Docket newsApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//            .select()
//            .apis(RequestHandlerSelectors.any())
//            .paths(PathSelectors.any())
//            .build()
//            .securitySchemes(Collections.singletonList(apiKey()))
//            .securityContexts(Collections.singletonList(securityContext()))
//            .apiInfo(apiEndPointsInfo());
//    }
//
//    @Bean
//    SecurityContext securityContext() {
////        return SecurityContext.builder()
////            .securityReferences(defaultAuth())
////            .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
////            .build();
//        return SecurityContext.builder().securityReferences(defaultAuth()).build();
//    }
//
//    List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return Collections.singletonList(new SecurityReference("Bearer", authorizationScopes));
//    }
//
//    private ApiKey apiKey() {
//        return new ApiKey("Bearer", "Authorization", "header");
//    }
//
//    private ApiInfo apiEndPointsInfo() {
//        return new ApiInfoBuilder().title("REST API")
//            .description("Product REST API")
//            .contact(new Contact("Recurring Team", "alpabit.com", ""))
//            .license("Apache 2.0")
//            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
//            .version("1.0.0")
//            .build();
//    }
//
//
//
//}
//
