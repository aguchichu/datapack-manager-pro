package com.aguchichu.datapackmanagerpro.datapack;

import com.aguchichu.datapackmanagerpro.DatapackManagerPro;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class GlobalDatapackFolder {
    private GlobalDatapackFolder() {}

    public static void initialize() {
        try {
            Files.createDirectories(DatapackManagerPro.GLOBAL_DATAPACKS_DIRECTORY);
            DatapackManagerPro.LOGGER.info("Global datapacks folder: {}",
                    DatapackManagerPro.GLOBAL_DATAPACKS_DIRECTORY.toAbsolutePath());
        } catch (IOException e) {
            DatapackManagerPro.LOGGER.error("Could not create global_datapacks", e);
        }
    }

    public static Path getPath() {
        return DatapackManagerPro.GLOBAL_DATAPACKS_DIRECTORY;
    }
}