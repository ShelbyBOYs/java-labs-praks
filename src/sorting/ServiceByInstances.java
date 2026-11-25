package sorting;

import model.Service;

import java.util.Comparator;

public class ServiceByInstances implements Comparator<Service> {

    @Override
    public int compare(Service a, Service b) {
        return Integer.compare(a.getInstances(), b.getInstances());
    }
}