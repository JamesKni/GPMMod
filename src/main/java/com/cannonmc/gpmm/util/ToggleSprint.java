package com.cannonmc.gpmm.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class ToggleSprint {
	
    public static boolean sprinting;
	final static int keySprint = Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode();
	
    public static void sprintToggle() {
    	sprinting = !sprinting;
    }
    
    public static void sprintSetter() {
    	if (sprinting) {
    		KeyBinding.setKeyBindState(keySprint, true);
    	}else {
    		KeyBinding.setKeyBindState(keySprint, false);
    	}
    }

}
