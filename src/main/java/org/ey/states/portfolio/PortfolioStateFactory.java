package org.ey.states.portfolio;

import org.ey.enums.PortfolioStatus;
import java.util.Map;

public class PortfolioStateFactory {

    private static final Map<PortfolioStatus, IPortfolioState> status = Map.of(
            PortfolioStatus.ACTIVE, new ActiveStatus(),
            PortfolioStatus.CLOSED, new ClosedStatus(),
            PortfolioStatus.DEFENSIVE, new DefensiveStatus(),
            PortfolioStatus.EMPTY, new EmptyStatus(),
            PortfolioStatus.VIP, new VipStatus()
    );

    public static IPortfolioState getStatus(PortfolioStatus portfolioStatus) {
        return status.getOrDefault(status, new DefaultStatus());
    }
}
