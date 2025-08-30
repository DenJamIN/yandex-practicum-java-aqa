package test;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.example.app.ShippingCalculator;
import org.example.app.enums.DimensionType;
import org.example.app.enums.WorkloadStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Feature("Расчёт суммы доставки")
public class CalculateShippingTests extends BaseTest {

    @DisplayName("Расчёт суммы доставки")
    @ParameterizedTest
    @CsvSource({
            "0, SMALL, true, DEFAULT, 450.0",
            "0, SMALL, true, HIGH, 630.0",
            "1, BIG, true, INCREASED, 660.0",
            "1, BIG, true, VERY_HIGH, 880.0",
            "2, SMALL, true, HIGH, 630.0",
            "2, SMALL, true, DEFAULT, 450.0",
            "2.1, BIG, true, VERY_HIGH, 960.0",
            "2.1, BIG, true, INCREASED, 720.0",
            "5, SMALL, true, DEFAULT, 500.0",
            "5, BIG, false, VERY_HIGH, 480.0",
            "5, SMALL, true, HIGH, 700.0",
            "10, BIG, true, INCREASED, 720.0",
            "10, BIG, true, VERY_HIGH, 960.0",
            "10.1, SMALL, true, HIGH, 840.0",
            "10.1, BIG, false, INCREASED, 480.0",
            "10.1, SMALL, true, DEFAULT, 600.0",
            "10.1, BIG, false, VERY_HIGH, 640.0",
            "15, BIG, true, VERY_HIGH, 1120.0",
            "15, SMALL, false, HIGH, 420.0",
            "15, BIG, true, INCREASED, 840.0",
            "30, SMALL, true, DEFAULT, 600.0",
            "30, BIG, false, VERY_HIGH, 640.0",
            "30, SMALL, true, HIGH, 840.0",
            "30, BIG, false, INCREASED, 480.0",
            "30.1, SMALL, false, HIGH, 560.0",
            "35, BIG, false, INCREASED, 600.0",
            "35, BIG, false, VERY_HIGH, 800.0",
    })
    public void calculateShipping(Double range, DimensionType dimensionType,
                                  Boolean isFragile, WorkloadStatus workloadStatus,
                                  Double expected) {

        Double actual = ShippingCalculator.calculateAmount(range, dimensionType, isFragile, workloadStatus);
        assertEquals(expected, actual, "Сумма рассчитана некорректно");
    }

    @Story("Минимальная сумма доставки")
    @DisplayName("Расчет суммы ниже минимальной")
    @ParameterizedTest
    @CsvSource({
            "0, BIG, false, VERY_HIGH",
            "0, BIG, false, INCREASED",
            "1, SMALL, false, DEFAULT",
            "1, SMALL, false, HIGH",
            "2, BIG, false, INCREASED",
            "2, BIG, false, VERY_HIGH",
            "2.1, SMALL, false, HIGH",
            "2.1, SMALL, false, DEFAULT",
            "5, BIG, false, INCREASED",
            "10, SMALL, false, DEFAULT",
            "10, SMALL, false, HIGH",
            "15, SMALL, false, DEFAULT",
            "30.1, SMALL, false, DEFAULT",
    })

    public void testAmountBelowMinimum(Double range, DimensionType dimensionType,
                                       Boolean isFragile, WorkloadStatus workloadStatus) {
        Double actual = ShippingCalculator.calculateAmount(range, dimensionType, isFragile, workloadStatus);
        assertEquals(ShippingCalculator.MIN_SHIPPING_AMOUNT,
                actual,
                "Сумма должна быть не ниже минимальной");
    }
}
