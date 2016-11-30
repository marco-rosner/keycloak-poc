(function () {

    "use strict";

    /**
     * Service with api for contacts.
     */
    function APIService($http) {

        var self = this;

        var CONTACTS_URL_BASE = "/api/v1/contacts"

        /**
         *
         * @returns {*}
         */
        self.getContacts = function () {
            return $http.get(CONTACTS_URL_BASE);
        };
    }

    //Register angular components
    angular.module('keycloak-sec-app').service('APIService', ['$http', APIService]);
})();
