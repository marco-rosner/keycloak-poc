package com.web.keycloak.controllers;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping (path = "/api/v1/contacts", produces = { MediaType.APPLICATION_JSON_VALUE })
public class ContactController {

    @RequestMapping (method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated() and hasPermission(authentication, 'get-contacts')")
    public List<String> getPreferredContacts () {
        List<String> contacts = new ArrayList<String> ();
        contacts.add ("Contact 1");
        contacts.add ("Contact 2");
        contacts.add ("Contact 3");
        contacts.add ("Contact 4");
        contacts.add ("Contact 5");

        return contacts;
    }

}
