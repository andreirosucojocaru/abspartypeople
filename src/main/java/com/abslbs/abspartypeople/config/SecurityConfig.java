package com.abslbs.abspartypeople.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.abslbs.abspartypeople.service.AuthenticationAccessDeniedHandler;
import com.abslbs.abspartypeople.service.AuthenticationAccessSuccessHandler;
import com.abslbs.abspartypeople.service.AuthenticationUserDetailsService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationAccessSuccessHandler accessSuccessHandler;
	
	@Autowired
	private AuthenticationAccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private AuthenticationUserDetailsService authenticationUserDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/", "/add", "/login", "/js/**", "/css/**", "/img/**").permitAll()
				.antMatchers("/user/").hasRole("Admin")
				.antMatchers("/post/").authenticated()
				.anyRequest().authenticated()
			.and()
				.csrf().disable().formLogin().successHandler(accessSuccessHandler)
			.and()
				.formLogin()
					.loginPage("/login")
					.permitAll()
			.and()
				.logout()
					.invalidateHttpSession(true)
					.clearAuthentication(true)
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.logoutSuccessUrl("/login?logout")
					.permitAll()
			.and()
				.exceptionHandling()
					.accessDeniedHandler(accessDeniedHandler);
		http
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				.invalidSessionUrl("/invalidSession")
				.sessionFixation().migrateSession();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder authentication) throws Exception {
		authentication
			.userDetailsService(authenticationUserDetailsService)
			.passwordEncoder(bCryptPasswordEncoder);
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	    return bCryptPasswordEncoder;
	}
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}

}
