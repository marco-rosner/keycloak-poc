(function () {

    "use strict";

    /**
     * Creating a Http interceptor to manage the http states.
     */
    angular.module('keycloak-sec-app').factory('httpInterceptor',['$q', 'keycloak',
        function ($q, keycloak) {
            return {
                request: function (config) {
                    return $q(function(resolve, reject) {
                        keycloak.updateToken(5)
                            .success(function(refreshed) {
                                resolve(keycloak.token);
                            }).error(function() {
                            reject('error');
                        });
                    }).then(
                        function(result){
                            //multi-tenant feature
                            config.headers['realm'] = keycloak.realm;
                            // Keycloak token
                            config.headers['Authorization'] = 'Bearer ' + result;
                            return config || $q.when(config);
                        },
                        function(error){
                            keycloak.logout();
                        });
                },
                response: function (response) {
                    return response || $q.when(response);
                },
                responseError: function (response) {
                    return $q.reject(response);
                }
            };
        }
    ]).config(['$httpProvider', function ($httpProvider) {
        $httpProvider.interceptors.push('httpInterceptor');
    }]);

})();