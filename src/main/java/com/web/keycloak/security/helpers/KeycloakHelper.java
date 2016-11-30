package com.web.keycloak.security.helpers;

import com.web.keycloak.settings.Settings;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.ClientsResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * The keycloak helper class to manage the Keycloak Java API.
 */
public final class KeycloakHelper {

    private static KeycloakHelper instance = new KeycloakHelper();

    @Autowired
    private Settings settings;

    private Keycloak keycloak = null;

    public static KeycloakHelper getInstance () {
        return instance;
    }

    @PostConstruct
    public void start () {
        keycloak = getKeycloak ();
    }

    private KeycloakHelper(){}


    /**
     * Initialize the Keycloak
     *
     * @return keycloak instance
     */
    public Keycloak getKeycloak () {
        return KeycloakBuilder.builder ().serverUrl (settings.getKeycloakUrl ())
                .realm (settings.getKeycloakMasterRealm ())
                .clientId (settings.getKeycloakMasterRealmAdminClient ())
                .resteasyClient (new ResteasyClientBuilder().connectionPoolSize(settings.getKeycloakPoolSize()).build())
                .username (settings.getKeycloakMasterRealmAdmin ())
                .password (settings.getKeycloakMasterRealmAdminPassword ())
                .build ();
    }


    /**
     *
     * Return the JSON configuration associated with realm and client.
     *
     * Each security realm consists of a set of configured security providers, users, groups,
     * security roles, and security policies Each security client consists of a representation of
     * applications that interacts with security system.
     *
     * The returned configuration consists the JSON with all informations to be used by each
     * application.
     *
     * @param realm the Realm that the client belongs. This parameter cannot be null.
     * @param client the Client id that provided the JSON configuration. This parameter cannot be
     *            null.
     * @return Client information in JSON format to be used by each application.
     */
    public String getJsonRealmClientConfiguration (final String realm, final String client) {

        String jsonClientConfiguration = null;
        final ClientsResource clientsResource = keycloak.realm (realm).clients ();

        final List<ClientRepresentation> clientsRepresentation = clientsResource
                .findByClientId (client);

        if (clientsRepresentation != null && !clientsRepresentation.isEmpty ()) {

            final ClientRepresentation cr = clientsRepresentation.get (0);
            final ClientResource clientResource = clientsResource.get (cr.getId ());
            jsonClientConfiguration = clientResource
                    .getInstallationProvider ("keycloak-oidc-keycloak-json");
        }

        return jsonClientConfiguration;
    }

}
