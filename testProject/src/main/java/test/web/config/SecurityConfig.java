package test.web.config;

import org.springframework.beans.factory.annotation.Autowired;

/*
 * authenticated()	인증된 사용자의 접근을 허용
 * fullyAuthenticated()	인증된 사용자의 접근을 허용, rememberMe 인증 제외
 * permitAll()	무조건 접근을 허용
 * denyAll()	무조건 접근을 허용하지 않음
 * anonymous()	익명사용자의 접근을 허용
 * rememberMe()	기억하기를 통해 인증된 사용자의 접근을 허용
 * access(String)	주어진 SpEL 표현식의 평가 결과가 true이면 접근을 허용
 * hasRole(String)	사용자가 주어진 역할이 있다면 접근을 허용
 * hasAuthority(String)	사용자가 주어진 권한이 있다면
 * hasAnyRole(String...)	사용자가 주어진 권한이 있다면 접근을 허용
 * hasAnyAuthority(String...)	사용자가 주어진 권한 중 어떤 것이라도 있다면 접근을 허용
 * hasIpAddress(String)	주어진 IP로부터 요청이 왔다면 접근을 허용
 * */


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import test.web.oauth.PrincipalOauth2UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

	@Autowired
	PrincipalOauth2UserService principalOauth2UserService;
	
	@Bean
	public static BCryptPasswordEncoder incoding_password() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
     	
		http.authorizeHttpRequests(a -> a.anyRequest().permitAll());
				
		
		
		
		
//				.requestMatchers("/manager/**").hasAnyAuthority("MANAGER","ADMIN")
//				.requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
	
		
		http.oauth2Login(login -> login
				.loginPage("/login")
				.userInfoEndpoint(endpoint ->endpoint
				.userService(principalOauth2UserService))
				.defaultSuccessUrl("/main2")
				.failureUrl("/login"));
		
		http.formLogin(login->login
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/main2")
				.failureUrl("/login")
		);
		
		
		http.logout(a -> a.logoutUrl("/logout")
				.logoutSuccessUrl("/main"));
		
		http.csrf((csrf)->csrf.disable());
		
        return http.build();
    }
}
