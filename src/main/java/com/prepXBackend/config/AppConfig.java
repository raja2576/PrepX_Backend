package com.prepXBackend.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.AuthorizeHttpRequestsDsl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class AppConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http ) throws Exception{
		
		http.sessionManagement(Management->Management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		     .authorizeHttpRequests(auth -> auth
		                .requestMatchers("/api/**","/auth/signup","/auth/signing","/public/**","/api/progress/save","/auth/send-otp","/auth/verify-otp","/auth/register","/auth/send-otp-register","/auth/verify-otp-register").permitAll()  // Allow public access"
		                .requestMatchers("/api/progress/**").authenticated()		            )
		     .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
		     .csrf(csrf -> csrf.disable())
		     .cors(cors->cors.configurationSource(corsConfigurationSource()));
		return http.build();
		
	}
	
	
	private CorsConfigurationSource corsConfigurationSource()
	{
		return new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration cfg=new CorsConfiguration();
				cfg.setAllowedOrigins(Arrays.asList(
						    "http://localhost:8080/",
						    "http://localhost:3000",
						    "http://localhost:4200",
						    "https://prepex.netlify.app"
						 
						));
				 cfg.setAllowedMethods(Collections.singletonList("*"));
			        cfg.setAllowedHeaders(Collections.singletonList("*"));
			        cfg.addAllowedOriginPattern("*");
			        cfg.setAllowCredentials(true);  //
                   cfg.setExposedHeaders(Arrays.asList("Authorization"));
                   cfg.setMaxAge(3600L);
			return cfg;
			}
		};
		

		
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
