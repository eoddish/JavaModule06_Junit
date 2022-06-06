package edu.school21.numbers;

public class NumberWorker {

    public class IllegalNumberException extends RuntimeException {
        IllegalNumberException(String errorMessage) {
            super(errorMessage);
        }
    }

    public boolean isPrime(int number) {
        if (number <= 1) {
            throw new IllegalNumberException("Error: Illegal Number!");
        }
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public int digitsSum(int number) {
        int result = 0;
        number = Math.abs(number);
        while (number > 0) {
            result += number % 10;
            number = number / 10;
        }
        return result;
    }
}
