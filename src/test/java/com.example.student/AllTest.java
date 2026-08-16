package com.example.student;

public final class AllTest {

    private AllTest() {}

    public static void main(final String[] args) {
        boolean allPassed = true;

        allPassed &= StudentTest.runAll();
        allPassed &= SortTest.runAll();
        allPassed &= SelectiveBubbleSortTest.runAll();

        if (!allPassed) {
            throw new AssertionError("Один или несколько тестовых классов завершились с ошибкой.");
        }
        System.out.println("Все тесты проекта пройдены успешно.");
    }
}
