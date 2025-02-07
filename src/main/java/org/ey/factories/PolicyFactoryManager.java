package org.ey.factories;
import com.fasterxml.jackson.databind.JsonNode;
import org.ey.PolicyProcessor;
import org.ey.policies.IPolicies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class PolicyFactoryManager {
    private final List<PolicyFactory> factories;
    private static final Logger logger = LoggerFactory.getLogger(PolicyFactoryManager.class);

    public PolicyFactoryManager(List<PolicyFactory> factories) {
        this.factories = factories;
    }

    public IPolicies createPolicy(Map<String, Object> policy) {
        for (PolicyFactory factory : factories) {
            if (factory.canHandle(policy)) {
                return factory.createPolicy(policy);
            } else {
                logger.error("!!!!!!!!!!!!!!!------La policy ingresada no pudo ser procesada por la factory solicitada.------!!!!!!!!!!!!!!!");
            }
        }
        throw new IllegalArgumentException("No factory can handle the given policy: " + policy);
    }
}
