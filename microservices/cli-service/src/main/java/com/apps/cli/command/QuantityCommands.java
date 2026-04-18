package com.apps.cli.command;

import com.apps.cli.service.ApiClient;
import com.apps.cli.service.SessionState;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

/**
 * Quantity operation commands – mirrors CalculatorPage.js from the React frontend.
 *
 * Supported measurement types : LENGTH | WEIGHT | TEMPERATURE | VOLUME
 *
 * LENGTH units      : FEET, INCHES, YARDS, CENTIMETERS
 * WEIGHT units      : KILOGRAM, GRAM, POUND
 * TEMPERATURE units : CELSIUS, FAHRENHEIT
 * VOLUME units      : LITRE, MILLILITRE, GALLON
 *
 * Examples:
 *   compare --v1 100 --u1 CENTIMETERS --t1 LENGTH --v2 1 --u2 YARDS --t2 LENGTH
 *   convert --value 100 --from FAHRENHEIT --type TEMPERATURE --to CELSIUS
 *   add      --v1 2 --u1 KILOGRAM --t1 WEIGHT --v2 500 --u2 GRAM --t2 WEIGHT
 *   subtract --v1 5 --u1 LITRE    --t1 VOLUME --v2 2   --u2 LITRE --t2 VOLUME
 *   divide   --v1 9 --u1 FEET     --t1 LENGTH --v2 3   --u2 FEET --t2 LENGTH
 */
@ShellComponent
@RequiredArgsConstructor
public class QuantityCommands {

    private final ApiClient apiClient;
    private final SessionState session;
    private final ObjectMapper objectMapper;

    // ─── Compare ──────────────────────────────────────────────────────────

    @ShellMethod(value = "Compare two quantities  (e.g. compare --v1 100 --u1 CENTIMETER --t1 LENGTH --v2 1 --u2 METER --t2 LENGTH)", key = "compare")
    public String compare(
            @ShellOption(value = "--v1", help = "Value of first quantity")  double v1,
            @ShellOption(value = "--u1", help = "Unit  of first quantity")  String u1,
            @ShellOption(value = "--t1", help = "Type  of first quantity")  String t1,
            @ShellOption(value = "--v2", help = "Value of second quantity") double v2,
            @ShellOption(value = "--u2", help = "Unit  of second quantity") String u2,
            @ShellOption(value = "--t2", help = "Type  of second quantity") String t2) {
        if (!requireLogin()) return loginPrompt();
        try {
            Object result = apiClient.compare(ApiClient.buildPayload(v1, u1.toUpperCase(), t1.toUpperCase(), v2, u2.toUpperCase(), t2.toUpperCase()));
            return formatResult(result);
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    // ─── Convert ──────────────────────────────────────────────────────────

    @ShellMethod(value = "Convert a quantity to another unit  (e.g. convert --value 100 --from FAHRENHEIT --type TEMPERATURE --to CELSIUS)", key = "convert")
    public String convert(
            @ShellOption(value = "--value", help = "Value to convert")     double value,
            @ShellOption(value = "--from",  help = "Source unit")          String from,
            @ShellOption(value = "--type",  help = "Measurement type")     String type,
            @ShellOption(value = "--to",    help = "Target unit")          String to) {
        if (!requireLogin()) return loginPrompt();
        try {
            Object result = apiClient.convert(
                    ApiClient.buildPayload(value, from.toUpperCase(), type.toUpperCase(), 0, to.toUpperCase(), type.toUpperCase()));
            return formatResult(result);
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    // ─── Add ──────────────────────────────────────────────────────────────

    @ShellMethod(value = "Add two quantities  (e.g. add --v1 2 --u1 KILOGRAM --t1 WEIGHT --v2 500 --u2 GRAM --t2 WEIGHT)", key = "add")
    public String add(
            @ShellOption(value = "--v1", help = "Value of first quantity")  double v1,
            @ShellOption(value = "--u1", help = "Unit  of first quantity")  String u1,
            @ShellOption(value = "--t1", help = "Type  of first quantity")  String t1,
            @ShellOption(value = "--v2", help = "Value of second quantity") double v2,
            @ShellOption(value = "--u2", help = "Unit  of second quantity") String u2,
            @ShellOption(value = "--t2", help = "Type  of second quantity") String t2) {
        if (!requireLogin()) return loginPrompt();
        try {
            Object result = apiClient.add(ApiClient.buildPayload(v1, u1.toUpperCase(), t1.toUpperCase(), v2, u2.toUpperCase(), t2.toUpperCase()));
            return formatResult(result);
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    // ─── Subtract ─────────────────────────────────────────────────────────

    @ShellMethod(value = "Subtract second quantity from first  (e.g. subtract --v1 5 --u1 LITER --t1 VOLUME --v2 2 --u2 LITER --t2 VOLUME)", key = "subtract")
    public String subtract(
            @ShellOption(value = "--v1", help = "Value of first quantity")  double v1,
            @ShellOption(value = "--u1", help = "Unit  of first quantity")  String u1,
            @ShellOption(value = "--t1", help = "Type  of first quantity")  String t1,
            @ShellOption(value = "--v2", help = "Value of second quantity") double v2,
            @ShellOption(value = "--u2", help = "Unit  of second quantity") String u2,
            @ShellOption(value = "--t2", help = "Type  of second quantity") String t2) {
        if (!requireLogin()) return loginPrompt();
        try {
            Object result = apiClient.subtract(ApiClient.buildPayload(v1, u1.toUpperCase(), t1.toUpperCase(), v2, u2.toUpperCase(), t2.toUpperCase()));
            return formatResult(result);
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    // ─── Divide ───────────────────────────────────────────────────────────

    @ShellMethod(value = "Divide first quantity by second  (e.g. divide --v1 9 --u1 METER --t1 LENGTH --v2 3 --u2 METER --t2 LENGTH)", key = "divide")
    public String divide(
            @ShellOption(value = "--v1", help = "Value of first quantity")  double v1,
            @ShellOption(value = "--u1", help = "Unit  of first quantity")  String u1,
            @ShellOption(value = "--t1", help = "Type  of first quantity")  String t1,
            @ShellOption(value = "--v2", help = "Value of second quantity") double v2,
            @ShellOption(value = "--u2", help = "Unit  of second quantity") String u2,
            @ShellOption(value = "--t2", help = "Type  of second quantity") String t2) {
        if (!requireLogin()) return loginPrompt();
        try {
            Object result = apiClient.divide(ApiClient.buildPayload(v1, u1.toUpperCase(), t1.toUpperCase(), v2, u2.toUpperCase(), t2.toUpperCase()));
            return formatResult(result);
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    // ─── Helpers ──────────────────────────────────────────────────────────

    private boolean requireLogin() {
        return session.isLoggedIn();
    }

    private String loginPrompt() {
        return "🔒 You must be logged in. Use: login --username <u> --password <p>";
    }

    private String formatResult(Object result) {
        try {
            return "✅ Result:\n" + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        } catch (Exception e) {
            return "✅ Result: " + result;
        }
    }
}
