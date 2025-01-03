package org.example.utils;

import io.qameta.allure.Attachment;

public class AllureAssert {

    @Attachment(value = "Assertion Checkpoint: {checkpointName}", type = "text/plain")
    public static String attachAssertion(String checkpointName, String details) {
        return details;
    }

    public static void assertEquals(String actual, String expected, String checkpointName) {
        if (!actual.equals(expected)) {
            attachAssertion(checkpointName, "Expected: " + expected + "\nActual: " + actual);
            throw new AssertionError("Assertion failed: " + checkpointName);
        }
        attachAssertion(checkpointName, "Assertion passed!\nExpected: " + expected + "\nActual: " + actual);
    }

    public static void assertTrue(boolean condition, String checkpointName) {
        if (!condition) {
            attachAssertion(checkpointName, "Assertion failed! Condition is false.");
            throw new AssertionError("Assertion failed: " + checkpointName);
        }
        attachAssertion(checkpointName, "Assertion passed! Condition is true.");
    }
}