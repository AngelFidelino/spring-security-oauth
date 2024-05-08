package com.aflr.oauthclient.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProtectedController {

    @GetMapping("/")
    public String access(OAuth2AuthenticationToken token) {
        return "protected.html";
    }
}
