package com.apps.cli.service;

import org.springframework.stereotype.Component;

/**
 * Holds the current session state in memory.
 * Equivalent of localStorage.token in the React frontend.
 */
@Component
public class SessionState {

    private String token;
    private String username;

    public boolean isLoggedIn() {
        return token != null && !token.isBlank();
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public void setSession(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public void clearSession() {
        this.token = null;
        this.username = null;
    }

    /** Returns "Bearer <token>" ready for Authorization header */
    public String bearerToken() {
        return "Bearer " + token;
    }
}
