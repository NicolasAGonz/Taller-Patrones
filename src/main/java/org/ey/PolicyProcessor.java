package org.ey;

import org.ey.dao.PortfolioDAO;
import org.ey.enums.PortfolioStatus;
import org.ey.enums.ResolutionEvent;
import org.ey.factories.CompletePolicyFactory;
import org.ey.factories.PolicyFactory;
import org.ey.factories.PolicyFactoryManager;
import org.ey.factories.SimplePolicyFactory;
import org.ey.policies.IPolicies;
import org.ey.states.portfolio.IPortfolioState;
import org.ey.states.portfolio.PortfolioStateFactory;
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

        logger.info("/****************************************************************************/");
        logger.info("/**************** COMENZANDO EL PROCESAMIENTO DE MOVIMIENTOS ****************/");
        logger.info("/****************************************************************************/");

        movements.forEach(
                movement -> {
                    logger.info("/**************** PROCESANDO NUEVO MOVIMIENTO ****************/");
                    logger.info("CONSTRUYENDO LISTA DE EVENTOS");
                    List<ResolutionEvent> resolutionEvents = new ArrayList<>(ResolutionEvent.getAllEventsForProcess());
                    logger.info("PROCESANDO MOVIMIENTO:", movement.toString() );


                    logger.info("COMENZANDO EJECUCION DE POLICIES");
                    for (IPolicies policy : createdPolicies){
                        policy.processMovement(movement, resolutionEvents);
                        logger.info("POLICY PROCESADA, IMPRIMIENDO ESTADO ACTUAL DE EVENTOS");
                        logger.info(resolutionEvents.toString());
                    };

                    logger.info("FINALIZO EL PROCESAMIENTO DE POLICIES PARA EL MOVIMIENTO, IMPRIMIENDO LISTADO FINAL DE EVENTOS");
                    logger.info(resolutionEvents.toString());

                    if (!resolutionEvents.isEmpty()) {
                        String eventToApply = resolutionEvents.getFirst().name();
                        logger.info("SE APLICARA EL SIGUIENTE EVENTO: {}", eventToApply);

                        ResolutionEvent resultEvent = ResolutionEvent.valueOf(eventToApply);
                        String carteraId = movement.get("carteraId");

                        logger.info("OBTENIENDO ESTADO ACTUAL DEL PORTAFOLIO");
                        PortfolioStatus currentStatus = dao.getPortfolioStatus(Long.parseLong(carteraId));
                        logger.info("ESTADO ACTUAL DEL PORTAFOLIO: {}", currentStatus);
                        IPortfolioState currentState = PortfolioStateFactory.getStatus(currentStatus);

                        logger.info("CALCULANDO PROXIMO ESTADO...");
                        PortfolioStatus nextStatus = currentState.getNextStatus(resultEvent);

                        logger.info("PASANDO CARTERA AL ESTADO: {}", nextStatus);
                        dao.savePortfolioStatus(Long.valueOf(carteraId), nextStatus);
                    };
                    logger.info("/**************** FINALIZO EL PROCESAMIENTO DEL MOVIMIENTO ****************/");
                });

        logger.info("/****************************************************************************/");
        logger.info("/**************** FINALIZADO EL PROCESAMIENTO DE MOVIMIENTOS ****************/");
        logger.info("/****************************************************************************/");
    };
};
