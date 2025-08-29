package org.example.app;

import org.example.app.enums.DimensionType;
import org.example.app.enums.WorkloadStatus;
import org.example.app.exceptions.FragileRangeException;
import org.example.app.exceptions.InvalidRangeException;

/**
 * Статический класс для расчёта стоимости доставки груза
 */
public class ShippingCalculator {

    /**
     * Минимальная стоимость доставки
     */
    private static final Double MIN_SHIPPING_AMOUNT = 400.0;
    /**
     * Максимальная длина для доставки хрупкого груза
     */
    private static final Double MAX_RANGE_FOR_IS_FRAGILE = 30.0;

    /**
     * Расчёт стоимости доставки. Минимальная стоимость доставки: {@link ShippingCalculator#MIN_SHIPPING_AMOUNT}
     *
     * @param range          расстояния до пункта назначения указывается в километрах
     * @param dimensionType  тип габаритов груза
     * @param isFragile      хрупкость груза. Если груз хрупкий, тогда {@code true}
     * @param workloadStatus статус загруженности службы доставки
     * @return итоговая стоимость доставки
     * @throws InvalidRangeException если значение поля {@code range} отрицательное
     * @throws FragileRangeException если хрупкий груз провозится на недопустимое расстояние
     */
    public static Double calculateAmount(Double range, DimensionType dimensionType, Boolean isFragile, WorkloadStatus workloadStatus) {
        //TODO Требуется уточнение: валидным ли является значение 0. Так как адрес доставки может быть такой же
        if (range < 0) {
            throw new InvalidRangeException();
        }

        if (isFragile && range > MAX_RANGE_FOR_IS_FRAGILE) {
            throw new FragileRangeException(MAX_RANGE_FOR_IS_FRAGILE);
        }

        double amount = 0;

        amount += geRangeCost(range);

        amount += dimensionType.getCost();

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
    private static Double geRangeCost(Double range) {
        /* TODO Требуется уточнение, фрагмент задачи:
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