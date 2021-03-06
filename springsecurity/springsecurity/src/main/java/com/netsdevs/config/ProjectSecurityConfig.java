package com.netsdevs.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.netsdevs.filter.AuthoritiesLoggingAfterFilter;
import com.netsdevs.filter.AuthoritiesLoggingAtFilter;
import com.netsdevs.filter.JWTTokenGeneratorFilter;
import com.netsdevs.filter.JWTTokenValidatorFilter;
import com.netsdevs.filter.RequestValidationBeforeFilter;



@Configuration
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter  {

	/**
	 * /myAccount - Secured /myBalance - Secured /myLoans - Secured /myCards -
	 * Secured /notices - Not Secured /contact - Not Secured
	 */
	//@Override
	//protected void configure(HttpSecurity http) throws Exception {

		/**
		 * Default configurations which will secure all the requests
		 */

		/*
		 * http .authorizeRequests() .anyRequest().authenticated() .and()
		 * .formLogin().and() .httpBasic();
		 */

		/**
		 * Custom configurations as per our requirement
		 */

		
//		  http.authorizeRequests()
//		  .antMatchers("/myAccount").authenticated()
//		  .antMatchers("/myBalance").authenticated()
//		  .antMatchers("/myLoans").authenticated()
//		  .antMatchers("/myCards").authenticated()
//		  .antMatchers("/notices").permitAll()
//		  .antMatchers("/contact").permitAll().and().formLogin().and().httpBasic();


		/**
		 * Configuration to deny all the requests
		 */

		
//		  http.authorizeRequests().anyRequest().denyAll().and().formLogin().and().httpBasic();


		/**
		 * Configuration to permit all the requests
		 */

//		http.authorizeRequests().anyRequest().permitAll().and().formLogin().and().httpBasic();
		
	//}
	
//##################################################################
//##################################################################	
//##################################################################	
//Variant 2
	
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
//		//prostoy variant
//		http.authorizeRequests()
//		.antMatchers("/myAccount").authenticated()
//		.antMatchers("/myBalance").authenticated()
//		.antMatchers("/myLoans").authenticated()
//		.antMatchers("/myCards").authenticated()
//		.antMatchers("/notices").permitAll()
//		.antMatchers("/contact").permitAll()
//		.and().formLogin().and().httpBasic();
		
		//variaint s zashitoy ot CROS i CSRF
		http.cors().configurationSource(new CorsConfigurationSource() {
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration config = new CorsConfiguration();
				config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
				config.setAllowedMethods(Collections.singletonList("*"));
				config.setAllowCredentials(true);
				config.setAllowedHeaders(Collections.singletonList("*"));
				config.setMaxAge(3600L);
				return config;
			}
		})
//			.and().csrf().//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//		ignoringAntMatchers("/contact").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and().
//		authorizeRequests().
//		antMatchers("/myAccount").authenticated()
//		.antMatchers("/myBalance").authenticated()
//		.antMatchers("/myLoans").authenticated()
//		.antMatchers("/myCards").authenticated()
//		.antMatchers("/user").authenticated()
//		.antMatchers("/notices").permitAll()
//		.antMatchers("/contact").permitAll().and().httpBasic();
//		
//			//s authorizazia
//			.and().csrf().ignoringAntMatchers("/contact").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//			.and()
//			//seczia filtrov
//			.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
//			.addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
//			.addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
//			//seczia filtrov
//			.authorizeRequests()
//			.antMatchers("/myAccount").hasRole("USER")
//			.antMatchers("/myBalance").hasAnyRole("USER","ADMIN")
//			.antMatchers("/myLoans").hasRole("ROOT")
//			.antMatchers("/myCards").authenticated()
//			.antMatchers("/user").authenticated()
//			.antMatchers("/notices").permitAll()
//			.antMatchers("/contact").permitAll().and().httpBasic();
		
		
		//esli ispolzovat JWT
		.and().csrf()
		.disable()
		.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
		.addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
		.addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
		.addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
		.addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
		.authorizeRequests()
		.antMatchers("/myAccount").hasRole("USER")
		.antMatchers("/myBalance").hasAnyRole("USER","ADMIN")
		.antMatchers("/myLoans").hasRole("ROOT")
		.antMatchers("/myCards").authenticated()
		.antMatchers("/user").authenticated()
		.antMatchers("/notices").permitAll()
		.antMatchers("/contact").permitAll().and().httpBasic();
		
		
	}
	
	
	//zapusk v pam`ati, esli nujno neskolko polzovateley
//	  @Override 
//	  protected void configure(AuthenticationManagerBuilder auth) throws
//	  Exception {
//	  auth.inMemoryAuthentication()
//	  .withUser("admin").password("admin").authorities("admin")
//	  .and().withUser("user").password("user").authorities("read")
//	  .and().passwordEncoder(NoOpPasswordEncoder.getInstance()); }
	 
	
	
//	  @Override 
//	  protected void configure(AuthenticationManagerBuilder auth) throws Exception { 
//	  InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager(); 
//	  UserDetails user = User.withUsername("admin").password("admin").authorities("admin").build();
//	  UserDetails user1 = User.withUsername("user").password("user").authorities("read").build();
//	  userDetailsService.createUser(user); 
//	  userDetailsService.createUser(user1);
//	  auth.userDetailsService(userDetailsService); 
//	  }
	
	
	//v etom szinarii usery sozdayutsa i razmeshautsav DB
	  @Bean 
	  public UserDetailsService userDetailsService(DataSource dataSource) {
	  return new JdbcUserDetailsManager(dataSource); }
	 
	
	  //kodiruem paroli
		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
		
		//tak byvaet esli net shifrovaniya
//		@Bean
//		public PasswordEncoder passwordEncoder() {
//			return NoOpPasswordEncoder.getInstance();
//		}
}
