package com.walterpaulo.produto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

	@Bean
	// authentication
	public UserDetailsService userDetailsService() {
		UserDetails admin = User.withUsername("admin").password(passwordEncoder.encode("123")).roles("ADMIN").build();
		UserDetails user = User.withUsername("walter").password(passwordEncoder.encode("123"))
				.roles("USER", "ADMIN", "HR").build();
		return new InMemoryUserDetailsManager(admin, user);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(authorize -> {
			authorize.requestMatchers(HttpMethod.GET, "/", "/logout").permitAll()
					.requestMatchers(HttpMethod.POST, "/login").permitAll().anyRequest().authenticated();
		});

		http.addFilterBefore(new WebFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

}
