package com.example.personalLib.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // CSRF handled by Vaadin
                    .exceptionHandling()
                    .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                .and()
                    .logout()
                    .logoutSuccessUrl("/login/loggedout")
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/registration", "/VAADIN/**"
                            , "/login", "/login**", "/login/**", "/book/**", "/frontend/**", "/find/**", "/review/**"
                            , "/header", "/tab",
                            "/frontend/**", "/main", "/search", "/greeting").permitAll()
                    //.requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
                    // deny other URLs until authenticated
                    .anyRequest().fullyAuthenticated();
        http.headers().frameOptions().sameOrigin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .usersByUsernameQuery("select username, password, active from Users where username=?")
                .authoritiesByUsernameQuery("select u.username, ur.role from Users u inner join user_role ur on u.user_id = ur.user_id where u.username=?");
    }

    /*@Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }*/

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }
}