package org.ey.states.portfolio;

import org.ey.enums.PortfolioStatus;
import org.ey.enums.ResolutionEvent;

public class EmptyStatus implements IPortfolioState {

    @Override
    public PortfolioStatus getNextStatus(ResolutionEvent resultEvent) {
        return switch (resultEvent) {
            case EXTREME_RISK -> PortfolioStatus.CLOSED;
            case BULL -> PortfolioStatus.ACTIVE;
            case DEBT_DEFAULT -> PortfolioStatus.DEFENSIVE;
            default -> PortfolioStatus.EMPTY;  // Cubre Bear(Empty) -> Empty, MarketC(Empty) -> Empty
        };
    }
}
