package com.aguchichu.datapackmanagerpro.datapack;

import com.aguchichu.datapackmanagerpro.DatapackManagerPro;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class DatapackScanner {
    private DatapackScanner() {}

    public static List<DatapackInfo> scan() {
        List<DatapackInfo> result = new ArrayList<>();
        Path root = GlobalDatapackFolder.getPath();
        try {
            Files.createDirectories(root);
            try (var paths = Files.list(root)) {
                paths.filter(p -> Files.isDirectory(p) ||
                                (Files.isRegularFile(p) && p.getFileName().toString().toLowerCase().endsWith(".zip")))
                     .sorted(Comparator.comparing(p -> p.getFileName().toString().toLowerCase()))
                     .forEach(p -> {
                         try {
                             Metadata metadata = readMetadata(p);
                             result.add(new DatapackInfo(metadata.name, metadata.description, p, Files.isDirectory(p)));
                         } catch (Exception e) {
                             DatapackManagerPro.LOGGER.warn("Skipping invalid datapack {}", p, e);
                         }
                     });
            }
        } catch (Exception e) {
            DatapackManagerPro.LOGGER.error("Could not scan global datapacks", e);
        }
        return result;
    }

    private static Metadata readMetadata(Path path) throws Exception {
        String json;
        if (Files.isDirectory(path)) {
            Path meta = path.resolve("pack.mcmeta");
            if (!Files.isRegularFile(meta)) throw new IllegalArgumentException("Missing pack.mcmeta");
            json = Files.readString(meta, StandardCharsets.UTF_8);
        } else {
            try (ZipFile zip = new ZipFile(path.toFile())) {
                ZipEntry entry = zip.getEntry("pack.mcmeta");
                if (entry == null) throw new IllegalArgumentException("Missing pack.mcmeta");
                try (InputStream in = zip.getInputStream(entry)) {
                    json = new String(in.readAllBytes(), StandardCharsets.UTF_8);
                }
            }
        }
        JsonObject root = JsonParser.parseString(json).getAsJsonObject();
        JsonObject pack = root.getAsJsonObject("pack");
        if (pack == null) throw new IllegalArgumentException("Missing pack object");
        String description = pack.has("description") ? pack.get("description").getAsString() : "Sin descripción";
        String name = path.getFileName().toString();
        return new Metadata(name, description);
    }

    private record Metadata(String name, String description) {}
}