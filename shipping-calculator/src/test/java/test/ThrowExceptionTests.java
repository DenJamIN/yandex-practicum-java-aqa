package test;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.example.app.ShippingCalculator;
import org.example.app.enums.DimensionType;
import org.example.app.enums.WorkloadStatus;
import org.example.app.exceptions.FragileRangeException;
import org.example.app.exceptions.InvalidRangeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Feature("Исключения валидации")
public class ThrowExceptionTests extends BaseTest {

    @Story("Расстояние")
    @DisplayName("Отрицательное значение расстояния доставки")
    @ParameterizedTest
    @CsvSource({
            "-1.0, SMALL, false, DEFAULT",
            "-1.0, BIG, true, INCREASED",
            "-1.0, SMALL, false, HIGH",
            "-1.0, BIG, true, VERY_HIGH",
    })
    public void negativeRange(Double range, DimensionType dimensionType,
                              Boolean isFragile, WorkloadStatus workloadStatus) {
        assertThrows(
                InvalidRangeException.class,
                () -> ShippingCalculator.calculateAmount(range, dimensionType, isFragile, workloadStatus));
    }

    @Story("Хрупкость")
    @DisplayName("Доставка хрупкого груза на расстояние более 30км")
    @ParameterizedTest
    @CsvSource({
            "35, SMALL, true, DEFAULT",
            "30.1, BIG, true, INCREASED",
            "35, SMALL, true, HIGH",
            "30.1, BIG, true, VERY_HIGH",
    })
    public void isFragileToRangeGreaterPossible(Double range, DimensionType dimensionType,
                                                Boolean isFragile, WorkloadStatus workloadStatus) {
        assertThrows(
                FragileRangeException.class,
                () -> ShippingCalculator.calculateAmount(range, dimensionType, isFragile, workloadStatus));
    }
}
