package com.icezhg.utils.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ZipUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZipUtil.class);

    private static final String ZIP_EXPANDED_NAME = ".zip";
    private static final int BUFFER_SIZE = 1024;
    private static final int EOF = -1;
    private static final Charset CHARSET_GBK = Charset.forName("GBK");

    private ZipUtil() {
    }

    private static void assertZipFile(File file) throws IOException {
        if (!file.getName().toLowerCase().endsWith(ZIP_EXPANDED_NAME)) {
            throw new IOException("not zip archive");
        }
    }

    public static void unzip(File archive, String targetDirectory) throws IOException {

        assertZipFile(archive);

        try (ZipFile zipFile = new ZipFile(archive, CHARSET_GBK)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                File file = new File(FileUtil.completeDirectoryName(targetDirectory), zipEntry.getName());
                if (!file.toPath().normalize().startsWith(FileUtil.completeDirectoryName(targetDirectory))) {
                    throw new RuntimeException("Bad zip entry");
                }

                if (zipEntry.isDirectory()) {
                    FileUtil.mkdirs(file);
                } else {
                    FileUtil.mkdirs(file.getParentFile());

                    try (InputStream is = zipFile.getInputStream(zipEntry);
                         OutputStream os = new FileOutputStream(file)) {
                        byte[] bytes = new byte[BUFFER_SIZE];
                        int read;
                        while ((read = is.read(bytes)) != EOF) {
                            os.write(bytes, 0, read);
                        }
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage(), e);
                        throw e;
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

}
