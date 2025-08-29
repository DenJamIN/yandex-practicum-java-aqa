package org.example.app.exceptions;

/**
 * Исключение для проверки валидации перевозки хрупких грузов на расстояние больше предельного
 */
public class FragileRangeException extends RuntimeException {
    /**
     * @param maxRange максимальное расстояние, на которое можно провозить хрупких груз
     */
    public FragileRangeException(Double maxRange) {
        super(String.format("Ошибка валидации: хрупкие грузы нельзя возить на расстояние более %s км", maxRange));
    }
}
