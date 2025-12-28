package com.swirlysys.gamepadhotbar;

import com.mojang.blaze3d.platform.InputConstants;
import com.swirlysys.gamepadhotbar.config.GamepadHotbarClientConfig;
import com.swirlysys.gamepadhotbar.handler.ClientEventHandler;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.client.ConfigScreenFactoryRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;

public class GamepadHotbarClient implements ClientModInitializer {
    private static KeyMapping left;
    private static KeyMapping up;
    private static KeyMapping right;
    private static KeyMapping down;
    private static KeyMapping weapon;
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
        ClientEventHandler.register();
        NeoForgeConfigRegistry.INSTANCE.register(GamepadHotbar.MOD_ID, ModConfig.Type.CLIENT, GamepadHotbarClientConfig.SPEC);
        ConfigScreenFactoryRegistry.INSTANCE.register(GamepadHotbar.MOD_ID, ConfigurationScreen::new);

        left = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.gamepadhotbar.cyclehotbar_0",
                InputConstants.Type.KEYSYM, InputConstants.UNKNOWN.getValue(), "category.gamepadhotbar"
        ));
        up = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.gamepadhotbar.cyclehotbar_1",
                InputConstants.Type.KEYSYM, InputConstants.UNKNOWN.getValue(), "category.gamepadhotbar"
        ));
        right = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.gamepadhotbar.cyclehotbar_2",
                InputConstants.Type.KEYSYM, InputConstants.UNKNOWN.getValue(), "category.gamepadhotbar"
        ));
        down = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.gamepadhotbar.cyclehotbar_3",
                InputConstants.Type.KEYSYM, InputConstants.UNKNOWN.getValue(), "category.gamepadhotbar"
        ));
        weapon = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.gamepadhotbar.cyclehotbar_4",
                InputConstants.Type.KEYSYM, InputConstants.UNKNOWN.getValue(), "category.gamepadhotbar"
        ));
	}

    public static KeyMapping getLeft() {
        return left;
    }
    public static KeyMapping getUp() {
        return up;
    }
    public static KeyMapping getRight() {
        return right;
    }
    public static KeyMapping getDown() {
        return down;
    }
    public static KeyMapping getWeapon() {
        return weapon;
    }
}