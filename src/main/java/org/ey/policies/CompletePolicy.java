package org.ey.policies;

import java.util.List;

public class CompletePolicy implements IPolicies {
    private String field;
    private String comparator;
    private String compareToValue;
    private String operator;
    private List<String> events;

    public CompletePolicy(String field, String comparator, String compareToValue, String operator, List<String> events) {
        this.field = field;
        this.comparator = comparator;
        this.compareToValue = compareToValue;
        this.operator = operator;
        this.events = events;
    }

    @Override
    public void displayDetails() {
        System.out.println("CompletePolicy:");
        System.out.println("Field: " + field);
        System.out.println("Comparator: " + comparator);
        System.out.println("CompareToValue: " + compareToValue);
        System.out.println("Operator: " + operator);
        System.out.println("Events: " + events);
    }

}

