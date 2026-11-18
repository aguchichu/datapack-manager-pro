package com.aguchichu.datapackmanagerpro.datapack;

import java.nio.file.Path;

public record DatapackInfo(String name, String description, Path source, boolean directory) {}