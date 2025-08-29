package test;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.example.app.ShippingCalculator;
import org.example.app.enums.DimensionType;
import org.example.app.enums.WorkloadStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Feature("Негативные сценарии тестирования")
public class NegativeTestCase {

    @Test
    @Story("Расстояние")
    @DisplayName("Отрицательное значение расстояния доставки")
    public void negativeRange() {
        Double range = -1.0;
        DimensionType dimensionType = DimensionType.BIG;
        Boolean isFragile = true;
        WorkloadStatus workloadStatus = WorkloadStatus.DEFAULT;

        assertThrows(
                IllegalArgumentException.class,
                () -> ShippingCalculator.calculateAmount(range, dimensionType, isFragile, workloadStatus),
                "Валидация неверного значения успешно прошла");
    }

    @Test
    @Story("Расстояние")
    @DisplayName("Доставка хрупкого груза на расстояние более 30км")
    public void isFragileToRangeGreaterPossible() {
        Double range = 30.1;
        DimensionType dimensionType = DimensionType.BIG;
        Boolean isFragile = true;
        WorkloadStatus workloadStatus = WorkloadStatus.DEFAULT;

        assertThrows(
                IllegalArgumentException.class,
                () -> ShippingCalculator.calculateAmount(range, dimensionType, isFragile, workloadStatus),
                "Валидация неверного значения успешно прошла");
    }
}
