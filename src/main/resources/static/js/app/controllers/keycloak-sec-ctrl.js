(function () {

    "use strict";

    function KeycloakSecController($scope, $timeout, keycloak, APIService){
        var self = this;

        self.keycloakUser = "";
        self.keycloakRealm = keycloak.realm;
        self.APIResult = "";
        self.allowGetContacts = "get-contacts";
        self.allowLogout = "logout";

        function prepareUserInfo(){
            keycloak.loadUserProfile().success(function(result){
                $timeout(function(){
                    self.keycloakUser = result.username;
                });
            }).error(function(error){
                console.error("Error: " + error)
            });
        }

        self.getContacts = function(){
            APIService.getContacts().then(function(result){
                self.APIResult = "Success on retrieve the contacts API";
            }, function(error){
                self.APIResult = "Error on retrieve the contacts API: " + error.status + " (" + error.data.message + ")";
            });
        };

        self.doLogout = function(){
            keycloak.logout();
        };

        function init(){
            prepareUserInfo();
        }

        init();
    };

    angular.module('keycloak-sec-app').controller('KeycloakSecController',['$scope', '$timeout', 'keycloak', 'APIService', KeycloakSecController]);

})();