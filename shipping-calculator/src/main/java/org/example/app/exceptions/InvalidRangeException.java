package org.example.app.exceptions;

/**
 * Исключение для проверки валидации поля [range]
 */
public class InvalidRangeException extends RuntimeException {
    public InvalidRangeException() {
        super("Ошибка валидации: поле [range] должно быть положительным");
    }
}
