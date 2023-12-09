package com.kevinAri.example.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigBasicAuth {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
//                .passwordEncoder(new BCryptPasswordEncoder())
                    .withUser("namhm")
                    .password(new BCryptPasswordEncoder().encode("pass"))
                    .roles("USER")
                .and()
                    .withUser("user")
                    .password(new BCryptPasswordEncoder().encode("pass"))
                    .roles("ADMIN")
        ;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().and()
//                .authorizeHttpRequests((authz) -> authz
//                        .antMatchers("/test2/**").hasRole("CAPTAIN")
//                        .antMatchers("/test/**").hasRole("CREW")
//                        .anyRequest().authenticated()
//                )
//                .authorizeRequests().anyRequest().authenticated()
//                .and()

                // no security
                .authorizeRequests().anyRequest().permitAll().and()

//                .httpBasic(Customizer.withDefaults());
                .httpBasic();
        return http.build();
    }
}



