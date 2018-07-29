package com.cannonmc.gpmm.config;

import java.io.File;

import com.cannonmc.gpmm.MusicMod;

import net.minecraftforge.common.config.Configuration;

public class Config {
	public static Configuration config;
    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_WEATHER = "weather";
    
    public static String CFcolour;
    public static boolean CFsprinting;
    public static int CFrequestspeed;
    public static String CFfallbackjson;
    
    public static boolean CFweatherhud;
    public static String CFDARKSKY_API_KEY;
    public static String CFlocation;
    
    
    public static void init(File file) {
        if (Config.config == null) {
            Config.config = new Configuration(file);
        }
        loadConfig();
        Config.config.save();
    }
    
    public static void loadConfig() {
    	config.load();
    	config.addCustomCategoryComment("weather", "Contains options for the weather display settings");
        CFcolour = Config.config.getString("TextColour", CATEGORY_GENERAL, "77E2EA", "Colour of text (HEX)");
        CFsprinting = Config.config.getBoolean("Sprinting", CATEGORY_GENERAL, false, "Start with sprint toggled on");
        CFrequestspeed = Config.config.getInt("RequestSpeed", CATEGORY_GENERAL, 5, 2 , 100, "The Speed at which the mod accesses the playback file");
        CFfallbackjson = Config.config.getString("FallbackJSON", CATEGORY_GENERAL, "/", "Used if the json file is not in one of the usual locations");
        
        CFweatherhud = Config.config.getBoolean("WeatherHUD", CATEGORY_WEATHER, false, "Enable weather HUD");
        CFDARKSKY_API_KEY = Config.config.getString("DARKSKY_API_KEY", CATEGORY_WEATHER, "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", "Put your Dark Sky API key here. https://darksky.net/dev");
        CFlocation = Config.config.getString("Location", CATEGORY_WEATHER, "51.5031729,-0.1873927", "Put your latitude and longitude here");
    }
    
    public static void updateConfig() {
    	System.out.println("Updating...");
    	loadConfig();
    	MusicMod.hexColour = Config.CFcolour;
    	MusicMod.sprinting = Config.CFsprinting;
    	MusicMod.requestCounter = 0;
    }

}

