package model;

import storage.Keyed;

import java.util.Objects;

public class Incident implements Keyed<String> {

    private final String number;
    private final String serviceName;
    private final int durationMinutes;

    public Incident(String number, String serviceName, int durationMinutes) {
        this.number = number;
        this.serviceName = serviceName;
        this.durationMinutes = durationMinutes;
    }

    public String getNumber() { return number; }
    public String getServiceName() { return serviceName; }
    public int getDurationMinutes() { return durationMinutes; }

    @Override
    public String getKey() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Incident)) return false;
        Incident other = (Incident) o;
        return Objects.equals(number, other.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return number + " " + serviceName + ", " + durationMinutes + " хв";
    }
}