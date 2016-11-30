package com.web.keycloak.security.tenant;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.web.keycloak.security.exceptions.RealmNotSelectedException;
import com.web.keycloak.security.helpers.KeycloakHelper;
import com.web.keycloak.settings.Settings;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.spi.HttpFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class MTKeycloakConfigResolver
 */
@Component
public class MTKeycloakConfigResolver implements KeycloakConfigResolver {

    public static final String PREFIX = "realm";

    private final Map<String, KeycloakDeployment> cache = new ConcurrentHashMap<String, KeycloakDeployment> ();

    private KeycloakDeployment keycloakDeployment;

    @Autowired
    private Settings settings;

    @Autowired
    private KeycloakHelper keycloakHelper;

    public KeycloakDeployment resolve (final HttpFacade.Request request) {

        final String client = settings.getKeycloakBackendClient();
        final String realm = getRealmInformation (request);

        if (realm == null) {
            throw new RealmNotSelectedException(
                    "You must select the 'realm'. Use the query param realm (e.g., server:port/realm/<REALM-NAME>)");
        }

        keycloakDeployment = cache.get (realm);

        if (keycloakDeployment == null) {

            /*
             * Configuration from Keycloak REST API.
             */
            final String conf = keycloakHelper.getJsonRealmClientConfiguration (realm, client);
            final InputStream configInputStream = new ByteArrayInputStream (conf.getBytes ());
            keycloakDeployment = KeycloakDeploymentBuilder.build (configInputStream);

            cache.put (realm, keycloakDeployment);
        }

        return keycloakDeployment;
    }


    private String getRealmInformation (final HttpFacade.Request request) {

        // used for first request (non authenticated)
        final String uri = request.getURI ();
        final List<String> uriParts = Arrays.asList (uri.split ("/"));
        final int prefixIndex = uriParts.indexOf (PREFIX);
        String realm = null;

        if (prefixIndex != -1) {
            if (uriParts.size () > prefixIndex + 1) {
                realm = uriParts.get (prefixIndex + 1);
            }

            if (uriParts.get (prefixIndex + 1).contains ("?")) {
                realm = realm.split ("\\?")[0];
            }

            if (realm != null) {
                realm = realm.split ("#")[0];
            }
        }

        // used with user authenticated
        if (realm == null) {
            realm = request.getHeader ("realm");
        }

        return realm;
    }
    
}
