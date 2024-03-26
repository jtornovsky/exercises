package accesssentry;

import java.time.LocalDateTime;
import java.util.*;

import static accesssentry.Component.DEFAULT_COMPONENT_NAME;

public class AccessSentry {

    static final int MAX_COMPONENTS = 6;
    static final int MAX_ACCESSES = 150000;

    private final DataSeeder dataSeeder = new DataSeeder();
    private final AccessSimulator accessSimulator = new AccessSimulator();
    private final Map<String, Component> componentsMap = dataSeeder.seedData();
    private final Map<String, List<LocalDateTime>> accessLog = accessSimulator.seedAccessToComponents(componentsMap);

    public static void main(String[] args) {
        AccessSentry accessSentry = new AccessSentry();
        accessSentry.runAccessToComponentsFlow();
    }

    void runAccessToComponentsFlow() {
        for (int accessIdx = 0; accessIdx < MAX_ACCESSES; accessIdx++) {
            String accessedComponentName = accessSimulator.generateAccessedComponentName();
            if (!componentsMap.containsKey(accessedComponentName)) {
                accessedComponentName = DEFAULT_COMPONENT_NAME;
            }
            List<LocalDateTime> accessTime = accessLog.getOrDefault(accessedComponentName, new ArrayList<>());
            if (isAccessGranted(componentsMap.get(accessedComponentName), accessTime)) {
                accessTime.add(LocalDateTime.now());
                accessLog.put(accessedComponentName, accessTime);
//                System.out.println("Access granted for component " + accessedComponent.getComponentName());
            } else {
                System.out.println("Access NOT granted for component " + accessedComponentName);
            }

        }
//        printAccessLog();
    }

    boolean isAccessGranted(Component component, List<LocalDateTime> accessTime) {

        Map<Policy, Map<LocalDateTime, Integer>> accessCounts = new TreeMap<>();
        Map<LocalDateTime, Integer> accessCountsPerUnit;

        for (LocalDateTime timestamp : accessTime) {
            // Map to store access counts per time unit
            for (Policy policy : component.getPolicies()) {
                // Truncate the timestamp to the unit specified in the policy
                LocalDateTime truncatedTimestamp = timestamp.truncatedTo(policy.getUnit().toChronoUnit());
                accessCountsPerUnit = accessCounts.getOrDefault(policy, new HashMap<>());
                // Increment the access count for the truncated timestamp
                accessCountsPerUnit.put(truncatedTimestamp, accessCountsPerUnit.getOrDefault(truncatedTimestamp, 0) + 1);
                accessCounts.put(policy, accessCountsPerUnit);
            }
        }

        // Check if access granted based on policies
        for (Policy policy : component.getPolicies()) {
            long maxHits = policy.getMaxHits();
            LocalDateTime now = LocalDateTime.now();
            accessCountsPerUnit = accessCounts.get(policy);

            // Calculate the start time based on the policy's time unit and duration
            LocalDateTime startTime = now.minus(policy.getValue(), policy.getUnit().toChronoUnit());

            // Calculate the total access count for the policy's time unit
            int totalAccessCount = 0;
            for (Map.Entry<LocalDateTime, Integer> entry : accessCountsPerUnit.entrySet()) {
                LocalDateTime entryTime = entry.getKey();
                int accessCount = entry.getValue();

                // Check if the entry falls within the policy's time window
                if (entryTime.isAfter(startTime) && entryTime.isBefore(now)) {
                    totalAccessCount += accessCount;

                    // Check if the total access count exceeds the maximum hits allowed by the policy
                    if (totalAccessCount > maxHits) {
                        System.out.println("Violation of policy: " + policy.getMaxHits() + " hits allowed in " + policy.getValue() + " " + policy.getUnit());
                        return false;
                    }
                }
            }
        }

        // Access is granted if no policy is violated
        return true;
    }

    private void printAccessLog() {
        for (Map.Entry<String, List<LocalDateTime>> entry : accessLog.entrySet()) {
            String componentName = entry.getKey();
            List<LocalDateTime> timestamps = entry.getValue();

            System.out.println("Component: " + componentName);
            System.out.println("Access Log:");

            for (LocalDateTime timestamp : timestamps) {
                System.out.println("  " + timestamp);
            }

            System.out.println();
        }
    }
}


