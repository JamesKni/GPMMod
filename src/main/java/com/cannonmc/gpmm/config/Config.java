package com.cannonmc.gpmm.config;

import java.io.File;

import com.cannonmc.gpmm.MusicMod;
import com.cannonmc.gpmm.util.ToggleSprint;
import com.cannonmc.gpmm.weather.UpdateWeatherThread;
import com.cannonmc.gpmm.weather.WeatherGetter;

import net.minecraftforge.common.config.Configuration;

public class Config {
	public static Configuration config;
    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_WEATHER = "weather";
    public static final String CATEGORY_TIME = "time";
    
    public static String CFcolour;
    public static boolean CFsprinting;
    public static int CFrequestspeed;
    public static String CFfallbackjson;
    public static boolean CFshowalbumart;
    
    public static boolean CFweatherhud;
    public static String CFDARKSKY_API_KEY;
    public static String CFlocation;
    
    public static boolean CFtimehud;
    
    
    public static void init(File file) {
        if (Config.config == null) {
            Config.config = new Configuration(file);
        }
        loadConfig();
        Config.config.save();
    }
    
    public static void loadConfig() {
    	config.load();
    	config.addCustomCategoryComment(CATEGORY_WEATHER, "Contains options for the weather HUD");
    	config.addCustomCategoryComment(CATEGORY_TIME, "Contains options for the time HUD");
        CFcolour = Config.config.getString("TextColour", CATEGORY_GENERAL, "77E2EA", "Text Colour(HEX)");
        CFsprinting = Config.config.getBoolean("Sprinting", CATEGORY_GENERAL, false, "Start with sprint toggled on");
        CFrequestspeed = Config.config.getInt("RequestSpeed", CATEGORY_GENERAL, 5, 2 , 100, "Playback file access limiter");
        CFshowalbumart = Config.config.getBoolean("ShowAlbumArt", CATEGORY_GENERAL, true, "Very very experimental!");
        
        CFweatherhud = Config.config.getBoolean("WeatherHUD", CATEGORY_WEATHER, false, "Enable weather HUD");
        CFDARKSKY_API_KEY = Config.config.getString("DARKSKY_API_KEY", CATEGORY_WEATHER, "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", "Put your Dark Sky API key here. https://darksky.net/dev");
        CFlocation = Config.config.getString("Location", CATEGORY_WEATHER, "51.5031729,-0.1873927", "Put your latitude and longitude here");
        
        CFtimehud = Config.config.getBoolean("TimeHUD", CATEGORY_TIME, false, "Enable time HUD");

    }
    
    public static void updateConfig() {
    	System.out.println("Updating...");
    	loadConfig();
    	setVars();
    	MusicMod.THREAD_POOL.submit(new UpdateWeatherThread());
    }
    
    public static void setVars() {
    	MusicMod.hexColour = Config.CFcolour;
    	ToggleSprint.sprinting = Config.CFsprinting;
    	MusicMod.requestCounter = 0;
    }

}

