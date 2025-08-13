package com.furqan.crud_demo_inventory.security;

import com.furqan.crud_demo_inventory.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class DemoSecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public DemoSecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }



    // BCrypt encoder bean for password hashing
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager for custom UserDetailsService
    @Bean
    public AuthenticationManager authenticationManager(
            org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration authConfig
    ) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/products/showFormForAdd").hasRole("ADMIN")
                                .requestMatchers("/products/showFormForUpdate").hasAnyRole("MANAGER", "ADMIN")
                                .requestMatchers("/products/save").hasAnyRole("MANAGER", "ADMIN")
                                .requestMatchers("/products/delete").hasRole("ADMIN")
                                .requestMatchers("/products/import").hasRole("ADMIN")
                                .requestMatchers("/products/list").hasAnyRole("EMPLOYEE", "MANAGER", "ADMIN")

                                // New role-based page mappings
                                .requestMatchers("/admin-role").hasRole("ADMIN")
                                .requestMatchers("/manager-role").hasAnyRole("MANAGER", "ADMIN")
                                .requestMatchers("/employ-role").hasAnyRole("EMPLOYEE", "MANAGER", "ADMIN")

                                .requestMatchers("/signup", "/verify", "/css/**", "/js/**").permitAll()

                                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .usernameParameter("email") // <-- tell Spring to expect "email"
                        .passwordParameter("password")
                        .loginPage("/showMyLoginPage")
                        .loginProcessingUrl("/authenticateTheUser")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }
}
