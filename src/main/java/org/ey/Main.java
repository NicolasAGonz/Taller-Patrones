package org.ey;

import org.ey.dao.InMemoryPortfolioDAO;
import org.ey.utils.UtilsProxy;

public class Main {
    public static void main(String[] args) {
        //policies
        var policiesSimple = UtilsProxy.readPolicies("src/main/resources/policies-simple.json");
        var policiesComplete = UtilsProxy.readPolicies("src/main/resources/policies.json");

        //movements
        var movements = UtilsProxy.readMovements();

        PolicyProcessor policyProcessor = new PolicyProcessor(new InMemoryPortfolioDAO(), true);

        policyProcessor.process(policiesSimple, movements);

        //policyProcessor.setUseSimplePolicies(false);
        //policyProcessor.processEjemplo(policiesComplete, movements);

    }
}