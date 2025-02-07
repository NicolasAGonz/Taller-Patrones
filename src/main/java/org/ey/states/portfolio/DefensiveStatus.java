package org.ey.states.portfolio;

import org.ey.enums.PortfolioStatus;
import org.ey.enums.ResolutionEvent;

public class DefensiveStatus implements IPortfolioState {

    @Override
    public PortfolioStatus getNextStatus(ResolutionEvent resultEvent) {
        return switch (resultEvent) {
            case EXTREME_RISK, OUT_OF_INVESTORS -> PortfolioStatus.CLOSED;
            case BULL -> PortfolioStatus.ACTIVE;
            case BEAR, DEBT_DEFAULT, MARKET_COLLAPSE -> PortfolioStatus.EMPTY;
            default -> PortfolioStatus.DEFENSIVE;
        };
    }
}
