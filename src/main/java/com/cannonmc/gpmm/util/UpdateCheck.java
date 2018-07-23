package com.cannonmc.gpmm.util;

import java.net.URL;

import org.apache.commons.io.IOUtils;

import com.cannonmc.gpmm.MusicMod;


public class UpdateCheck {

	public static String latestVersion;

	public static void versionCheck() {
		try {
			final String latestVersion = IOUtils.toString(new URL("https://raw.githubusercontent.com/JamesKni/GPMMod/master/Latestversion"));
			System.out.println(latestVersion);
			if (Float.parseFloat(latestVersion) > Float.parseFloat(MusicMod.VERSION) ) {
				MusicMod.outdated = true;
				System.out.println("##### Out of date mod #####");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
