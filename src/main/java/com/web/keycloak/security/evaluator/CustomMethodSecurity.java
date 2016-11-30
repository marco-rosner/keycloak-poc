package com.web.keycloak.security.evaluator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * Class CustomMethodSecurity
 */
@Configuration
@EnableGlobalMethodSecurity (prePostEnabled = true)
public class CustomMethodSecurity extends GlobalMethodSecurityConfiguration {

    @Autowired
    private SecurityEvaluator securityEvaluator;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler () {
        final DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler ();
        handler.setPermissionEvaluator (securityEvaluator);
        return handler;
    }

}
