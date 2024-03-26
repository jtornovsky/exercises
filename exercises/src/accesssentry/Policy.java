package accesssentry;

import java.util.concurrent.TimeUnit;

// <3d : 1000> , <24h : 100> , <10m : 20>, <20s : 1>
class Policy implements Comparable<Policy> {
    private final int maxHits;
    private final int value;
    private final TimeUnit unit;

    public Policy(int maxHits, int value, TimeUnit unit) {
        this.maxHits = maxHits;
        this.value = value;
        this.unit = unit;
    }

    public int getMaxHits() {
        return maxHits;
    }

    public int getValue() {
        return value;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    @Override
    public int compareTo(Policy otherPolicy) {
        // comparable.Compare policies based on their time units
        return this.unit.compareTo(otherPolicy.unit);
    }
}
