package edu.school21.numbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class NumberWorkerTest {

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 17, 211, 7919})
    void isPrimeForPrimes(int number) {
        NumberWorker numberWorker = new NumberWorker();
        assertTrue(numberWorker.isPrime(number));
    }
    @ParameterizedTest
    @ValueSource(ints = {4, 6, 9, 21, 42, 7918})
    void PrimeForNotPrimes(int number) {
        NumberWorker numberWorker = new NumberWorker();
        assertFalse(numberWorker.isPrime(number));
    }
    @ParameterizedTest
    @ValueSource(ints = {-100, -1, 0, 1, -3, -2})
    void sPrimeForIncorrectNumbers(int number) {
        NumberWorker numberWorker = new NumberWorker();
        assertThrows(NumberWorker.IllegalNumberException.class, () -> {
            numberWorker.isPrime(number);
        });
    }
    @ParameterizedTest
    @CsvFileSource(resources = {"/data.csv"}, delimiter = ',')
    void digitsSum(int number, int result) {
        NumberWorker numberWorker = new NumberWorker();
        assertEquals(numberWorker.digitsSum(number), result);
    }
}
