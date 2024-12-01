package org.ey.factories;
import com.fasterxml.jackson.databind.JsonNode;
import org.ey.policies.CompletePolicy;
import org.ey.policies.IPolicies;

import java.util.List;
import java.util.Map;

public class CompletePolicyFactory extends PolicyFactory {

    @Override
    public IPolicies createPolicy(Map<String, Object> policy) {
        String field = (String) policy.get("field");
        String comparator = (String) policy.get("comparator");
        String compareToValue = (String) policy.get("compareToValue");
        String operator = (String) policy.get("operator");

        // Validar que los eventos sean una lista
        @SuppressWarnings("unchecked")
        List<String> events = (List<String>) policy.get("events");

        return new CompletePolicy(field, comparator, compareToValue, operator, events);
    }

    @Override
    public boolean canHandle(Map<String, Object> policy) {
        System.out.println("ESTOY EN EL HANDLER DEL FACTORY DE POLICIES COMPLETAS, RECIBI ESTA POLICY");
        System.out.println(policy);
        // CompletePolicy se maneja si tiene las claves adicionales "field" y "operator".
        return policy.containsKey("field") && policy.containsKey("operator") && policy.get("events") instanceof List;
    }
}

