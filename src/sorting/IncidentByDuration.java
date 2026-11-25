package sorting;

import model.Incident;

import java.util.Comparator;

public class IncidentByDuration implements Comparator<Incident> {

    @Override
    public int compare(Incident a, Incident b) {
        return Integer.compare(a.getDurationMinutes(), b.getDurationMinutes());
    }
}