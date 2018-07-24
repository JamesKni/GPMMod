package com.cannonmc.gpmm.config;

import java.io.File;

import com.cannonmc.gpmm.MusicMod;

import net.minecraftforge.common.config.Configuration;

public class Config {
	public static Configuration config;
    public static final String CATEGORY_GENERAL = "general";
    
    public static String CFcolour;
    public static boolean CFsprinting;
    public static boolean CFupdatenotifications;
    public static int CFrequestspeed;
    
    public static void init(File file) {
        if (Config.config == null) {
            Config.config = new Configuration(file);
        }
        loadConfig();
        Config.config.save();
    }
    
    public static void loadConfig() {
    	config.load();
        CFcolour = Config.config.getString("TextColour", CATEGORY_GENERAL, "77E2EA", "Colour of text (HEX)");
        CFsprinting = Config.config.getBoolean("Sprinting", CATEGORY_GENERAL, false, "Start with sprint toggled on");
        CFupdatenotifications = Config.config.getBoolean("UpdateNotifications", CATEGORY_GENERAL, true, "Be notified if a new version is avalible");
        CFrequestspeed = Config.config.getInt("RequestSpeed", CATEGORY_GENERAL, 5, 2 , 100, "The Speed at which the mod accesses the playback file");
        
    }
    
    public static void updateConfig() {
    	System.out.println("Updating...");
    	loadConfig();
    	MusicMod.hexColour = Config.CFcolour;
    	MusicMod.sprinting = Config.CFsprinting;
    	MusicMod.requestCounter = 0;
    }

}

