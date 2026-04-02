package org.yash.quize_app.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.yash.quize_app.security.JwtFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain  securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->auth
                .requestMatchers("/auth/**", "/api/auth/**", "/error").permitAll()
                .requestMatchers("/", "/*.html", "/css/**", "/js/**").permitAll()
                .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/api/student/**").hasAuthority("STUDENT")
                .requestMatchers("/api/user/**").authenticated()
                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
return http.build();

    }

}
