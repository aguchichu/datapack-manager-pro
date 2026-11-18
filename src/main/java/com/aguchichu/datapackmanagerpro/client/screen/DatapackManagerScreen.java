package com.aguchichu.datapackmanagerpro.client.screen;

import com.aguchichu.datapackmanagerpro.datapack.DatapackInfo;
import com.aguchichu.datapackmanagerpro.datapack.DatapackScanner;
import com.aguchichu.datapackmanagerpro.datapack.GlobalDatapackFolder;
import com.aguchichu.datapackmanagerpro.util.PlatformUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import java.util.List;

public final class DatapackManagerScreen extends Screen {
    private final Screen parent;
    private List<DatapackInfo> datapacks = List.of();
    private int scroll;
    private String status = "";

    public DatapackManagerScreen(Screen parent) {
        super(Component.translatable("screen.datapackmanagerpro.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        refresh();
        int buttonY = this.height - 28;
        addRenderableWidget(Button.builder(Component.translatable("screen.datapackmanagerpro.refresh"),
                b -> refresh()).bounds(this.width / 2 - 154, buttonY, 100, 20).build());
        addRenderableWidget(Button.builder(Component.translatable("screen.datapackmanagerpro.open_folder"),
                b -> {
                    boolean opened = PlatformUtils.openFolder(GlobalDatapackFolder.getPath());
                    status = opened ? GlobalDatapackFolder.getPath().toString()
                            : Component.translatable("screen.datapackmanagerpro.folder_error").getString();
                }).bounds(this.width / 2 - 50, buttonY, 100, 20).build());
        addRenderableWidget(Button.builder(Component.translatable("screen.datapackmanagerpro.close"),
                b -> onClose()).bounds(this.width / 2 + 54, buttonY, 100, 20).build());
    }

    private void refresh() {
        datapacks = DatapackScanner.scan();
        scroll = 0;
        status = Component.translatable("screen.datapackmanagerpro.count", datapacks.size()).getString();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics, mouseX, mouseY, partialTick);
        graphics.drawCenteredString(font, title, width / 2, 18, 0xFFFFFF);
        graphics.drawCenteredString(font, status, width / 2, 34, 0xAAAAAA);
        int top = 56;
        int bottom = height - 38;
        int rowHeight = 34;
        if (datapacks.isEmpty()) {
            graphics.drawCenteredString(font, Component.translatable("screen.datapackmanagerpro.empty"),
                    width / 2, height / 2 - 5, 0xBBBBBB);
        } else {
            int visible = Math.max(1, (bottom - top) / rowHeight);
            int end = Math.min(datapacks.size(), scroll + visible);
            for (int i = scroll; i < end; i++) {
                DatapackInfo info = datapacks.get(i);
                int y = top + (i - scroll) * rowHeight;
                int color = (i % 2 == 0) ? 0x80202020 : 0x80404040;
                graphics.fill(width / 2 - 190, y - 3, width / 2 + 190, y + 29, color);
                graphics.drawString(font, info.name(), width / 2 - 180, y, 0xFFFFFF);
                String description = info.description();
                if (description.length() > 76) description = description.substring(0, 73) + "...";
                graphics.drawString(font, description, width / 2 - 180, y + 12, 0xAAAAAA);
            }
            graphics.drawString(font, "Rueda del mouse: desplazarse", width / 2 - 190, bottom + 2, 0x777777);
        }
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (scrollY < 0 && scroll < Math.max(0, datapacks.size() - 1)) scroll++;
        if (scrollY > 0 && scroll > 0) scroll--;
        return true;
    }

    @Override
    public void onClose() {
        minecraft.setScreen(parent);
    }
}