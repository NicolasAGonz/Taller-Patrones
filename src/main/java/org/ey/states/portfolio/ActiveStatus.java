package org.ey.states.portfolio;

import org.ey.enums.PortfolioStatus;
import org.ey.enums.ResolutionEvent;

public class ActiveStatus implements IPortfolioState {
    @Override
    public PortfolioStatus getNextStatus(ResolutionEvent resultEvent) {
        return switch (resultEvent) {
            case ResolutionEvent.OUT_OF_INVESTORS, ResolutionEvent.DEBT_DEFAULT -> PortfolioStatus.DEFENSIVE;
            case ResolutionEvent.MARKET_COLLAPSE, BEAR -> PortfolioStatus.EMPTY;
            case ResolutionEvent.EXTREME_RISK -> PortfolioStatus.CLOSED;
            case ResolutionEvent.BULL -> PortfolioStatus.VIP;
            default -> PortfolioStatus.ACTIVE;
        };
    }
}
