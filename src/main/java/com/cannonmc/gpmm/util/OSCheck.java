package com.cannonmc.gpmm.util;

import java.io.FileReader;

public class OSCheck {

    public static String OS;
	public static final String playbackFileWindows = System.getProperty("user.home") + "\\AppData\\Roaming\\Google Play Music Desktop Player\\json_store\\playback.json";
    public static final String playbackFileLinux = System.getProperty("user.home") + "/.config/Google Play Music Desktop Player/json_store/playback.json";
    public static final String playbackFileOSX = System.getProperty("user.home") + "/Library/Application Support/Google Play Music Desktop Player/json_store/playback.json";
    
    public static void init() {
    	OSCheck.findOS();
    }
	
	public static void findOS() {
    	try {
    		System.out.println("Windows test...");
    		OS = "windows";
    		System.out.println(playbackFileWindows);
    		FileReader testFile = new FileReader(playbackFileWindows);
    		testFile.close();
    	} catch(Exception e) {
    		System.out.println("FAILED");
    		try {
    			System.out.println(playbackFileLinux);
    			System.out.println("Linux test...");
    			OS = "linux";
    			FileReader testFile = new FileReader(playbackFileLinux);
        		testFile.close();
    		}catch (Exception ex) {
    			System.out.println("FAILED");
    			try {
        			System.out.println(playbackFileOSX);
        			System.out.println("Mac OS test...");
        			OS = "osx";
        			FileReader testFile = new FileReader(playbackFileOSX);
            		testFile.close();
        		}catch (Exception exx) {
        			System.out.println("FAILED - GPMM DISABLED");
        			OS = "unknown";
        		}
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
			}else if (OS == "osx") {
				file = new FileReader(playbackFileOSX);
			}else {
				file = null;
			}
			return file;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
