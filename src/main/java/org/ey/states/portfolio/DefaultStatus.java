package org.ey.states.portfolio;

import org.ey.enums.PortfolioStatus;
import org.ey.enums.ResolutionEvent;

public class DefaultStatus implements IPortfolioState {
    @Override
    public PortfolioStatus getNextStatus(ResolutionEvent resultEvent) {
        return switch (resultEvent) {
            case EXTREME_RISK, MARKET_COLLAPSE -> PortfolioStatus.CLOSED;
            case BULL -> PortfolioStatus.ACTIVE;
            case BEAR -> PortfolioStatus.EMPTY;
            default -> PortfolioStatus.DEFENSIVE;
        };
    }
}
