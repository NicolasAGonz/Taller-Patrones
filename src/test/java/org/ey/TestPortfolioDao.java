package org.ey;

import org.ey.dao.InMemoryPortfolioDAO;
import org.ey.enums.PortfolioStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Proxy del PortfolioDAO, que le agrega funcionalidad para contar cuantas veces fue invocado cada métod
 */
public class TestPortfolioDao extends InMemoryPortfolioDAO {

    private final Map<String, Integer> invocationMap;
    private final Map<String, Integer> exceptionMap;

    public TestPortfolioDao() {
        invocationMap = new HashMap<>();
        invocationMap.put("savePortfolioStatus", 0 );
        invocationMap.put("getPortfolioStatus", 0);

        exceptionMap = new HashMap<>();
        exceptionMap.put("savePortfolioStatus", 0 );
        exceptionMap.put("getPortfolioStatus", 0);
    }

    @Override
    public void savePortfolioStatus(Long Id, PortfolioStatus status) {
        try {
            super.savePortfolioStatus(Id, status);
            invocationMap.put("savePortfolioStatus", invocationMap.get("savePortfolioStatus") + 1);
        }
        catch (Exception e){
            exceptionMap.put("savePortfolioStatus", exceptionMap.get("savePortfolioStatus") + 1);
            throw e;
        }
    }

    @Override
    public PortfolioStatus getPortfolioStatus(Long Id) {
        try {
            var result = super.getPortfolioStatus(Id);
            invocationMap.put("getPortfolioStatus", invocationMap.get("getPortfolioStatus") + 1);
            return result;
        } catch (Exception e){
            exceptionMap.put("getPortfolioStatus", exceptionMap.get("getPortfolioStatus") + 1);
            throw e;
        }
    }

    public Integer getInvocations(String method) {
        return invocationMap.get(method);
    }

    public Integer getExceptions(String method) {
        return exceptionMap.get(method);
    }


    public Boolean notInteracted() {
        return exceptionMap.get("getPortfolioStatus").equals(0) && invocationMap.get("getPortfolioStatus").equals(0) &&
                exceptionMap.get("savePortfolioStatus").equals(0) && invocationMap.get("savePortfolioStatus").equals(0);
    }

    public Integer getTotalExceptions() {
        return  exceptionMap.get("savePortfolioStatus") +  exceptionMap.get("getPortfolioStatus");
    }

    @Override
    public PortfolioStatus getPortfolioStatusRaw(Long Id) {
        return super.getPortfolioStatusRaw(Id);
    }

}
