package org.ey.factories;
import com.fasterxml.jackson.databind.JsonNode;
import org.ey.policies.IPolicies;
import org.ey.policies.SimplePolicy;

import java.util.List;
import java.util.Map;

public class SimplePolicyFactory extends PolicyFactory {

    @Override
    public IPolicies createPolicy(Map<String, Object> policy) {
        String comparator = (String) policy.get("comparator");
        String compareTo = (String) policy.get("compareTo");
        // Validar que los eventos sean una lista
        @SuppressWarnings("unchecked")
        List<String> events = (List<String>) policy.get("events");

        return new SimplePolicy(comparator, compareTo, events);
    }

    @Override
    public boolean canHandle(Map<String, Object> policy) {
        System.out.println("ESTOY EN EL HANDLER DEL FACTORY DE POLICIES SIMPLE, RECIBI ESTA POLICY");
        System.out.println(policy);
        // SimplePolicy se maneja si solo tiene las claves "comparator", "compareToValue" y "events".
        return !policy.containsKey("field") && !policy.containsKey("operator");
    }
}

