package org.ey.states.portfolio;

import org.ey.enums.PortfolioStatus;
import org.ey.enums.ResolutionEvent;

public class VipStatus implements IPortfolioState {
    @Override
    public PortfolioStatus getNextStatus(ResolutionEvent resultEvent) {
        return switch (resultEvent) {
            case EXTREME_RISK, MARKET_COLLAPSE -> PortfolioStatus.CLOSED;
            case BEAR -> PortfolioStatus.EMPTY;
            case DEBT_DEFAULT -> PortfolioStatus.DEFENSIVE;
            default -> PortfolioStatus.VIP; //Este cubre Bull(Vip) -> VIP, OOI(Vip) -> VIP
        };
    };
};
