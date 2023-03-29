package dk.sdu.sesem4.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class SPILocator {
    /**
     * List of currently cached services
     */
    @SuppressWarnings("rawtypes")
    private static final Map<Class, ServiceLoader> services = new HashMap<Class, ServiceLoader>();

    private SPILocator() {
    }

    /**
     * Locate all instances of the provided class through ServiceLoader
     *
     * @param service Class of service to find
     * @return List of instances, with the provided class type
     * @param <T> Provided class type
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> locateAll(Class<T> service) {
        // Get cached serviceLoader
        ServiceLoader<T> loader;
        try {
            loader = services.get(service);
        } catch (ClassCastException|NullPointerException exception) {
            // If error due occur, print the stack trace for debugging but continue
            exception.printStackTrace();
            loader = null;
        }

        boolean printStatement = false;

        // Retrieve serviceLoader for provided class, if not cached
        if (loader == null) {
            loader = ServiceLoader.load(service);
            services.put(service, loader);
            printStatement = true;
        }

        // Create list for all the instances found
        List<T> instances = new ArrayList<T>();

        try {
            // Add each instance to instances list
            for (T instance : loader) {
                instances.add(instance);
            }
        } catch (ServiceConfigurationError serviceError) {
            // If error due occur, print the stack trace for debugging but continue
            serviceError.printStackTrace();
        }

        // Print found count, if any new instances has been found in the locating process
        if (printStatement) {
            System.out.println("Found " + instances.size() + " implementations for interface: " + service.getName());
        }

        // Return the instances found
        return instances;
    }
}
