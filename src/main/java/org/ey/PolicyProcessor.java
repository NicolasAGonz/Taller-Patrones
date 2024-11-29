package org.ey;


import org.ey.dao.PortfolioDAO;
import org.ey.enums.PortfolioStatus;
import org.ey.factories.CompletePolicyFactory;
import org.ey.factories.PolicyFactory;
import org.ey.factories.PolicyFactoryManager;
import org.ey.factories.SimplePolicyFactory;
import org.ey.policies.IPolicies;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PolicyProcessor {
    final PortfolioDAO dao;
    boolean useSimplePolicies;

    public PolicyProcessor(final PortfolioDAO dao, boolean useSimplePolicies){
        this.dao = dao;
        this.useSimplePolicies = useSimplePolicies;
    }

    public final PortfolioDAO getDao() {
        return dao;
    }

    public void setUseSimplePolicies(boolean flag) {
        this.useSimplePolicies = flag;
    }

    // ### EJEMPLO. EN LOS TEST SE USARÁ EL METODO "process" ###
    public void processEjemplo(List<Map<String, Object>> policies, List<Map<String, String>> movements){
        System.out.println(movements);
        System.out.println(policies);

        movements.forEach(
                movement -> {
                    var id = movement.get("carteraId");
                    var oldStatus = dao.getPortfolioStatus(Long.parseLong(id));
                    var newHardcodedStatus =  PortfolioStatus.VIP;
                    //Ejemplo. El estado nuevo debe ser el resultado de procesar reglas de políticas.
                    System.out.println("Cartera Id: " + id +
                            ", Status Viejo: " + oldStatus + ", Status Nuevo: " + newHardcodedStatus);
                }
        );

    }

    public void process(List<Map<String, Object>> policies, List<Map<String, String>> movements) {
        // TODO COMPLETAR
        System.out.println("Imprimiendo politicas");
        System.out.println(policies);

        // Crear y registrar policy factories
        List<PolicyFactory> factories = new ArrayList<>();
        factories.add(new SimplePolicyFactory());
        factories.add(new CompletePolicyFactory());

        PolicyFactoryManager factoryManager = new PolicyFactoryManager(factories);

        // Procesar cada JSON de la lista
        for (Map<String, Object> policy : policies) {
            try {
                IPolicies newPolicy = factoryManager.createPolicy(policy);
                newPolicy.displayDetails();
            } catch (Exception e) {
                System.err.println("Error de creación de política: " + e.getMessage());
            }
        }


    }
}
