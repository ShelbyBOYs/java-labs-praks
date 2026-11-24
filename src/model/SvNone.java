package model;

public class SvNone {
    private String name;
    private String version;
    private int instances;

    public SvNone(String name, String version, int instances) {
        this.name = name;
        this.version = version;
        this.instances = instances;
    }

    public void setVersion(String version) { this.version = version; }

    @Override
    public String toString() {
        return name + ":" + version + " x" + instances;
    }
}