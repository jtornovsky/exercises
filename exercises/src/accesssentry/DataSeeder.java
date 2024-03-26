package accesssentry;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

import static accesssentry.AccessSentry.MAX_COMPONENTS;
import static accesssentry.Component.COMPONENT_NAME_PREFIX;
import static accesssentry.Component.DEFAULT_COMPONENT_NAME;

public class DataSeeder {

    private final int SEEDED_COMPONENTS = MAX_COMPONENTS-1;

    Map<String, Component> seedData() {
        Map<String, Component> components = seedComponents(SEEDED_COMPONENTS);
//        printComponents(components);
        return components;
    }

    private void printComponents(Set<Component> components) {
        for (Component component : components) {
            System.out.println("Component Type: " + component.getComponentName());
            System.out.println("Policies:");
            for (Policy policy : component.getPolicies()) {
                System.out.println("  Max Hits: " + policy.getMaxHits() +
                        ", Value: " + policy.getValue() +
                        ", Time Unit: " + policy.getUnit());
            }
            System.out.println();
        }
    }

    private Map<String, Component> seedComponents(int count) {
        Map<String, Component> componentsMap = new TreeMap<>();
        for (int i = 1; i <= count; i++) {
            String componentName = COMPONENT_NAME_PREFIX + i;
            Component component = new Component(componentName);
            seedPolicies(component);
            componentsMap.put(componentName, component);
        }
        // component with type "*" for applicable for all components
        Component defaultComponent = new Component(DEFAULT_COMPONENT_NAME);
        seedPolicies(defaultComponent);
        componentsMap.put(DEFAULT_COMPONENT_NAME, defaultComponent);

        return componentsMap;
    }

    private void seedPolicies(Component component) {
        int numberOfPolicies = ThreadLocalRandom.current().nextInt(0, TimeUnitType.values().length); // Random number of policies (1-4)
        for (int i = 0; i <= numberOfPolicies; i++) {
            int maxHits = Math.round(ThreadLocalRandom.current().nextInt(10, 100)/10)*10;
            TimeUnitType timeUnitType = getRandomUnit();
            int value = ThreadLocalRandom.current().nextInt(1, timeUnitType.getUpperBound());
            component.addPolicy(maxHits, value, timeUnitType.getTimeUnit());
        }
    }

    private static TimeUnitType getRandomUnit() {
        TimeUnitType values[] = TimeUnitType.values();
        return values[ThreadLocalRandom.current().nextInt(values.length)];
    }
}
