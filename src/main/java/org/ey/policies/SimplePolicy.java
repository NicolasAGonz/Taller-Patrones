package org.ey.policies;
import org.ey.enums.ResolutionEvent;
import org.ey.strategies.ComparisonStrategy;

import java.util.List;
import java.util.Map;

public class SimplePolicy implements IPolicies {
    private String comparator;
    private String compareTo;
    private List<String> events;

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
        System.out.println("!!!!!!!!!!!");
        System.out.println("DENTRO DE LA POLICY, RECIBI ESTE LISTADO DE EVENTOS");
        System.out.println(resolutionEvents);

        boolean conditionMet = false;
        double amount = Double.parseDouble(movement.get("amount"));
        double compareToValue = Double.parseDouble(String.valueOf(compareTo));

        // Evaluar la condición según el comparador
        switch (comparator.trim()) {
            case "greater_than":
                System.out.println("COMPARANDO POR greater_than... ");
                conditionMet = amount > compareToValue;
                break;
            case "greater_or_equal":
                System.out.println("COMPARANDO POR greater_or_equal... ");
                conditionMet = amount >= compareToValue;
                break;
            case "less_than":
                System.out.println("COMPARANDO POR less_than... ");
                conditionMet = amount < compareToValue;
                break;
            case "less_or_equal":
                System.out.println("COMPARANDO POR less_or_equal... ");
                conditionMet = amount <= compareToValue;
                break;
            case "equal":
                System.out.println("COMPARANDO POR equal... ");
                conditionMet = amount == compareToValue;
                break;
            default:
                System.out.println("Comparador no reconocido: " + comparator);
                break;
        };

        System.out.println("RESULTADO DE LA COMPARACION");
        System.out.println(conditionMet);

        // Si la condición se cumple, eliminar los eventos de la lista
        if (conditionMet) {
            System.out.println("ELIMINANDO LOS SIGUIENTES EVENTOS");
            System.out.println(events);
            resolutionEvents.removeAll(events);
        }

        return resolutionEvents;
    }


}

