package org.ey.policies;

import org.ey.enums.ResolutionEvent;

import java.util.List;
import java.util.Map;

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

    @Override
    public List<String> processMovement(Map<String, String> movement, List<String> resolutionEvents) {
        return List.of();
    }

}

