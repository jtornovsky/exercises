package accesssentry;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

class Component implements Comparable<Component> {

    public static String COMPONENT_NAME_PREFIX = "component_";
    public static String DEFAULT_COMPONENT_NAME = "*";

    private String componentName;
    private Set<Policy> policies = new TreeSet<>();

    public Component(String componentName) {
        this.componentName = componentName;
    }

    public void addPolicy(int maxHits, int value, TimeUnit unit) {
        Policy policy = new Policy(maxHits, value, unit);
        policies.add(policy);
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public Set<Policy> getPolicies() {
        return policies;
    }

    @Override
    public int compareTo(Component o) {
        return this.componentName.compareTo(o.componentName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Component component = (Component) o;
        return Objects.equals(componentName, component.componentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(componentName);
    }
}
