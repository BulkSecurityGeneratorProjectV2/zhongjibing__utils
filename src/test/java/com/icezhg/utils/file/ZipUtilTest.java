package com.icezhg.utils.file;

import java.io.File;

import org.junit.Test;

public class ZipUtilTest {
    @Test
    public void unzip() throws Exception {
        ZipUtil.unzip(new File("E:\\test\\test.zip"), "E:\\tmp\\unzip\\");
    }

}