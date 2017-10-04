package com.icezhg.utils.file;

import java.io.File;

public final class FileUtil {

    private FileUtil() {
    }

    /**
     * Creates the directory named by the specified file abstract pathname,
     * including any necessary but nonexistent parent directories.
     *
     * @param file pathName
     */
    public static void mkdirs(File file) {
        if (file == null) {
            throw new NullPointerException("file should not be null");
        }

        if (!file.exists() && !file.mkdirs()) {
            throw new DirectoryCreateFailedException();
        }
    }

    /**
     * Creates the directory named by the specified pathName, including any
     * necessary but nonexistent parent directories.
     *
     * @param pathName pathName
     */
    public static void mkdirs(String pathName) {
        if (pathName == null) {
            throw new NullPointerException("pathName should not be null");
        }

        mkdirs(new File(pathName));
    }

    /**
     * Append directory name with the system-dependent default name-separator character
     *
     * @param directory directory name
     * @return directory name
     */
    public static String completeDirectoryName(String directory) {
        String path = directory != null ? directory : "/";
        if (!path.endsWith("/") && !path.endsWith("\\")) {
            path += File.separator;
        }
        return path;
    }

}
