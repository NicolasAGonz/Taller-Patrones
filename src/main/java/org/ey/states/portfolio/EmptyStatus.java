package org.ey.states.portfolio;

import org.ey.enums.PortfolioStatus;
import org.ey.enums.ResolutionEvent;

public class EmptyStatus implements IPortfolioState {

    @Override
    public PortfolioStatus getNextStatus(ResolutionEvent resultEvent) {
        return switch (resultEvent) {
            case EXTREME_RISK -> PortfolioStatus.CLOSED; // Si ocurre EXTREME_RISK, pasa a CLOSED.
            case BULL -> PortfolioStatus.ACTIVE;         // Si ocurre BULL, pasa a ACTIVE.
            case DEBT_DEFAULT -> PortfolioStatus.DEFENSIVE; // Si ocurre DEBT_DEFAULT, pasa a DEFENSIVE.
            default -> PortfolioStatus.EMPTY;            // Otros eventos no cambian el estado.
        };
    }
}
