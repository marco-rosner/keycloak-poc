package com.web.keycloak.security;

import com.web.keycloak.security.entrypoints.HttpAuthenticationEntryPoint;
import com.web.keycloak.security.helpers.KeycloakHelper;
import com.web.keycloak.security.tenant.MTKeycloakConfigResolver;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


/**
 * This class applies the security configuration setup for current web application.
 */
@Configuration
@EnableWebSecurity
@ComponentScan (basePackageClasses = KeycloakSecurityComponents.class)
public class WebSecurityConfigurer extends KeycloakWebSecurityConfigurerAdapter {

    private static final String API_MATCHER = "/api/**";

    @Autowired
    private KeycloakClientRequestFactory keycloakClientRequestFactory;

    @Autowired
    private MTKeycloakConfigResolver mtKeycloakConfigResolver;

    @Override
    public void configure(final WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers("/js/**", "/css/**", "/images/**", "/favicon.ico", "index.html");
    }

    @Bean
    @Scope (ConfigurableBeanFactory.SCOPE_SINGLETON)
    public KeycloakHelper keycloakHelper () {
        final KeycloakHelper keycloakHelper = KeycloakHelper.getInstance ();
        return keycloakHelper;
    }


    @Bean
    public InternalResourceViewResolver viewResolver() {
        final InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/");
        resolver.setSuffix(".html");
        resolver.setAlwaysInclude (true);
        return resolver;
    }


    @Bean
    @Scope (ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public KeycloakRestTemplate keycloakRestTemplate () {
        return new KeycloakRestTemplate (keycloakClientRequestFactory);
    }

    @Autowired
    public void configureGlobal (final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider (keycloakAuthenticationProvider ());
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy () {
        return new NullAuthenticatedSessionStrategy();
    }


    @Bean
    public FilterRegistrationBean keycloakAuthenticationProcessingFilterRegistrationBean (
            final KeycloakAuthenticationProcessingFilter filter) {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean (filter);
        registrationBean.setEnabled (false);
        return registrationBean;
    }


    @Bean
    public KeycloakConfigResolver keycloakConfigResolver(){
        return mtKeycloakConfigResolver;
    }

    @Bean
    public FilterRegistrationBean keycloakPreAuthActionsFilterRegistrationBean (
            final KeycloakPreAuthActionsFilter filter) {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean (filter);
        registrationBean.setEnabled (false);
        return registrationBean;
    }


    @Override
    protected void configure (final HttpSecurity http) throws Exception {
        super.configure(http);

        http.csrf().disable();

        http.authorizeRequests().antMatchers (
                "/js/**",
                "/css/**",
                "/images/**",
                "/favicon.ico",
                "index.html").permitAll();

        http.authorizeRequests ().antMatchers(API_MATCHER).authenticated ();

        // Sets the default authentication exception handler
        http.exceptionHandling ().authenticationEntryPoint (new HttpAuthenticationEntryPoint());
    }	

}
