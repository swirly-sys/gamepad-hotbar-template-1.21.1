package com.swirlysys.gamepadhotbar.handler;

import com.mojang.datafixers.util.Pair;
import com.swirlysys.gamepadhotbar.GamepadHotbar;
import com.swirlysys.gamepadhotbar.GamepadHotbarClient;
import com.swirlysys.gamepadhotbar.config.GamepadHotbarClientConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;

public class ClientEventHandler {
    private static final ResourceLocation HOTBAR_0 = ResourceLocation.fromNamespaceAndPath(GamepadHotbar.MOD_ID, "item/hotbar_0");
    private static final ResourceLocation HOTBAR_1 = ResourceLocation.fromNamespaceAndPath(GamepadHotbar.MOD_ID, "item/hotbar_1");
    private static final ResourceLocation HOTBAR_2 = ResourceLocation.fromNamespaceAndPath(GamepadHotbar.MOD_ID, "item/hotbar_2");
    private static final ResourceLocation HOTBAR_3 = ResourceLocation.fromNamespaceAndPath(GamepadHotbar.MOD_ID, "item/hotbar_3");
    private static final ResourceLocation HOTBAR_4 = ResourceLocation.fromNamespaceAndPath(GamepadHotbar.MOD_ID, "item/hotbar_4");
    private static final ResourceLocation HOTBAR_5 = ResourceLocation.fromNamespaceAndPath(GamepadHotbar.MOD_ID, "item/hotbar_5");
    private static final ResourceLocation HOTBAR_6 = ResourceLocation.fromNamespaceAndPath(GamepadHotbar.MOD_ID, "item/hotbar_6");
    private static final ResourceLocation HOTBAR_7 = ResourceLocation.fromNamespaceAndPath(GamepadHotbar.MOD_ID, "item/hotbar_7");
    private static final ResourceLocation HOTBAR_8 = ResourceLocation.withDefaultNamespace("item/empty_slot_sword");
    private static Pair<ResourceLocation, ResourceLocation> iterateSlotIcons(int index) {
        switch (index) {
            case 1 -> {return Pair.of(InventoryMenu.BLOCK_ATLAS, HOTBAR_1);}
            case 2 -> {return Pair.of(InventoryMenu.BLOCK_ATLAS, HOTBAR_2);}
            case 3 -> {return Pair.of(InventoryMenu.BLOCK_ATLAS, HOTBAR_3);}
            case 4 -> {return Pair.of(InventoryMenu.BLOCK_ATLAS, HOTBAR_4);}
            case 5 -> {return Pair.of(InventoryMenu.BLOCK_ATLAS, HOTBAR_5);}
            case 6 -> {return Pair.of(InventoryMenu.BLOCK_ATLAS, HOTBAR_6);}
            case 7 -> {return Pair.of(InventoryMenu.BLOCK_ATLAS, HOTBAR_7);}
            case 8 -> {return Pair.of(InventoryMenu.BLOCK_ATLAS, HOTBAR_8);}
            default -> {return Pair.of(InventoryMenu.BLOCK_ATLAS, HOTBAR_0);}
        }
    }

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            Player player = Minecraft.getInstance().player;
            if (GamepadHotbarClientConfig.GAMEPAD_HOTBAR_TOGGLE.isFalse() || player == null) return;
            if (player.isSpectator()) return;
            int current = player.getInventory().selected;

            while (GamepadHotbarClient.getLeft().consumeClick())
                player.getInventory().selected = current == 0 ? 1 : 0;
            while (GamepadHotbarClient.getUp().consumeClick())
                player.getInventory().selected = current == 2 ? 3 : 2;
            while (GamepadHotbarClient.getRight().consumeClick())
                player.getInventory().selected = current == 5 ? 4 : 5;
            while (GamepadHotbarClient.getDown().consumeClick())
                player.getInventory().selected = current == 7 ? 6 : 7;
            while (GamepadHotbarClient.getWeapon().consumeClick()) player.getInventory().selected = 8;
        });

        ScreenEvents.AFTER_INIT.register((client, container, scaledX, scaledY) -> {
            if (GamepadHotbarClientConfig.GAMEPAD_HOTBAR_TOGGLE.isFalse()) return;
            ScreenEvents.afterRender(container).register((screen, guiGfx, mouseX, mouseY, tickDelta) -> {

                if (screen instanceof AbstractContainerScreen<?> aScreen) {
                    if (aScreen.getMenu().getCarried().isEmpty()) return;

                    int adjuster = 0;
                    if (aScreen instanceof InventoryScreen) adjuster = 1;
                    if (aScreen instanceof CreativeModeInventoryScreen creativeScreen)
                        if (creativeScreen.isInventoryOpen()) adjuster = 2;

                    guiGfx.pose().pushPose();
                    guiGfx.pose().translate(aScreen.leftPos, aScreen.topPos, 100.0F);
                    int slotCount = aScreen.getMenu().slots.size();

                    for (int i = 0; i < 9; i++) {
                        Slot slot = aScreen.getMenu().getSlot(i + slotCount - 9 - adjuster);
                        if (slot.hasItem()) continue;

                        Pair<ResourceLocation, ResourceLocation> pair = iterateSlotIcons(i);
                        TextureAtlasSprite texAtlas = Minecraft.getInstance().getTextureAtlas(pair.getFirst()).apply(pair.getSecond());
                        guiGfx.blit(slot.x, slot.y, 0, 16, 16, texAtlas);
                    }
                    guiGfx.pose().popPose();
                }
            });
        });
    }
}
