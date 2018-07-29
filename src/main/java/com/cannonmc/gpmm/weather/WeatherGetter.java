package com.cannonmc.gpmm.weather;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.cannonmc.gpmm.MusicMod;
import com.cannonmc.gpmm.config.Config;

public class WeatherGetter {
	
	public static String CURRENT_CONDIDTIONS;
	public static String CURRENT_TEMP;
	public static String CURRENT_ICON;
	
	private final static String DS_APIKEY = Config.CFDARKSKY_API_KEY;
	private final static String LOCATION = Config.CFlocation;
	
	private static boolean updatedWeather = false;
	
	public static void weatherCheck() {
		if ((java.time.LocalTime.now().getMinute() == 0 || java.time.LocalTime.now().getMinute() == 30) && updatedWeather == false) {
			MusicMod.THREAD_POOL.submit(new UpdateWeatherThread());
			updatedWeather = true;
		}else if((java.time.LocalTime.now().getMinute() == 5 || java.time.LocalTime.now().getMinute() == 35) && updatedWeather == true){
			System.out.println("Weather update: Ready");
			updatedWeather = false;
		}
	
	}
	public static String getURL() throws IOException {
		try {
			URL request = new URL("https://api.darksky.net/forecast/" + DS_APIKEY + "/" + LOCATION + "?units=auto");
			System.out.println("Request URL: " + request);
			String weather_report = IOUtils.toString(request);
			return weather_report;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void updateWeather() {
    	JSONParser parser = new JSONParser();
		
		try {
			Object obj = parser.parse(getURL());
			JSONObject jsonObject = (JSONObject) obj;
			JSONObject currently = (JSONObject) jsonObject.get("currently");

			CURRENT_CONDIDTIONS = (String) currently.get("summary");
			CURRENT_TEMP = Double.toString((Double) currently.get("temperature"));
			CURRENT_ICON = (String) currently.get("icon");
			System.out.println("Weather updated");
		}catch (Exception e) {
			e.printStackTrace();
		}

    }
	
}
