package org.ey.strategies;

// Implementaciones de las estrategias de comparación
public class GreaterThanStrategy implements ComparisonStrategy {
    public boolean compare(double amount, double compareTo) {
        return amount > compareTo;
    }
}
