package org.ey.policies;
import java.util.List;

public class SimplePolicy implements IPolicies {
    private String comparator;
    private String compareTo;
    private List<String> events;

    public SimplePolicy(String comparator, String compareTo, List<String> events) {
        this.comparator = comparator;
        this.compareTo = compareTo;
        this.events = events;
    }

    @Override
    public void displayDetails() {
        System.out.println("SimplePolicy:");
        System.out.println("Comparator: " + comparator);
        System.out.println("CompareTo: " + compareTo);
        System.out.println("Events: " + events);
    }


}

