package com.apps.cli.command;

import com.apps.cli.service.ApiClient;
import com.apps.cli.service.SessionState;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

/**
 * History commands – mirrors HistoryPage.js from the React frontend.
 *
 * Examples:
 *   history
 *   history-by-op --operation compare
 *   op-count      --operation add
 */
@ShellComponent
@RequiredArgsConstructor
public class HistoryCommands {

    private final ApiClient apiClient;
    private final SessionState session;
    private final ObjectMapper objectMapper;

    @ShellMethod(value = "Show all operation history", key = "history")
    public String history() {
        if (!session.isLoggedIn()) return "🔒 Please login first.";
        try {
            Object result = apiClient.getHistory();
            return formatResult(result);
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    @ShellMethod(value = "Show history filtered by operation type  (compare | convert | add | subtract | divide)", key = "history-by-op")
    public String historyByOp(
            @ShellOption(value = "--operation", help = "Operation type: compare, convert, add, subtract, divide") String operation) {
        if (!session.isLoggedIn()) return "🔒 Please login first.";
        try {
            Object result = apiClient.getHistoryByOperation(operation.toLowerCase());
            return formatResult(result);
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    @ShellMethod(value = "Show count of successful operations by type", key = "op-count")
    public String opCount(
            @ShellOption(value = "--operation", help = "Operation type: compare, convert, add, subtract, divide") String operation) {
        if (!session.isLoggedIn()) return "🔒 Please login first.";
        try {
            Object result = apiClient.getOperationCount(operation.toLowerCase());
            return "✅ Count for [" + operation + "]: " + result;
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    private String formatResult(Object result) {
        try {
            return "✅ Result:\n" + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        } catch (Exception e) {
            return "✅ Result: " + result;
        }
    }
}
