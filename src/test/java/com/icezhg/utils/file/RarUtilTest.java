package com.icezhg.utils.file;

import org.junit.Test;

public class RarUtilTest {
    @Test
    public void unrar() throws Exception {
        RarUtil.unrar("E:\\test\\test.rar", "E:\\tmp\\unrar\\");
    }

}