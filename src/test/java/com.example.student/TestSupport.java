package com.example.student;

import java.util.Objects;

final class TestSupport {

    private TestSupport() {}

    static boolean runTestClass(String className, CheckedAction tests) {
        try {
            tests.run();
            System.out.println("Тест для " + className + " пройден.");
            return true;
        } catch (Throwable error) {
            System.out.println("Тест для " + className
                    + " не пройден. Причина: " + error.getMessage());
            return false;
        }
    }

    static void assertEquals(Object expected, Object actual, String message) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError(message + ". Ожидалось: " + expected
                    + ", получено: " + actual);
        }
    }

    static void assertDoubleEquals(double expected, double actual, String message) {
        if (Double.compare(expected, actual) != 0) {
            throw new AssertionError(message + ". Ожидалось: " + expected
                    + ", получено: " + actual);
        }
    }

    static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }


    @FunctionalInterface
    interface CheckedAction {
        void run() throws Exception;
    }
}
