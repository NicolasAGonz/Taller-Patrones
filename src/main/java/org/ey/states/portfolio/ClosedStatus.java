package org.ey.states.portfolio;

import org.ey.enums.PortfolioStatus;
import org.ey.enums.ResolutionEvent;

public class ClosedStatus implements IPortfolioState {

    @Override
    public PortfolioStatus getNextStatus(ResolutionEvent resultEvent) {
        return switch (resultEvent) {
            default -> PortfolioStatus.CLOSED;
        };
    }
}
