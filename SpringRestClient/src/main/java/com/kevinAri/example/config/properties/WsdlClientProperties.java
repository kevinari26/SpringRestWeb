package com.kevinAri.example.config.properties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
// prefix harus kebab-case
@ConfigurationProperties(prefix="wsdl-client")
@Setter(AccessLevel.PACKAGE)
@Getter
@ToString
public class WsdlClientProperties {
    private Integer connectTimeout;
    private Integer requestTimeout;

    private WsdlClientProperty soapWeb1;
    private WsdlClientProperty soapWeb2;


    @Setter(AccessLevel.PACKAGE)
    @Getter
    @ToString
    public static class WsdlClientProperty {
        private String endpoint;
        private String packageName;
        private String username;
        private String password;
    }
}
