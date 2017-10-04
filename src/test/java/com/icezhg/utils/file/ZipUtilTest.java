package com.icezhg.utils.file;

import org.junit.Test;

public class ZipUtilTest {
    @Test
    public void unzip() throws Exception {
        ZipUtil.unzip("E:\\test\\test.zip", "E:\\tmp\\unzip\\");
    }

}