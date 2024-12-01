package org.ey.factories;
import com.fasterxml.jackson.databind.JsonNode;
import org.ey.policies.IPolicies;
import java.util.List;
import java.util.Map;

public class PolicyFactoryManager {
    private final List<PolicyFactory> factories;

    public PolicyFactoryManager(List<PolicyFactory> factories) {
        this.factories = factories;
    }

    public IPolicies createPolicy(Map<String, Object> policy) {
        for (PolicyFactory factory : factories) {
            if (factory.canHandle(policy)) {
                return factory.createPolicy(policy);
            } else {
                System.out.println("La policy ingresada no pudo ser procesada por la factory solicitada.");
            }
        }
        throw new IllegalArgumentException("No factory can handle the given policy: " + policy);
    }
}
