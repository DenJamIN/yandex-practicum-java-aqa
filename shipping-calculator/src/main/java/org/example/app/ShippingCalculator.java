package org.example.app;

import org.example.app.enums.DimensionType;
import org.example.app.enums.WorkloadStatus;
import org.example.app.exceptions.FragileRangeException;
import org.example.app.exceptions.InvalidRangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Статический класс для расчёта стоимости доставки груза
 */
public class ShippingCalculator {

    /**
     * Логирование SLF4J
     */
    private static final Logger log = LoggerFactory.getLogger(ShippingCalculator.class);

    /**
     * Минимальная стоимость доставки
     */
    public static final Double MIN_SHIPPING_AMOUNT = 400.0;
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
        log.info("Начат расчёт стоимости доставки..");

        //TODO Требуется уточнение: валидным ли является значение 0. Так как адрес доставки может быть такой же
        if (range < 0) {
            log.debug("Расчёт прерван. [range={}] < 0", range);
            throw new InvalidRangeException();
        }
        if (isFragile && range > MAX_RANGE_FOR_IS_FRAGILE) {
            log.debug("Расчёт прерван. недопустимое расстояние для хрупкого груза: [range={}] > {}", range, MAX_RANGE_FOR_IS_FRAGILE);
            throw new FragileRangeException(MAX_RANGE_FOR_IS_FRAGILE);
        }

        double amount = 0;

        amount += geRangeCost(range);
        log.debug("Расчёта по параметру [Расстояние доставки], текущая стоимость доставки {}", amount);

        amount += dimensionType.getCost();
        log.debug("Расчёт по параметру [Габарит груза], текущая стоимость доставки: {}", amount);

        if (isFragile) {
            amount += 300;
        }
        log.debug("Расчёт по параметру [Хрупкость товара], текущая стоимость доставки: {}", amount);

        amount *= workloadStatus.getRatio();
        log.debug("Расчёт по параметру [Загруженность доставки], текущая стоимость доставки: {}", amount);

        amount = amount > MIN_SHIPPING_AMOUNT ? amount : MIN_SHIPPING_AMOUNT;
        log.info("Итоговая стоимость доставки: {}", amount);
        return amount;
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