package test;

import io.qameta.allure.Story;
import org.example.app.ShippingCalculator;
import org.example.app.enums.DimensionType;
import org.example.app.enums.WorkloadStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Story("Минимальная сумма доставки")
public class MinShippingAmountTests {

    @DisplayName("Расчет суммы ниже минимальной")
    @ParameterizedTest
    @CsvSource({
            "1.0, SMALL, false, DEFAULT",
            "0.0, BIG, false, DEFAULT",
            "5.0, SMALL, false, INCREASED"
    })
    public void testAmountBelowMinimum(Double range, DimensionType dimensionType, Boolean isFragile, WorkloadStatus workloadStatus) {
        Double actual = ShippingCalculator.calculateAmount(range, dimensionType, isFragile, workloadStatus);
        assertEquals(ShippingCalculator.MIN_SHIPPING_AMOUNT, actual, "Сумма должна быть не ниже минимальной");
    }

    //TODO доработать
    @DisplayName("Расчет суммы равной или выше минимальной")
    @ParameterizedTest
    @CsvSource({
            "15.0, BIG, false, HIGH, 560.0",
            "10.0, SMALL, true, DEFAULT, 500.0",
            "30.0, SMALL, false, DEFAULT, 400.0"
    })
    public void testAmountAtOrAboveMinimum(Double range, DimensionType dimensionType, Boolean isFragile, WorkloadStatus workloadStatus, Double expected) {
        Double actual = ShippingCalculator.calculateAmount(range, dimensionType, isFragile, workloadStatus);
        assertEquals(expected, actual, "Сумма должна быть рассчитана корректно, если больше минимальной");
    }
}
