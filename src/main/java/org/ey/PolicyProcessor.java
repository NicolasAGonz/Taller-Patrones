package org.ey;


import org.ey.dao.PortfolioDAO;
import org.ey.enums.PortfolioStatus;
import org.ey.factories.CompletePolicyFactory;
import org.ey.factories.PolicyFactory;
import org.ey.factories.PolicyFactoryManager;
import org.ey.factories.SimplePolicyFactory;
import org.ey.policies.IPolicies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PolicyProcessor {
    final PortfolioDAO dao;
    boolean useSimplePolicies;
    private static final Logger logger = LoggerFactory.getLogger(PolicyProcessor.class);

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
        logger.info(movements.toString());
        logger.info(policies.toString());

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
        // Crear y registrar policy factories
        List<PolicyFactory> factories = new ArrayList<>();
        factories.add(new SimplePolicyFactory());
        factories.add(new CompletePolicyFactory());
        PolicyFactoryManager factoryManager = new PolicyFactoryManager(factories);

        //Crear y registrar las policies creadas
        List<IPolicies> createdPolicies = new ArrayList<>();

        // Procesar cada JSON de la lista
        for (Map<String, Object> policy : policies) {
            try {
                IPolicies newPolicy = factoryManager.createPolicy(policy);
                createdPolicies.add(newPolicy);
            } catch (Exception e) {
                logger.error("Error de creación de policy: {}", e.getMessage());
            }
        }

        logger.info("SE HAN CREADO LAS SIGUIENTES POLICIES");
        for (IPolicies policy : createdPolicies){
            policy.displayDetails();
        }



        movements.forEach(
                movement -> {
                    logger.info("/**************** PROCESANDO NUEVO MOVIMIENTO ****************/");
                    logger.info("CONSTRUYENDO LISTA DE EVENTOS");
                    ArrayList<String> resolutionEvents = new ArrayList<>(List.of("EXTREME_RISK", "BULL", "BEAR", "DEBT_DEFAULT", "MARKET_COLLAPSE", "AUDIT_RISK", "OUT_OF_INVESTORS"));
                    //ArrayList<ResolutionEvent> resolutionEvents = new ArrayList<>(getAllEventsForProcess());


                    logger.info("PROCESANDO MOVIMIENTO:");
                    logger.info(movement.toString());

                    logger.info("COMENZANDO EJECUCION DE POLICIES");
                    for (IPolicies policy : createdPolicies){
                        resolutionEvents = (ArrayList<String>) policy.processMovement(movement, resolutionEvents);
                    }

                    logger.info("LISTADO FINAL DE EVENTOS");
                    logger.info(String.valueOf(resolutionEvents));
                    logger.info("PRIMER EVENTO DEL LISTADO");
                    logger.info(resolutionEvents.get(0));

                }
        );

    }
}
