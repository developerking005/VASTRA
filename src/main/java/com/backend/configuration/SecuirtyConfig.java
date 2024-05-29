package com.backend.configuration;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.backend.jwt.JWTAuthenticationEntryPoint;
import com.backend.jwt.JwtAuthenticationFilter;

import jakarta.servlet.http.HttpServletRequest;





@Configuration
public class SecuirtyConfig {
	
	 @Autowired
	 private JWTAuthenticationEntryPoint point;
	 
	 @Autowired
	 private JwtAuthenticationFilter filter;
	 
	 @Autowired
	 private UserDetailsService userDetailsService;
	 
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	 

	 @Bean
	 public SecurityFilterChain secuirtyFilterChain(HttpSecurity http) throws Exception {
		 
//		 because direct csrf is depricated that's why we are using (csrf-> csrf. type thing so these thing are saying as lamda)
		 http.csrf(csrf-> csrf.disable())
		 	.cors(cors-> cors.configurationSource(new CorsConfigurationSource() {
				
				@Override
				public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
					
					CorsConfiguration cfg= new CorsConfiguration();
					cfg.setAllowedOrigins(Arrays.asList(
							"http://localhost:3000/",
							"https://avatar-ecommerce.vercel.app/",
							"https://vastra-sable.vercel.app/"
							));
					cfg.setAllowedMethods(Collections.singletonList("*"));
					cfg.setAllowCredentials(true);
					cfg.setAllowedHeaders(Collections.singletonList("*"));
					cfg.setExposedHeaders(Arrays.asList("Authorization"));
					cfg.setMaxAge(3600L);
					return cfg;
				}
			})) 
		 	.authorizeHttpRequests(auth-> auth 
		 			.requestMatchers("/auth/login", "/auth/signup").permitAll()
	                .anyRequest().authenticated())
		 	.exceptionHandling(ex-> ex.authenticationEntryPoint(point))
		 	.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))		 			 	
		 	;
		 
		 http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);		 
		 return http.build();
	 }
	 
	 @Bean
	 public DaoAuthenticationProvider doDaoAuthenticationProvider() {
		 DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		 provider.setUserDetailsService(userDetailsService);
		 provider.setPasswordEncoder(passwordEncoder);
		 return provider;
	 }
	 
	 @Bean
		public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
			return builder.getAuthenticationManager();
		}
}
