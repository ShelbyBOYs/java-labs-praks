package model;

import java.util.Objects;

public class ServiceVersion {
    private String name;
    private String version;
    private int instances;

    public ServiceVersion(String name, String version, int instances) {
        this.name = name;
        this.version = version;
        this.instances = instances;
    }

    public String getName() { return name; }
    public String getVersion() { return version; }
    public int getInstances() { return instances; }
    public void setVersion(String version) { this.version = version; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceVersion)) return false;
        ServiceVersion other = (ServiceVersion) o;
        return Objects.equals(name, other.name) && Objects.equals(version, other.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, version);
    }

    @Override
    public String toString() {
        return name + ":" + version + " x" + instances;
    }
}