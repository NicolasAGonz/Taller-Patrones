package org.ey.strategies;

public class LessOrEqualStrategy implements ComparisonStrategy {
    public boolean compare(double amount, double compareTo) {
        return amount <= compareTo;
    }
}
