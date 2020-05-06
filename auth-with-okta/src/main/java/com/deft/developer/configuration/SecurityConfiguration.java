package com.deft.developer.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SimpleSavedRequest;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Created by sgolitsyn on 4/17/20
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .oauth2Login().and()
                .csrf()
                /* CookieCsrfTokenRepository.withHttpOnlyFalse
                 * means that the XSRF-TOKEN cookie won’t be marked HTTP-only,
                 * so React can read it and send it back when it tries to manipulate data.
                 */
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .authorizeRequests()
                .antMatchers("/**/*.{js,html,css}").permitAll()
                .antMatchers("/", "/api/user").permitAll() // define what URLs are allowed for anonymous users
                .antMatchers("/healthcheck**").permitAll() // define what URLs are allowed for anonymous users
                .antMatchers(HttpMethod.POST, "/api/users**").permitAll() // define what URLs are allowed for anonymous users
                .anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/users");
    }

    /**
     * The RequestCache bean overrides the default request cache.
     * It saves the referrer header (misspelled referer in real life),
     * so Spring Security can redirect back to it after authentication.
     * The referrer-based request cache comes in handy
     * when you’re developing React on http://localhost:3000
     * and want to be redirected back there after logging in.
     *
     * @return
     */
    @Bean
    @Profile("dev")
    public RequestCache refererRequestCache() {
        return new HttpSessionRequestCache() {
            @Override
            public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
                String referrer = request.getHeader("referer");
                if (referrer != null) {
                    request.getSession().setAttribute("SPRING_SECURITY_SAVED_REQUEST", new SimpleSavedRequest(referrer));
                }
            }
        };
    }
}
