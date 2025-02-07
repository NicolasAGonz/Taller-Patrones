package org.ey.states.portfolio;

import org.ey.enums.PortfolioStatus;
import org.ey.enums.ResolutionEvent;

public class ActiveStatus implements IPortfolioState {
    @Override
    public PortfolioStatus getNextStatus(ResolutionEvent resultEvent) {
        return switch (resultEvent) {
            case EXTREME_RISK -> PortfolioStatus.CLOSED;
            case BULL -> PortfolioStatus.VIP;
            case MARKET_COLLAPSE, BEAR -> PortfolioStatus.EMPTY;
            case OUT_OF_INVESTORS, DEBT_DEFAULT -> PortfolioStatus.DEFENSIVE;
            default -> PortfolioStatus.ACTIVE;
        };
    }
}
