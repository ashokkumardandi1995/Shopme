package com.shopme.admin.security;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig{
	
	@Bean
	UserDetailsService userDetailsService() {
		return new ShopmeUserDetailsService();
	}

	@Bean
  PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Bean
	 SecurityFilterChain configureHttp(HttpSecurity http) throws Exception {
	    http.authorizeHttpRequests(auth -> auth
	    		.requestMatchers("/users/**").hasAuthority("Admin")
	    		.requestMatchers("/categories/**","/brands/**").hasAnyAuthority("Admin", "Editor")
	    		.requestMatchers("/products/**").hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")	    		.anyRequest().authenticated()
	    		).formLogin(form -> form
	    				.loginPage("/login")
	    				.usernameParameter("email").permitAll())
	    .logout(logout -> logout.permitAll())
	    .rememberMe(rem -> rem.key("Abcdefghijklmnopqrs_1234567890")
	    		.tokenValiditySeconds(7 * 24 * 60 * 60));
	     
	    return http.build();
	}
	@Bean
	WebSecurityCustomizer configureWebSecurity() throws Exception{
		return (web)-> web.ignoring().requestMatchers("/images/**","/js/**","/webjars/**");
	}

	
	}


	

	