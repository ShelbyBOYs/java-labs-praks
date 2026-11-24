  class MicroserviceVersion {
    private final String name;
    private final String version;
    private final int instances;

    public MicroserviceVersion(String name, String version, int instances) {
        this.name = name;
        this.version = version;
        this.instances = instances;
    }

    public String getName() { return name; }
    public String getVersion() { return version; }
    public int getInstances() { return instances; }

    @Override
    public String toString() {
        return name + ":" + version + " (" + instances + ")";
    }
}