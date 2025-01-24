package org.ey.policies;

import org.ey.enums.ResolutionEvent;
import org.ey.strategies.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompletePolicy implements IPolicies {
    private String field;
    private String comparator;
    private String compareToValue;
    private String operator;
    private List<String> events;
    private ComparisonStrategy comparisonStrategy;
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

    public void setStrategy(ComparisonStrategy comparisonStrategy){
        this.comparisonStrategy = comparisonStrategy;
    }

    @Override
    public void processMovement(Map<String, String> movement, List<ResolutionEvent> resolutionEvents) {
        logger.info("DENTRO DE LA POLICY, RECIBI ESTE LISTADO DE EVENTOS");
        logger.info(String.valueOf(resolutionEvents));

        boolean conditionMet = false;
        /* MOVEMENTS VARIABLES */
        String movementAmount = movement.get("amount");
        String movementCompany = movement.get("company"); //APPLE, TESLA, OIL...
        String movementType = movement.get("type"); // STOCK, BOND, FUTURE...
        String movementIsForeign = movement.get("isForeign"); // TRUE o FALSE

        logger.info("GENERANDO PREDICADO PARA POLICY COMPLETA...");
        String predicate = field+"-"+comparator+"-"+compareToValue;
        logger.info("PREDICADO GENERADO: {}", predicate);

        switch (field.trim()) {
            case "amount":
                switch (comparator.trim()) {
                    case "greater_than" -> {
                        logger.info("COMPARANDO POR greater_than... ");
                        this.setStrategy(new GreaterThanStrategy() );
                        conditionMet = comparisonStrategy.compare(Double.parseDouble(movementAmount), Double.parseDouble(compareToValue));
                    }
                    case "greater_or_equal" -> {
                        logger.info("COMPARANDO POR greater_or_equal... ");
                        this.setStrategy( new GreaterOrEqualStrategy());
                        conditionMet = comparisonStrategy.compare(Double.parseDouble(movementAmount), Double.parseDouble(compareToValue));
                    }
                    case "less_than" -> {
                        logger.info("COMPARANDO POR less_than... ");
                        this.setStrategy(new LessThanStrategy());
                        conditionMet = comparisonStrategy.compare(Double.parseDouble(movementAmount), Double.parseDouble(compareToValue));
                    }
                    case "less_or_equal" -> {
                        logger.info("COMPARANDO POR less_or_equal... ");
                        this.setStrategy(new LessOrEqualStrategy());
                        conditionMet = comparisonStrategy.compare(Double.parseDouble(movementAmount), Double.parseDouble(compareToValue));
                    }
                    case "equal" -> {
                        logger.info("COMPARANDO POR equal... ");
                        this.setStrategy(new EqualStrategy());
                        conditionMet = comparisonStrategy.compare(Double.parseDouble(movementAmount), Double.parseDouble(compareToValue));
                    }
                    default -> logger.info("Comparador no reconocido: {}", comparator);
                }
                break;
            case "isForeign":
                logger.info("COMPARANDO POR isForeign... ");
                conditionMet = movementIsForeign != null && movementIsForeign.toString().equalsIgnoreCase(compareToValue);
                break;
            case "type":
                logger.info("COMPARANDO POR type... ");
                conditionMet = movementType != null && movementType.toString().equalsIgnoreCase(compareToValue);
                break;
            case "company":
                logger.info("COMPARANDO POR company... ");
                conditionMet = movementCompany != null && movementCompany.toString().equalsIgnoreCase(compareToValue);
                break;
            default:
                logger.info("Campo de comparacion no reconocido: {}", field);
                break;
        }
        logger.info("RESULTADO DE LA COMPARACION: {} ", conditionMet);

        if (conditionMet) {
            switch (operator.trim().toUpperCase()) {
                case "NOT" -> {
                    // se eliminan los eventos de events de la lista resultante.
                    logger.info("APLICANDO OPERATOR NOT");
                    for (String event : events) {
                        resolutionEvents.removeIf(e -> e.name().equals(event));
                    }
                }
                case "ONLY" -> {
                    // events reemplaza y pasa a ser la lista resultante.
                    logger.info("APLICANDO OPERATOR ONLY");
                    resolutionEvents.clear();
                    resolutionEvents.addAll(events.stream()
                            .map(ResolutionEvent::valueOf)
                            .toList());
                }
                case "RETURN" -> {
                    // No se ejecutan más políticas y el resultado es el primer elemento de events.
                    logger.info("APLICANDO OPERATOR RETURN");
                    resolutionEvents.clear();
                    for (String event : events) {
                        resolutionEvents.add(ResolutionEvent.valueOf(event));
                    }
                    return; // Detiene la evaluación adicional
                }
                default -> {
                    throw new IllegalArgumentException("Operador no reconocido: " + operator);
                }
            }; //END of switch
        }; // END of for
    };
};