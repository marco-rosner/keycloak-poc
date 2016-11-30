package com.web.keycloak.settings;

import java.util.List;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Class ApplicationSettings
 */
@Component
@ConfigurationProperties (prefix = "app")
public class Settings {

    private String keycloakMasterRealm;

    private String keycloakMasterRealmAdmin;

    private String keycloakMasterRealmAdminPassword;

    private String keycloakMasterRealmAdminClient;

    @Value ("${keycloak.auth-server-url}")
    private String keycloakUrl;

    private String keycloakFrontendClient;

    @Value ("${keycloak.resource}")
    private String keycloakBackendClient;

    private Integer keycloakPoolSize;
    
    public String getKeycloakBackendClient () {
        return keycloakBackendClient;
    }

    public void setKeycloakBackendClient (final String keycloakBackendClient) {
        this.keycloakBackendClient = keycloakBackendClient;
    }

    public String getKeycloakFrontendClient () {
        return keycloakFrontendClient;
    }

    public void setKeycloakFrontendClient (final String keycloakFrontendClient) {
        this.keycloakFrontendClient = keycloakFrontendClient;
    }

    public String getKeycloakUrl () {
        return keycloakUrl;
    }

    public void setKeycloakUrl (final String keycloakUrl) {
        this.keycloakUrl = keycloakUrl;
    }

    public String getKeycloakMasterRealmAdminClient () {
        return keycloakMasterRealmAdminClient;
    }

    public void setKeycloakMasterRealmAdminClient (final String keycloakMasterRealmAdminClient) {
        this.keycloakMasterRealmAdminClient = keycloakMasterRealmAdminClient;
    }

    public String getKeycloakMasterRealmAdminPassword () {
        return keycloakMasterRealmAdminPassword;
    }

    public void setKeycloakMasterRealmAdminPassword (
            final String keycloakMasterRealmAdminPassword) {
        this.keycloakMasterRealmAdminPassword = keycloakMasterRealmAdminPassword;
    }

    public String getKeycloakMasterRealmAdmin () {
        return keycloakMasterRealmAdmin;
    }

    public void setKeycloakMasterRealmAdmin (final String keycloakMasterRealmAdmin) {
        this.keycloakMasterRealmAdmin = keycloakMasterRealmAdmin;
    }

    public String getKeycloakMasterRealm () {
        return keycloakMasterRealm;
    }

    public void setKeycloakMasterRealm (final String keycloakMasterRealm) {
        this.keycloakMasterRealm = keycloakMasterRealm;
    }

    public Integer getKeycloakPoolSize () {
        return keycloakPoolSize;
    }

    public void setKeycloakPoolSize (final Integer keycloakPoolSize) {
        this.keycloakPoolSize = keycloakPoolSize;
    }

   

}
