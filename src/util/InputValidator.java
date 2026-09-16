package util;

public final class InputValidator {
    private InputValidator() {}

    public static String required(String value, String field) {
        if (value == null || value.trim().isEmpty())
            throw new IllegalArgumentException(field + " cannot be empty.");
        return value.trim();
    }

    public static boolean isValidStatus(String status) {
        return status.equalsIgnoreCase("OPEN")
                || status.equalsIgnoreCase("UNDER_INVESTIGATION")
                || status.equalsIgnoreCase("CLOSED");
    }
}
