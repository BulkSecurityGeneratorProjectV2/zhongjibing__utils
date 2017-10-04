package com.icezhg.utils.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RarUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RarUtil.class);

    private static final String RAR_EXPANDED_NAME = ".rar";

    private static void assertRarFile(File file) throws IOException {
        if (!file.getName().toLowerCase().endsWith(RAR_EXPANDED_NAME)) {
            throw new IOException("not rar archive");
        }
    }

    public static void unrar(String filePath, String targetDirectory) throws IOException {
        File archiveFile = new File(filePath);

        assertRarFile(archiveFile);

        try (Archive archive = new Archive(archiveFile)) {
            FileHeader fileHeader;
            while ((fileHeader = archive.nextFileHeader()) != null) {
                String fileName = fileHeader.getFileNameW().isEmpty() ? fileHeader.getFileNameString() : fileHeader.getFileNameW();
                File file = new File(FileUtil.completeDirectoryName(targetDirectory) + fileName);
                if (fileHeader.isDirectory()) {
                    FileUtil.mkdirs(file);
                } else {
                    FileUtil.mkdirs(file.getParentFile());
                    try (OutputStream os = new FileOutputStream(file)) {
                        archive.extractFile(fileHeader, os);
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage(), e);
                        throw e;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new IOException(e);
        }
    }

}
