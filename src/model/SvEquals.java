package model;

import java.util.Objects;

public class SvEquals {
    private String name;
    private String version;
    private int instances;

    public SvEquals(String name, String version, int instances) {
        this.name = name;
        this.version = version;
        this.instances = instances;
    }

    public void setVersion(String version) { this.version = version; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SvEquals)) return false;
        SvEquals other = (SvEquals) o;
        return Objects.equals(name, other.name) && Objects.equals(version, other.version);
    }

    @Override
    public String toString() {
        return name + ":" + version + " x" + instances;
    }
}