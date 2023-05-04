package dk.sdu.sesem4.common.data.resource;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Get resource from system.
 *
 * @author The0Mikkel & MGertz
 */
public class Resource {
    /** Temporary file name prefix. */
    private static final String tmpFilePrefix = "Nelda";
    /** Temporary file format. */
    private static final String tmpFileFormat = "tmp";
    /** Instance of the resource system. */
    private static Resource instance;
    /** Map of path UUIDs based on class and path. */
    private Map<Class<?>, Map<String, UUID>> pathUUID;
    /** File cache, to prevent duplicate temporary files. */
    private Map<UUID, File> fileCache;

    private Resource() {
        this.pathUUID = new HashMap<>();
        this.fileCache = new HashMap<>();
    }

    /**
     * Get resource instance.
     *
     * @return Resource instance.
     */
    public static Resource getInstance() {
        if (Resource.instance == null) {
            Resource.instance = new Resource();
        }
        return Resource.instance;
    }

    /**
     * Invalidate the current cache, and delete all current files used by the
     * system.
     *
     * @throws IOException Can be thrown by the deletion process.
     */
    public void invalidateCache() throws IOException {
        this.pathUUID = new HashMap<>();

        this.fileCache.forEach((uuid, file) -> file.delete());
        this.fileCache = new HashMap<>();
    }

    /**
     * Delete specific cached file
     *
     * @param file File object to delete
     */
    public void deleteFile(File file) {
        this.fileCache.values().remove(file);
    }

    /**
     * Get a resource file as a File.
     *
     * @param resourceClass Class which represents where the file is located
     * @param path          Path for the resource
     *
     * @return File that has been retrieved from resources
     */
    public File getResource(Class<?> resourceClass, String path) {
        // Ensure valid values
        if (resourceClass == null || path == null) {
            return null;
        }

        // Get cached file
        File cachedFile = getCachedFile(resourceClass, path);
        if (cachedFile != null) {
            return cachedFile;
        }

        // Generate tmp file
        UUID uuid = generatePathUUID(resourceClass, path);
        File file = generateTmpFile(resourceClass, path, uuid);
        if (file == null) {
            return null;
        }
        cacheFile(uuid, file);

        return file;
    }

    /**
     * Check if a resource exists. This does not check cache.
     *
     * @param resourceClass Class which represents where the file is located
     * @param path          Path for the resource
     *
     * @return Boolean representing if the resource actually exists. True indicates
     *         he resource is locatable.
     */
    public static boolean exists(Class<?> resourceClass, String path) {
        if (resourceClass == null || path == null) {
            return false;
        }

        URL url = getResourceAsURL(resourceClass, path);
        return url != null;
    }

    /**
     * Get cached file
     *
     * @param resourceClass Class which represents where the file is located
     * @param path          Path for the resource
     *
     * @return Cached file or null if no cached file exist
     */
    private File getCachedFile(Class<?> resourceClass, String path) {
        return this.getCachedFile(this.getPathUUID(resourceClass, path));
    }

    /**
     * Get cached file
     *
     * @param uuid UUID representing the cached file
     *
     * @return Cached file or null if no cached file exist
     */
    private File getCachedFile(UUID uuid) {
        if (!this.fileCache.containsKey(uuid)) {
            return null;
        }

        return this.fileCache.get(uuid);
    }

    /**
     * Cache file
     *
     * @param uuid Representing file
     * @param file File to cache
     */
    private void cacheFile(UUID uuid, File file) {
        this.fileCache.put(uuid, file);
    }

    /**
     * Get path UUID, based resource class and path
     *
     * @param resourceClass Class which represents where the file is located
     * @param path          Path for the resource
     *
     * @return UUID representing the unique path
     */
    private UUID getPathUUID(Class<?> resourceClass, String path) {
        if (!this.pathUUID.containsKey(resourceClass)) {
            return null;
        }

        Map<String, UUID> resourceClassUUIDs = this.pathUUID.get(resourceClass);
        if (!resourceClassUUIDs.containsKey(path)) {
            return null;
        }

        return resourceClassUUIDs.get(path);
    }

    /**
     * Generate path UUID, based resource class and path
     *
     * @param resourceClass Class which represents where the file is located
     * @param path          Path for the resource
     *
     * @return UUID representing the unique path
     */
    private UUID generatePathUUID(Class<?> resourceClass, String path) {
        UUID existingUUID = getPathUUID(resourceClass, path);
        if (existingUUID != null) {
            return existingUUID;
        }

        UUID uuid = UUID.randomUUID();
        storePathUUID(resourceClass, path, uuid);
        return uuid;
    }

    /**
     * Store path UUID
     *
     * @param resourceClass Class which represents where the file is located
     * @param path          Path for the resource
     * @param uuid          The UUID for the resource and path combined
     */
    private void storePathUUID(Class<?> resourceClass, String path, UUID uuid) {
        if (!this.pathUUID.containsKey(resourceClass)) {
            this.pathUUID.put(resourceClass, new HashMap<>());
        }

        Map<String, UUID> resourceClassUUIDs = this.pathUUID.get(resourceClass);
        if (!resourceClassUUIDs.containsKey(path)) {
            resourceClassUUIDs.put(path, uuid);
        }
    }

    /**
     * Generate a temporary file that is a clone of the resource
     *
     * @param resourceClass Class which represents where the file is located
     * @param path          Path for the resource
     * @param uuid          The UUID for the resource and path combined
     *
     * @return Temporary file, that can be used as a replacement for the actual
     *         resource file
     */
    private File generateTmpFile(Class<?> resourceClass, String path, UUID uuid) {
        // Get image data
        InputStream input = getResourceStream(resourceClass, path);
        if (input == null) {
            return null;
        }

        File tmpFile;
        try {
            // Load file in
            byte[] buffer = input.readAllBytes();
            // Save file in the current directory
            String tmpdir = System.getProperty("java.io.tmpdir");
            tmpFile = new File(tmpdir + "/" + tmpFilePrefix + "-" + uuid + "." + tmpFileFormat);
            tmpFile.deleteOnExit(); // Auto delete file when game closes
            OutputStream outStream = new FileOutputStream(tmpFile);
            outStream.write(buffer);
        } catch (IOException ioException) {
            return null;
        }

        return tmpFile;
    }

    /**
     * Get resource as URL
     *
     * @param resourceClass Class which represents where the file is located
     * @param path          Path for the resource
     *
     * @return URL representing the resource
     */
    private static URL getResourceAsURL(Class<?> resourceClass, String path) {
        return resourceClass.getResource(path);
    }

    /**
     * Get resource as stream
     *
     * @param resourceClass Class which represents where the file is located
     * @param path          Path for the resource
     *
     * @return URL representing the resource
     */
    private static InputStream getResourceStream(Class<?> resourceClass, String path) {
        return resourceClass.getResourceAsStream(path);
    }
}
