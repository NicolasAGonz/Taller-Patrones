package org.ey.strategies;

public class EqualStrategy implements ComparisonStrategy {
    public boolean compare(double amount, double compareTo) {
        return amount == compareTo;
    }
}
