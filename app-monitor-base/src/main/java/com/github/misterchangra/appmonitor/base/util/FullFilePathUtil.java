package com.github.misterchangra.appmonitor.base.util;

import java.io.File;

public class FullFilePathUtil {
    public static String getProcessBaseDir(String fullPathName) {
        if(null != fullPathName) {
            return null;
        }

        int i = fullPathName.lastIndexOf(File.separator);
        if(i == -1) {
           return null;
        }
        return fullPathName.substring(0, i);
    }

    public static String getProcessFileName(String fullPathName) {
        if(null != fullPathName) {
            return null;
        }

        int i = fullPathName.lastIndexOf(File.separator);

        if(i == -1) {
            return null;
        }

        return fullPathName.substring(i);
    }
}
