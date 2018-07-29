package com.cannonmc.gpmm;

import java.io.File;
import java.io.FileReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.cannonmc.gpmm.commands.MusicCommand;
import com.cannonmc.gpmm.commands.RetardChat;
import com.cannonmc.gpmm.commands.SprintCommand;
import com.cannonmc.gpmm.config.Config;
import com.cannonmc.gpmm.util.MusicModThreadFactory;
import com.cannonmc.gpmm.weather.WeatherGetter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

@Mod(modid = MusicMod.MODID, version = MusicMod.VERSION, acceptedMinecraftVersions = MusicMod.ACCEPTED_VERSIONS)
public class MusicMod
{
    public static final String MODID = "gpmm";
    public static final String VERSION = "1.3";
    public static final String ACCEPTED_VERSIONS = "[1.8, 1.8.9]";
    private static final Minecraft mc = Minecraft.getMinecraft();
    public static String OS;
    private File configFile;
   
    public static final String playbackFileWindows = System.getProperty("user.home") + "\\AppData\\Roaming\\Google Play Music Desktop Player\\json_store\\playback.json";
    public static final String playbackFileLinux = System.getProperty("user.home") + "\\.config\\Google Play Music Desktop Player\\\\json_store\\\\playback.json";
    public static String playbackFileFallback;
    public static boolean updateUI = false;
    
    public String title;
    public String artist;
    public String extra;
    public String totalTime;
    public String currentTime;
    public boolean songLiked;
    public boolean songDisliked;
    public boolean songPlaying;
    public int hideDelay;
    public boolean hiddenHUD = true;
    
    public static boolean sprinting;
    public static String hexColour = "77e2ea";
   
    public static int requestCounter = 0;
    
    public static final ExecutorService THREAD_POOL;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	configFile = event.getSuggestedConfigurationFile();
    	Config.init(configFile);
    	OSFinder();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	MinecraftForge.EVENT_BUS.register((Object) this);
    	ClientCommandHandler.instance.registerCommand(new MusicCommand());
    	ClientCommandHandler.instance.registerCommand(new SprintCommand());
    	ClientCommandHandler.instance.registerCommand(new RetardChat());
    	updatePlayback();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	hexColour = Config.CFcolour;
    	sprinting = Config.CFsprinting;
    	playbackFileFallback = Config.CFfallbackjson;
    	
    	if (Config.CFweatherhud) {
        	WeatherGetter.updateWeather();
    	}
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent e) {
    	if (Config.CFweatherhud) {
        	WeatherGetter.weatherCheck();
    	}
    	final int keySprint = this.mc.gameSettings.keyBindSprint.getKeyCode();
    	
    	if(updateUI == true) {
    		if (requestCounter == 1) { 
        		updatePlayback();
    		}else if(requestCounter == Config.CFrequestspeed){
    			requestCounter = 0;
    		}
    		requestCounter += 1;
    		
    		if (songLiked == true) {
    			this.extra = "[Like]";
    		}else if(songDisliked == true) {
    			this.extra = "[Dislike]";
    		}else {
    			this.extra = "";
    		}
    	}

    	if (sprinting) {
    		KeyBinding.setKeyBindState(keySprint, true);
    	}else {
    		KeyBinding.setKeyBindState(keySprint, false);
    	}
    	
    	if (songPlaying == false) {
    		hideDelay += 1;
    		if (hideDelay > 100) {
    			hiddenHUD = false;
    		}
    	}else {
    		hiddenHUD = true;
    		hideDelay = 0;
		}
    	
    }	
    
    @SubscribeEvent
    public void playerLoggedIn(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        this.updateUI = true;
        
    }
    
    @SubscribeEvent
    public void playerLoggedOut(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        this.updateUI = false;
    }
    
    @SubscribeEvent
    public void onRenderGameOverlay(final RenderGameOverlayEvent e) {
        if (e.type != RenderGameOverlayEvent.ElementType.TEXT) {
            return;
        }
        
        final ScaledResolution scaled = new ScaledResolution(this.mc);
        int width = scaled.getScaledWidth();
        final int height = scaled.getScaledHeight();
        final int colour = Integer.parseInt(hexColour, 16);
   
    	if (Config.CFweatherhud) {
        	this.mc.fontRendererObj.drawStringWithShadow(WeatherGetter.CURRENT_TEMP.substring(0,4) + " C", width - 80, 25, Integer.parseInt("888888", 16));
            this.mc.renderEngine.bindTexture(new ResourceLocation("gpmm", "icons/" + WeatherGetter.CURRENT_ICON + ".png"));
        	this.mc.ingameGUI.drawScaledCustomSizeModalRect(width-60, 0, 0, 0, 40, 40, 50, 50, 50, 50);

        }
    	
        if (!updateUI || !hiddenHUD) {
        	return;
        }
        
        this.mc.fontRendererObj.drawStringWithShadow(this.artist + " - " + this.title + "  " + this.extra, 5.0f, (float)(height - this.mc.fontRendererObj.FONT_HEIGHT - 2), colour);
        
        double playingWidth;
        try {
        	playingWidth = (Double.parseDouble(this.currentTime) / Double.parseDouble(this.totalTime)) * 107;
            playingWidth = (width / 100) * playingWidth;
        } catch(Exception ex) {
        	ex.printStackTrace();
        	playingWidth = 1;
        }
        this.mc.renderEngine.bindTexture(new ResourceLocation("gpmm", "texture/playbar.png"));
        this.mc.ingameGUI.drawTexturedModalRect(0, height-2, 0, 0, (int)playingWidth, 5); 
        
    }
    
    public static void sprintToggle() {
    	sprinting = !sprinting;
    }
    
    public static void OSFinder() {
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
    
    
 
	public void updatePlayback() {
    	JSONParser parser = new JSONParser();
		
		try {
			FileReader file;
			if (OS == "windows") {
				file = new FileReader(playbackFileWindows);
			}else if (OS == "linux") {
				file = new FileReader(playbackFileLinux);
			}else {
				file = new FileReader(playbackFileFallback);
			}
			
			Object obj = parser.parse(file);
			JSONObject jsonObject = (JSONObject) obj;
			JSONObject song = (JSONObject) jsonObject.get("song");
			title = (String) song.get("title");
			artist = (String) song.get("artist");
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
    
    public static boolean stringToBoolean(String input) {
    	if (input == "true") {
    		return true;
    	}else {
    		return false;
    	}
    }
    
    static {
        THREAD_POOL = Executors.newCachedThreadPool(new MusicModThreadFactory());
    }
}
