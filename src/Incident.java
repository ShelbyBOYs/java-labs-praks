public record Incident(String number, String service, String severity, int durationMin) {

    static Incident parse(String line) {
        String[] p = line.split(";");
        return new Incident(p[0].trim(), p[1].trim(), p[2].trim(), Integer.parseInt(p[3].trim()));
    }

    String describe() {
        return service + ", severity " + severity + ", тривалість " + durationMin + " хв";
    }
}