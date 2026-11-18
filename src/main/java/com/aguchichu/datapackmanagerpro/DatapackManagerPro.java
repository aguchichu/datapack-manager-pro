package com.aguchichu.datapackmanagerpro;

import com.aguchichu.datapackmanagerpro.client.ClientEvents;
import com.aguchichu.datapackmanagerpro.datapack.GlobalDatapackFolder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

@Mod(DatapackManagerPro.MOD_ID)
public final class DatapackManagerPro {
    public static final String MOD_ID = "datapackmanagerpro";
    public static final String MOD_NAME = "Datapack Manager Pro";
    public static final String VERSION = "1.0.0";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final Path GAME_DIRECTORY = FMLPaths.GAMEDIR.get();
    public static final Path GLOBAL_DATAPACKS_DIRECTORY = GAME_DIRECTORY.resolve("global_datapacks");

    public DatapackManagerPro(IEventBus modEventBus) {
        GlobalDatapackFolder.initialize();
        NeoForge.EVENT_BUS.register(new ClientEvents());
        LOGGER.info("{} {} initialized", MOD_NAME, VERSION);
    }
}