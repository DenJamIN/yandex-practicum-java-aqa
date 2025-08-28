package org.example.app;

import org.example.app.enums.DimensionType;
import org.example.app.enums.WorkloadStatus;

/**
 * Статический класс для расчёта стоимости доставки груза
 */
public class ShippingCalculator {

    /**
     * Минимальная стоимость доставки
     */
    private static final Double MIN_SHIPPING_AMOUNT = 400.0;

    //TODO поменять Exception на подходящие

    /**
     * Расчёт стоимости доставки. Минимальная стоимость доставки: {@link ShippingCalculator#MIN_SHIPPING_AMOUNT}
     *
     * @param range          расстояния до пункта назначения указывается в километрах
     * @param dimensionType  тип габаритов груза
     * @param isFragile      хрупкость груза. Если груз хрупкий, тогда {@code true}
     * @param workloadStatus агруженности службы доставки
     * @return итоговая стоимость доставки
     * @throws Exception если значение поля {@code range} отрицательное
     * @throws Exception если хрупкий груз провозится на недопустимое расстояние
     */
    public static Double calculateAmount(Double range, DimensionType dimensionType, Boolean isFragile, WorkloadStatus workloadStatus) throws Exception {
        // Требуется уточнение: валидным ли является значение 0. Так как адрес доставки может быть такой же
        if (range < 0) {
            throw new Exception("Ошибка валидации: поле [range] должно быть положительным");
        }

        if (isFragile && range > 30) {
            throw new Exception("Ошибка: хрупкие грузы нельзя возить на расстояние более 30 км");
        }

        double amount = 0;

        amount += getSumByRange(range);

        amount += switch (dimensionType) {
            case BIG -> 200;
            case SMALL -> 100;
        };

        if (isFragile) {
            amount += 300;
        }

        amount *= workloadStatus.getRatio();

        return amount > MIN_SHIPPING_AMOUNT ? amount : MIN_SHIPPING_AMOUNT;
    }

    /**
     * Расчёт доставки по расстоянию
     *
     * @param range расстояния до пункта назначения указывается в километрах
     * @return стоимость надбавки к стоимости доставки по расстоянию
     */
    private static Double getSumByRange(Double range) {
        /*Требуется уточнение, фрагмент задачи:
        - более 30 км: +300 рублей к доставке;
        - до 30 км: +200 рублей к доставке;

        Неоднозначно куда входить значение равное 30
         */
        if (range > 30) {
            return 300.0;
        } else if (range > 10) {
            return 200.0;
        } else if (range > 2) {
            return 100.0;
        } else {
            return 50.0;
        }
    }
}