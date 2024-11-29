package org.ey.factories;
import com.fasterxml.jackson.databind.JsonNode;
import org.ey.policies.IPolicies;

import java.util.List;
import java.util.Map;

public abstract class PolicyFactory {
    public abstract IPolicies createPolicy(Map<String, Object> policy);
    public abstract boolean canHandle(Map<String, Object> policy);


}

