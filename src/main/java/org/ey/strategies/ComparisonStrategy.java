package org.ey.strategies;

// Interfaz para la estrategia de comparación
public interface ComparisonStrategy {
    boolean compare(double amount, double compareTo);
}

// Implementaciones de las estrategias de comparación
class GreaterThanStrategy implements ComparisonStrategy {
    public boolean compare(double amount, double compareTo) {
        return amount > compareTo;
    }
}

class GreaterOrEqualStrategy implements ComparisonStrategy {
    public boolean compare(double amount, double compareTo) {
        return amount >= compareTo;
    }
}

class LessThanStrategy implements ComparisonStrategy {
    public boolean compare(double amount, double compareTo) {
        return amount < compareTo;
    }
}

class LessOrEqualStrategy implements ComparisonStrategy {
    public boolean compare(double amount, double compareTo) {
        return amount <= compareTo;
    }
}

class EqualStrategy implements ComparisonStrategy {
    public boolean compare(double amount, double compareTo) {
        return amount == compareTo;
    }
}
