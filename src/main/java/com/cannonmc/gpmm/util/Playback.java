package com.cannonmc.gpmm.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Playback {
	
	public static String title;
	public static String artist;
	public static boolean songLiked;
    public static boolean songDisliked;
    public static boolean songPlaying;
    public static String totalTime;
    public static String currentTime;
    
    public static String albumArt;
    
	public static void update() {
    	JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(OSCheck.getJSONPath());
			JSONObject jsonObject = (JSONObject) obj;
			JSONObject song = (JSONObject) jsonObject.get("song");
			title = (String) song.get("title");
			artist = (String) song.get("artist");
			albumArt = (String) song.get("albumArt");
			JSONObject rating = (JSONObject) jsonObject.get("rating");
			songLiked = stringToBoolean((String) rating.get("liked").toString());
			songDisliked = stringToBoolean((String) rating.get("disliked").toString());
			songPlaying = stringToBoolean((String) jsonObject.get("playing").toString());
			JSONObject time = (JSONObject) jsonObject.get("time");
			currentTime = (String) time.get("current").toString();
			totalTime = (String) time.get("total").toString();
		}catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	public static String likeStatus() {
		if (Playback.songLiked == true) {
			return "[Like]";
		}else if(Playback.songDisliked == true) {
			return "[Dislike]";
		}else {
			return "";
		}
	}
    
    public static boolean stringToBoolean(String input) {
    	if (input == "true") {
    		return true;
    	}else {
    		return false;
    	}
    }

}
