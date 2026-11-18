package com.aguchichu.datapackmanagerpro.util;

import java.awt.Desktop;
import java.nio.file.Path;

public final class PlatformUtils {
    private PlatformUtils() {}

    public static boolean openFolder(Path folder) {
        try {
            if (!java.nio.file.Files.isDirectory(folder)) java.nio.file.Files.createDirectories(folder);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(folder.toFile());
                return true;
            }
            String os = System.getProperty("os.name", "").toLowerCase();
            ProcessBuilder builder;
            if (os.contains("win")) builder = new ProcessBuilder("explorer", folder.toString());
            else if (os.contains("mac")) builder = new ProcessBuilder("open", folder.toString());
            else builder = new ProcessBuilder("xdg-open", folder.toString());
            builder.start();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}