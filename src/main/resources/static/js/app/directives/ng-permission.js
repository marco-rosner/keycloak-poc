(function () {

    "use strict";

    /**
     * Remove a HTML element if not has authority.
     * @param keycloak The keycloak adapter to check the user authorities.
     */
    function Permission (keycloak) {
        return {
            restrict: "A",
            scope: {
            	role: "="
            },
            link: function (scope, element, attr) {
                if(!keycloak.hasRealmRole("ROLE_" + scope.role.trim())) {
                    element.remove();
                }
            }
        };
    };

    angular.module('keycloak-sec-app').directive('ngPermission', ['keycloak', Permission]);

})();
