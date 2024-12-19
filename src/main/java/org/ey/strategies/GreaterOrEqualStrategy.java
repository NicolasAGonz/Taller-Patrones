package org.ey.strategies;

public class GreaterOrEqualStrategy implements ComparisonStrategy {
    public boolean compare(double amount, double compareTo) {
        return amount >= compareTo;
    }
}
