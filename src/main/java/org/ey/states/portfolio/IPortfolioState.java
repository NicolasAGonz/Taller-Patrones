package org.ey.states.portfolio;

import org.ey.enums.PortfolioStatus;
import org.ey.enums.ResolutionEvent;

public interface IPortfolioState {
    PortfolioStatus getNextStatus(ResolutionEvent resultEvent);
}
