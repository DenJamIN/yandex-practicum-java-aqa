package org.example.app.enums;

/**
 * Габариты груза
 */
public enum DimensionType {
    /**
     * Большие габариты
     */
    BIG(200.0),
    /**
     * Маленькие габариты
     */
    SMALL(100.0);

    private final Double cost;

    DimensionType(Double cost) {
        this.cost = cost;
    }

    public Double getCost() {
        return cost;
    }
}
