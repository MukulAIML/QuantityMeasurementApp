package com.apps.cli.command;

import com.apps.cli.dto.AuthResponseDTO;
import com.apps.cli.service.ApiClient;
import com.apps.cli.service.SessionState;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

/**
 * Auth commands – mirrors AuthPage.js from the React frontend.
 *
 * Usage:
 *   register --username alice --password secret
 *   login --username alice --password secret
 *   logout
 *   whoami
 */
@ShellComponent
@RequiredArgsConstructor
public class AuthCommands {

    private final ApiClient apiClient;
    private final SessionState session;

    @ShellMethod(value = "Register a new account", key = "register")
    public String register(
            @ShellOption(help = "Username") String username,
            @ShellOption(help = "Password") String password) {
        try {
            AuthResponseDTO resp = apiClient.register(username, password);
            return "✅ Registered successfully. You can now login with: login --username " + username + " --password <password>";
        } catch (Exception e) {
            return "❌ Registration failed: " + e.getMessage();
        }
    }

    @ShellMethod(value = "Login to your account", key = "login")
    public String login(
            @ShellOption(help = "Username") String username,
            @ShellOption(help = "Password") String password) {
        try {
            AuthResponseDTO resp = apiClient.login(username, password);
            return "✅ Logged in as: " + username + "\n   Token stored in session. You can now use quantity commands.";
        } catch (Exception e) {
            return "❌ Login failed: " + e.getMessage();
        }
    }

    @ShellMethod(value = "Logout and clear session", key = "logout")
    public String logout() {
        if (!session.isLoggedIn()) {
            return "ℹ️  You are not logged in.";
        }
        String user = session.getUsername();
        apiClient.logout();
        return "👋 Logged out from: " + user;
    }

    @ShellMethod(value = "Show current logged-in user", key = "whoami")
    public String whoami() {
        if (!session.isLoggedIn()) {
            return "ℹ️  Not logged in. Use: login --username <u> --password <p>";
        }
        return "👤 Logged in as: " + session.getUsername();
    }
}
