package org.example.app.enums;

/**
 * Статус загруженности доставки
 */
public enum WorkloadStatus {
    /**
     * Очень высокая загруженность
     */
    VERY_HIGH(1.6),
    /**
     * Высокая загруженность
     */
    HIGH(1.4),
    /**
     * Повышенная загруженность
     */
    INCREASED(1.2),
    /**
     * Загруженность по умолчанию
     */
    DEFAULT(1.0),
    ;

    private final Double ratio;

    WorkloadStatus(Double ratio) {
        this.ratio = ratio;
    }

    public Double getRatio() {
        return ratio;
    }
}
