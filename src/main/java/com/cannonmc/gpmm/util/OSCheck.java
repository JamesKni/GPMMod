package com.cannonmc.gpmm.util;

import java.io.FileReader;

import com.cannonmc.gpmm.config.Config;

public class OSCheck {

    public static String OS;
	public static final String playbackFileWindows = System.getProperty("user.home") + "\\AppData\\Roaming\\Google Play Music Desktop Player\\json_store\\playback.json";
    public static final String playbackFileLinux = System.getProperty("user.home") + "\\.config\\Google Play Music Desktop Player\\json_store\\playback.json";
    public static String playbackFileFallback;
    
    public static void init() {
    	playbackFileFallback = Config.CFfallbackjson;
    	OSCheck.findOS();
    }
	
	public static void findOS() {
    	try {
    		System.out.println("Windows test...");
    		OS = "windows";
    		FileReader testFile = new FileReader(playbackFileWindows);
    		testFile.close();
    	} catch(Exception e) {
    		System.out.println("FAILED");
    		try {
    			System.out.println("Linux test...");
    			OS = "linux";
    			FileReader testFile = new FileReader(playbackFileLinux);
        		testFile.close();
    		}catch (Exception ex) {
    			System.out.println("FAILED - USING FALLBACK PATH");
    			OS = "unknown";
    		}
    	}
    }
	
	public static FileReader getJSONPath() {
		try {
			FileReader file;
			if (OS == "windows") {
				file = new FileReader(playbackFileWindows);
			}else if (OS == "linux") {
				file = new FileReader(playbackFileLinux);
			}else {
				file = new FileReader(playbackFileFallback);
			}
			return file;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
}
