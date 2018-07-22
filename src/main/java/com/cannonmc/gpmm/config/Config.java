package com.cannonmc.gpmm.config;

import java.io.File;

import com.cannonmc.gpmm.MusicMod;

import net.minecraftforge.common.config.Configuration;

public class Config {
	public static Configuration config;
    public static final String CATEGORY_GENERAL = "general";
    
    public static String CFtextColour;
    public static String CFplaybarColour;
    
    public static void init(File file) {
        if (Config.config == null) {
            Config.config = new Configuration(file);
        }
        loadConfig();
        Config.config.save();
    }
    
    public static void loadConfig() {
    	config.load();
        CFtextColour = Config.config.getString("TextColour", "general", "77E2EA", "Colour of text (HEX)");
    }
    
    public static void updateConfig() {
    	System.out.println("Updating...");
    	loadConfig();
    	MusicMod.hexColour = Config.CFtextColour;
    }

}

