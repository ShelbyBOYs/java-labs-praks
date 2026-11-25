package model;

import storage.Keyed;

import java.util.Objects;

public class Service implements Keyed<String> {

    private final String name;
    private final String version;
    private final int instances;

    public Service(String name, String version, int instances) {
        this.name = name;
        this.version = version;
        this.instances = instances;
    }

    public String getName() { return name; }
    public String getVersion() { return version; }
    public int getInstances() { return instances; }

    @Override
    public String getKey() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Service)) return false;
        Service other = (Service) o;
        return Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name + " v" + version + ", інстансів: " + instances;
    }
}