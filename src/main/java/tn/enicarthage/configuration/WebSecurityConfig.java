package tn.enicarthage.configuration;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import tn.enicarthage.repositories.EtudiantRepository;
import tn.enicarthage.security.CustomerDetailsService;
import tn.enicarthage.security.JwtAuthenticationEntryPoint;
import tn.enicarthage.security.JwtAuthenticationFilter;
import tn.enicarthage.security.JwtTokenUtil;


@EnableWebSecurity
@Configuration

public class WebSecurityConfig {
	@Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
@Autowired 
private JwtTokenUtil jwt;
    @Autowired
    private CustomerDetailsService jwtUser;
    @Autowired
    private  EtudiantRepository userrepo;
	@Bean
	   public PasswordEncoder passwordEncoder() {
	      return new BCryptPasswordEncoder();
	   }
	@Bean 
	public UserDetailsService userDetailsService() {
		return username->userrepo.findByidutilisateur(Integer.parseInt(username));
	}
	@Bean
	  public AuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService());
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	  }
	 

 @Bean
 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	 http.authorizeHttpRequests().requestMatchers(HttpMethod.POST,"/{id}/{reference}/buypremiumbook").hasAuthority("PREMIUM");
 // http.authorizeHttpRequests().requestMatchers("/admini/**").hasAuthority("USER");
  //http.authorizeHttpRequests().requestMatchers("/rolessss").hasRole("USER");
  //http.authorizeHttpRequests().requestMatchers("/rere").hasRole("ADMIN");
 	  http.csrf().disable().authorizeHttpRequests()
       .requestMatchers("/registeretudiant","/loginetudiant","/**").permitAll()
       .anyRequest().authenticated()
       .and()
       .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
       .and()
       .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
       http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    

     return http.build();
 }
 
 

 @Bean
 public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
          throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
  }


 @Bean
 public JwtAuthenticationFilter jwtAuthenticationFilter() {
     return new JwtAuthenticationFilter();
 }
}