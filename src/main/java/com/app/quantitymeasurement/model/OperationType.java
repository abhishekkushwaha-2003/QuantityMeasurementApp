package com.app.quantitymeasurement.model;

public enum OperationType {
    ADD("Addition"),
    SUBTRACT("Subtraction"),
    MULTIPLY("Multiplication"),
    DIVIDE("Division"),
    COMPARE("Comparison"),
    CONVERT("Conversion");

    private final String displayName;

    // Constructor to set display name
    OperationType(String displayName) {
        this.displayName = displayName;
    }

    // Method to get the display name
    public String getDisplayName() {
        return displayName;
    }

    // Method to convert string to enum safely
    public static OperationType fromString(String text) {
        for (OperationType b : OperationType.values()) {
            if (b.name().equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}