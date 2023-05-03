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
	/** Resource file prefix. */
    private static final String resourcePrefix = "/";
	/** Temporary file name prefix. */
    private static final String tmpFilePrefix = "Nelda";
	/** Temporary file format. */
    private static final String tmpFileFormat = "tmp";
	/** Instance of the resource system. */
    private static Resource instance;
	/** Map of path UUIDs based on class and path. */
    private Map<Class<?>, Map<Path, UUID>> pathUUID;
	/** File cache, to prevent duplicate temporary files. */
    private Map<UUID, File> fileCache;

    private Resource() {
        this.pathUUID = new HashMap<>();
        this.fileCache = new HashMap<>();
    }

	/**
	 * Get ressource instance.
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
	 * Invalidate the current cache, and delete all current files used by the system.
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
	 * Get a ressource file as a File.
	 *
	 * @param ressourceClass Class which represents where the file is located
	 * @param path Path for the ressource
	 *
	 * @return File that has been retrieved from ressources
	 */
    public File getRessource(Class<?> ressourceClass, Path path) {
        // Ensure valid values
        if (ressourceClass == null || path == null) {
            return null;
        }

		// Get cached file
        File cachedFile = getCachedFile(ressourceClass, path);
        if (cachedFile != null) {
            return cachedFile;
        }

		// Generate tmp file
        UUID uuid = generatePathUUID(ressourceClass, path);
        File file = generateTmpFile(ressourceClass, path, uuid);
        if (file == null) {
            return null;
        }
		cacheFile(uuid, file);

        return file;
    }

	/**
	 * Check if a ressource exists. This does not check cache.
	 *
	 * @param ressourceClass Class which represents where the file is located
	 * @param path Path for the ressource
	 *
	 * @return Boolean representing if the ressource actually exists. True indicates the ressource is locatable.
	 */
    public static boolean exists(Class<?> ressourceClass, Path path) {
        if (ressourceClass == null || path == null) {
            return false;
        }

        URL url = getRessourceAsURL(ressourceClass, path);
        return url != null;
    }

	/**
	 * Get cached file
	 *
	 * @param ressourceClass Class which represents where the file is located
	 * @param path Path for the ressource
	 *
	 * @return Cached file or null if no cached file exist
	 */
    private File getCachedFile(Class<?> ressourceClass, Path path) {
        return this.getCachedFile(this.getPathUUID(ressourceClass, path));
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
	 * @param ressourceClass Class which represents where the file is located
	 * @param path Path for the ressource
	 *
	 * @return UUID representing the unique path
	 */
    private UUID getPathUUID(Class<?> ressourceClass, Path path) {
        if (!this.pathUUID.containsKey(ressourceClass)) {
            return null;
        }

        Map<Path, UUID> ressourceClassUUIDs = this.pathUUID.get(ressourceClass);
        if (!ressourceClassUUIDs.containsKey(path)) {
            return null;
        }

        return ressourceClassUUIDs.get(path);
    }

	/**
	 * Generate path UUID, based resource class and path
	 *
	 * @param ressourceClass Class which represents where the file is located
	 * @param path Path for the ressource
	 *
	 * @return UUID representing the unique path
	 */
    private UUID generatePathUUID(Class<?> ressourceClass, Path path) {
        UUID existingUUID = getPathUUID(ressourceClass, path);
        if (existingUUID != null) {
            return existingUUID;
        }

        UUID uuid = UUID.randomUUID();
        storePathUUID(ressourceClass, path, uuid);
        return uuid;
    }

	/**
	 * Store path UUID
	 *
	 * @param ressourceClass Class which represents where the file is located
	 * @param path Path for the ressource
	 * @param uuid The UUID for the ressource and path combined
	 */
    private void storePathUUID(Class<?> ressourceClass, Path path, UUID uuid) {
        if (!this.pathUUID.containsKey(ressourceClass)) {
            this.pathUUID.put(ressourceClass, new HashMap<>());
        }

        Map<Path, UUID> ressourceClassUUIDs = this.pathUUID.get(ressourceClass);
        if (!ressourceClassUUIDs.containsKey(path)) {
            ressourceClassUUIDs.put(path, uuid);
        }
    }

	/**
	 * Generate a temporary file that is a clone of the ressource
	 *
	 * @param ressourceClass Class which represents where the file is located
	 * @param path Path for the ressource
	 * @param uuid The UUID for the ressource and path combined
	 *
	 * @return Temporary file, that can be used as a replacement for the actual ressource file
	 */
    private File generateTmpFile(Class<?> ressourceClass, Path path, UUID uuid) {
        // Get image data
        InputStream input = getRessourceStream(ressourceClass, path);
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
	 * Get ressource as URL
	 *
	 * @param ressourceClass Class which represents where the file is located
	 * @param path Path for the ressource
	 *
	 * @return URL representing the ressource
	 */
    private static URL getRessourceAsURL(Class<?> ressourceClass, Path path) {
        return ressourceClass.getResource((resourcePrefix + path));
    }

	/**
	 * Get ressource as stream
	 *
	 * @param ressourceClass Class which represents where the file is located
	 * @param path Path for the ressource
	 *
	 * @return URL representing the ressource
	 */
    private static InputStream getRessourceStream(Class<?> ressourceClass, Path path) {
        return ressourceClass.getResourceAsStream((resourcePrefix + path));
    }
}
