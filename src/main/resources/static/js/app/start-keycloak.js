$(document).ready(function(){

    var keycloak = undefined;

    /**
     * Initialize the keycloak.
     */
    function initKeycloakConfiguration(){

        var realm = window.location.pathname.split('/').pop();
        if(!realm) { realm = 'default' };

        var keycloakConfiguration = {
            "url": "http://localhost:8080/auth/",
            "realm": realm,
            "clientId": "frontend"
        };

        keycloak = Keycloak(keycloakConfiguration);
    };

    initKeycloakConfiguration();

    keycloak.init({
        onLoad: 'login-required'
    }).success(function(){
        // Create an angular constant using the keycloak adapter.
        angular.module('keycloak-sec-app').constant('keycloak', keycloak);
        // Start manually the angular when the keycloak is authenticated.
        angular.bootstrap(document, ['keycloak-sec-app']);
    }).error(function (error) {
        console.error(error);
    });
});
