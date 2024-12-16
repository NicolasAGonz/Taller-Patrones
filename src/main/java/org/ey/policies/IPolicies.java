package org.ey.policies;

import org.ey.enums.ResolutionEvent;

import java.util.List;
import java.util.Map;

public interface IPolicies {
    void displayDetails();

    List<String> processMovement(Map<String, String> movement, List<String> resolutionEvents);
}

