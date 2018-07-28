package com.cannonmc.gpmm.weather;

public class UpdateWeatherThread implements Runnable{

	@Override
	public void run() {
		WeatherGetter.updateWeather();
	}

}
