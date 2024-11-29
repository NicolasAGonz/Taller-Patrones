package org.ey;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Map;

public class OtherTests {

    @Test
    public void testNoPolicies(){
        var testDao = new TestPortfolioDao();
        PolicyProcessor policyProcessor = new PolicyProcessor(testDao, false);

        List<Map<String, Object>> policies = List.of();
        List<Map<String, String>> movements= List.of();

        policyProcessor.process(
                policies,
                movements
        );

        Assertions.assertEquals(true, testDao.notInteracted());
    }
}
