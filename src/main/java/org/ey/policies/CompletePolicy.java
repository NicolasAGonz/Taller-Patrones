package org.ey.policies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class CompletePolicy implements IPolicies {
    private String field;
    private String comparator;
    private String compareToValue;
    private String operator;
    private List<String> events;
    private static final Logger logger = LoggerFactory.getLogger(CompletePolicy.class);

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
        logger.info("DENTRO DE LA POLICY, RECIBI ESTE LISTADO DE EVENTOS");
        logger.info(String.valueOf(resolutionEvents));

        boolean conditionMet = false;
        double amount = Double.parseDouble(movement.get("amount"));
        double valueToCompare = Double.parseDouble(String.valueOf(compareToValue));

        // Evaluar la condición según el comparador
        switch (comparator.trim()) {
            case "greater_than":
                logger.info("COMPARANDO POR greater_than... ");
                conditionMet = amount > valueToCompare;
                break;
            case "greater_or_equal":
                logger.info("COMPARANDO POR greater_or_equal... ");
                conditionMet = amount >= valueToCompare;
                break;
            case "less_than":
                logger.info("COMPARANDO POR less_than... ");
                conditionMet = amount < valueToCompare;
                break;
            case "less_or_equal":
                logger.info("COMPARANDO POR less_or_equal... ");
                conditionMet = amount <= valueToCompare;
                break;
            case "equal":
                logger.info("COMPARANDO POR equal... ");
                conditionMet = amount == valueToCompare;
                break;
            default:
                logger.info("Comparador no reconocido: " + comparator);
                break;
        };

        logger.info("RESULTADO DE LA COMPARACION");
        logger.info(String.valueOf(conditionMet));



        return resolutionEvents;
    }

}

