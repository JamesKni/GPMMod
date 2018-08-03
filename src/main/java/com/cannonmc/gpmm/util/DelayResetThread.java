package com.cannonmc.gpmm.util;

import java.util.concurrent.TimeUnit;

import com.cannonmc.gpmm.MusicMod;

public class DelayResetThread implements Runnable{

	@Override
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		MusicMod.updatedAlbumArt = false;
		System.out.println("Reset");
	}

}
