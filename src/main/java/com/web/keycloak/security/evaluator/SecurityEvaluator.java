package com.web.keycloak.security.evaluator;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * Class SecurityEvaluator
 */
@Component
public class SecurityEvaluator implements PermissionEvaluator {
	
	private static final String PREFIX_SECURITY = "ROLE_";

    private static final String PERMISSIONS_SEPARATOR = ",";

    @Override
    public boolean hasPermission (final Authentication authentication,
            final Object targetDomainObject, final Object permission) {

        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)
                && authentication.getAuthorities () != null
                && !authentication.getAuthorities ().isEmpty ()) {

            // Get roles granted to the user
            final List<GrantedAuthority> rolesList = (List<GrantedAuthority>)authentication.getAuthorities ();

            final List<String> permissions = Arrays.asList(permission.toString ().split (PERMISSIONS_SEPARATOR));
            permissions.replaceAll(x -> x.trim());

            List<String> myPermissions = null;
            
            myPermissions = rolesList.stream ().map (GrantedAuthority::getAuthority).collect (Collectors.toList ());
            
            myPermissions.replaceAll (x -> x.startsWith (PREFIX_SECURITY) ? x.substring (5) : x);

            return !Collections.disjoint (myPermissions, permissions);

        }
        return false;
    }
    
    @Override
    public boolean hasPermission (final Authentication authentication, final Serializable targetId,
            final String targetType, final Object permission) {
        return true;
    }
    
}
