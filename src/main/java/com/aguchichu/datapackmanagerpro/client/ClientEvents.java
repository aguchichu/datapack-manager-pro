package com.aguchichu.datapackmanagerpro.client;

import com.aguchichu.datapackmanagerpro.client.screen.DatapackManagerScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.ScreenEvent;

public final class ClientEvents {
    @net.neoforged.bus.api.SubscribeEvent
    public void onScreenInit(ScreenEvent.Init.Post event) {
        if (net.neoforged.fml.loading.FMLEnvironment.dist != Dist.CLIENT) return;
        Screen screen = event.getScreen();
        if (screen instanceof DatapackManagerScreen) return;
        if (screen.width < 220 || screen.height < 180) return;

        int x = screen.width / 2 + 105;
        int y = screen.height - 28;
        event.addListener(Button.builder(Component.translatable("screen.datapackmanagerpro.title"),
                button -> Minecraft.getInstance().setScreen(new DatapackManagerScreen(screen)))
                .bounds(x, y, 110, 20).build());
    }
}