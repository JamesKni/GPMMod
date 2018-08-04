package com.cannonmc.gpmm;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;

import com.cannonmc.gpmm.commands.MusicCommand;
import com.cannonmc.gpmm.commands.RetardChat;
import com.cannonmc.gpmm.commands.SprintCommand;
import com.cannonmc.gpmm.config.Config;
import com.cannonmc.gpmm.util.MusicModThreadFactory;
import com.cannonmc.gpmm.util.OSCheck;
import com.cannonmc.gpmm.util.Playback;
import com.cannonmc.gpmm.util.ToggleSprint;
import com.cannonmc.gpmm.util.DelayResetThread;
import com.cannonmc.gpmm.weather.WeatherGetter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.DynamicTexture;
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

@Mod(modid = MusicMod.MODID, version = MusicMod.VERSION, acceptedMinecraftVersions = MusicMod.ACCEPTED_VERSIONS, updateJSON="https://raw.githubusercontent.com/JamesKni/GPMMod/master/update.json")
public class MusicMod
{
    public static final String MODID = "gpmm";
    public static final String VERSION = "1.4.5";
    public static final String ACCEPTED_VERSIONS = "[1.8, 1.8.9]";
    private static final Minecraft mc = Minecraft.getMinecraft();
    private File configFile;
 
    public static boolean updateUI = false;
    public boolean hiddenHUD = true;
    public int hideDelay;
    public static String hexColour = "77e2ea";
    public static int requestCounter;
    
    public static final ExecutorService THREAD_POOL;
    
    public static BufferedImage AlbumImage = null;
    public static DynamicTexture DyImage = null;
    public static boolean updatedAlbumArt = false;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	configFile = event.getSuggestedConfigurationFile();
    	Config.init(configFile);
    	Config.setVars();
    	OSCheck.init();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	MinecraftForge.EVENT_BUS.register((Object) this);
    	ClientCommandHandler.instance.registerCommand(new MusicCommand());
    	ClientCommandHandler.instance.registerCommand(new SprintCommand());
    	ClientCommandHandler.instance.registerCommand(new RetardChat());
    	Playback.update();
    	
    	if(Config.CFshowalbumart) {
        	updateAlbumArt();
        	setAlbumImage();
    	}
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	if (Config.CFweatherhud) {
        	WeatherGetter.updateWeather();
    	}
    	
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent e) {
    	if (Config.CFweatherhud) {
        	WeatherGetter.weatherCheck();
    	}
    	
    	if(updateUI == true) {
    		if (requestCounter == 1) { 
        		Playback.update();
    		}else if(requestCounter == Config.CFrequestspeed){
    			requestCounter = 0;
    		}
    		requestCounter += 1;
    	}
    	
    	if (Playback.songPlaying == false) {
    		hideDelay += 1;
    		if (hideDelay > 100) {
    			hiddenHUD = false;
    		}
    	}else {
    		hiddenHUD = true;
    		hideDelay = 0;
		}
    	
    	ToggleSprint.sprintSetter();
    }	
    
    @SubscribeEvent
    public void playerLoggedIn(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
    	if (OSCheck.OS != "unknown") {
            this.updateUI = true;
    	}
    }
    
    @SubscribeEvent
    public void playerLoggedOut(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        this.updateUI = false;
    }
    
    public static void updateAlbumArt() {
    	
        try {
            URL url = new URL(Playback.albumArt);
            MusicMod.AlbumImage = ImageIO.read(url);
            System.out.println("Getting image");
        } catch (IOException ex) {
            System.out.println("Unable to retrieve Image!!!");
        }
    }
    
    public static void setAlbumImage() {
    	MusicMod.DyImage = new DynamicTexture(AlbumImage);
        System.out.println("Updated");
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
        int iconSize = 40;
        
    	if (Config.CFweatherhud) {
        	this.mc.fontRendererObj.drawStringWithShadow(WeatherGetter.CURRENT_TEMP + "C", width - iconSize-30, (float) (iconSize / 2), colour);
            this.mc.renderEngine.bindTexture(new ResourceLocation("gpmm", "icons/" + WeatherGetter.CURRENT_ICON + ".png"));
    		this.mc.fontRendererObj.drawStringWithShadow("", 0, 0, Integer.parseInt("000000", 16));
        	this.mc.ingameGUI.drawScaledCustomSizeModalRect(width-iconSize + 1, 1, 0, 0, iconSize, iconSize, iconSize, iconSize, iconSize, iconSize);
        	this.mc.fontRendererObj.drawStringWithShadow("", 0, 0, colour);
        	this.mc.ingameGUI.drawScaledCustomSizeModalRect(width-iconSize, 0, 0, 0, iconSize, iconSize, iconSize, iconSize, iconSize, iconSize);
        }
    	
    	if (Config.CFtimehud) {
    		this.mc.fontRendererObj.drawStringWithShadow(java.time.LocalTime.now().toString().substring(0, 8), width - iconSize-30, (float) iconSize, colour);
    	}
    	
        if (!updateUI || !hiddenHUD) {
        	return;
        }
        
        if (Config.CFshowalbumart) {
        	if(Integer.parseInt(Playback.currentTime) <= 1000 && Integer.parseInt(Playback.currentTime) >= 500) {
        		if (updatedAlbumArt == false) {
        			MusicMod.THREAD_POOL.submit(new DelayResetThread());
        			updateAlbumArt();
            		setAlbumImage();
            		System.out.println(Playback.albumArt);
            		updatedAlbumArt = true;
        		}
        	}
            
            this.DyImage.updateDynamicTexture();
            this.mc.renderEngine.getDynamicTextureLocation("GPM", MusicMod.DyImage);
            this.mc.fontRendererObj.drawStringWithShadow("", 0, 0, Integer.parseInt("ffffff", 16));
            this.mc.ingameGUI.drawScaledCustomSizeModalRect(width-Config.CFalbumartsize, height-Config.CFalbumartsize, 0, 0, Config.CFalbumartsize, Config.CFalbumartsize, Config.CFalbumartsize, Config.CFalbumartsize, Config.CFalbumartsize, Config.CFalbumartsize);
        }
        
        this.mc.fontRendererObj.drawStringWithShadow(Playback.artist + " - " + Playback.title + "  " + Playback.likeStatus(), 5.0f, (float)(height - this.mc.fontRendererObj.FONT_HEIGHT - 2), colour);
        
        double playingWidth;
        try {
        	if(!Config.CFshowalbumart) {
        		double widthSegmented = width / Double.parseDouble(Playback.totalTime);
            	double currentWidthSegment = width / Double.parseDouble(Playback.currentTime);
            	playingWidth = (widthSegmented / currentWidthSegment) * width;
        	}else {
        		double widthSegmented = (width-Config.CFalbumartsize) / Double.parseDouble(Playback.totalTime);
            	double currentWidthSegment = (width-Config.CFalbumartsize) / Double.parseDouble(Playback.currentTime);
            	playingWidth = (widthSegmented / currentWidthSegment) * (width-Config.CFalbumartsize);
        	}
        	
        } catch(Exception ex) {
        	ex.printStackTrace();
        	playingWidth = 1;
        }
        this.mc.renderEngine.bindTexture(new ResourceLocation("gpmm", "texture/playbar.png"));
        this.mc.ingameGUI.drawTexturedModalRect(0, height-2, 0, 0, (int)playingWidth, 5); 
    }
    
    static {
        THREAD_POOL = Executors.newCachedThreadPool(new MusicModThreadFactory());
    }
}
