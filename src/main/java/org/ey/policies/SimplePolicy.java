package org.ey.policies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class SimplePolicy implements IPolicies {
    private String comparator;
    private String compareTo;
    private List<String> events;
    private static final Logger logger = LoggerFactory.getLogger(SimplePolicy.class);


    public SimplePolicy(String comparator, String compareTo, List<String> events) {
        this.comparator = comparator;
        this.compareTo = compareTo;
        this.events = events;
    }

    @Override
    public void displayDetails() {
        System.out.println("SimplePolicy:");
        System.out.println("Comparator: " + comparator);
        System.out.println("CompareTo: " + compareTo);
        System.out.println("Events: " + events);
    }

    @Override
    public List<String> processMovement(Map<String, String> movement, List<String> resolutionEvents) {
        logger.info("DENTRO DE LA POLICY, RECIBI ESTE LISTADO DE EVENTOS");
        logger.info(String.valueOf(resolutionEvents));

        boolean conditionMet = false;
        double amount = Double.parseDouble(movement.get("amount"));
        double valueToCompare = Double.parseDouble(String.valueOf(compareTo));

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

        // Si la condición se cumple, eliminar los eventos de la lista
        if (conditionMet) {
            logger.info("ELIMINANDO LOS SIGUIENTES EVENTOS");
            logger.info(String.valueOf(events));
            resolutionEvents.removeAll(events);
        }

        return resolutionEvents;
    }


}

