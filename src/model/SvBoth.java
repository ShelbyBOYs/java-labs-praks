package model;

import java.util.Objects;

public class SvBoth {
    private String name;
    private String version;
    private int instances;

    public SvBoth(String name, String version, int instances) {
        this.name = name;
        this.version = version;
        this.instances = instances;
    }

    public void setVersion(String version) { this.version = version; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SvBoth)) return false;
        SvBoth other = (SvBoth) o;
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