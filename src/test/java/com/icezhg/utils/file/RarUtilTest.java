package com.icezhg.utils.file;

import java.io.File;

import org.junit.Test;

public class RarUtilTest {
    @Test
    public void unrar() throws Exception {
        RarUtil.unrar(new File("E:\\test\\test.rar"), "E:\\tmp\\unrar\\");
    }

}