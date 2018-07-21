package com.cannonmc.gpmm;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

@Mod(modid = MusicMod.MODID, version = MusicMod.VERSION)
public class MusicMod
{
    public static final String MODID = "gpmm";
    public static final String VERSION = "1.0";
    private static final Minecraft mc = Minecraft.getMinecraft();
   
    public final String playbackFile = System.getProperty("user.home") + "\\AppData\\Roaming\\Google Play Music Desktop Player\\json_store\\playback.json";
    public static boolean updateUI = false;
    
    public String title;
    public String artist;
    public String extra;
    public String songLiked;
    public String songDisliked;
    public String songPlaying;
    
    public String totalTime;
    public String currentTime;
    
    public boolean hiddenHUD = true;;
    public int hideDelay;
    
    public static boolean sprinting;
    
    public static String hexColour = "77e2ea";
    
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	MinecraftForge.EVENT_BUS.register((Object) this);
    	ClientCommandHandler.instance.registerCommand(new MusicCommand());
    	ClientCommandHandler.instance.registerCommand(new SprintCommand());
    	ClientCommandHandler.instance.registerCommand(new RetardChat());
    	updatePlayback();
    	sprintToggle();

    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent e) {
    	final int keySprint = this.mc.gameSettings.keyBindSprint.getKeyCode();
    	
    	if(updateUI == true) {
    		updatePlayback();
    		
    		if (songLiked == "true") {
    			this.extra = "[Like]";
    		}else if(songDisliked == "true") {
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
    	
    	if (songPlaying == "false") {
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
        if (e.type != RenderGameOverlayEvent.ElementType.TEXT || !updateUI || !hiddenHUD) {
            return;
        }
        final ScaledResolution scaled = new ScaledResolution(this.mc);
        int width = scaled.getScaledWidth();
        final int height = scaled.getScaledHeight();
        final int colour = Integer.parseInt(hexColour, 16);
        this.mc.fontRendererObj.drawStringWithShadow(this.artist + " - " + this.title + "  " + this.extra, 5.0f, (float)(height - this.mc.fontRendererObj.FONT_HEIGHT - 2), colour);
        
        double playingWidth;
        try {
        	playingWidth = (Double.parseDouble(this.currentTime) / Double.parseDouble(this.totalTime)) * 107;
            playingWidth = (width / 100) * playingWidth;
        } catch(Exception ex) {
        	ex.printStackTrace();
        	playingWidth = 1;
        }
        this.mc.renderEngine.bindTexture(new ResourceLocation("gpmm", "texture/blue.png"));
        this.mc.ingameGUI.drawTexturedModalRect(0, height-2, 0, 0, (int)playingWidth, height);
        
       
    }
    
    public static void sprintToggle() {
    	sprinting = !sprinting;
    }
    
    public void updatePlayback() {
    	JSONParser parser = new JSONParser();
		
		try {
			Object obj = parser.parse(new FileReader(playbackFile));
			JSONObject jsonObject = (JSONObject) obj;
			JSONObject song = (JSONObject) jsonObject.get("song");
			title = (String) song.get("title");
			artist = (String) song.get("artist");
			
			JSONObject rating = (JSONObject) jsonObject.get("rating");
			songLiked = (String) rating.get("liked").toString();
			songDisliked = (String) rating.get("disliked").toString();
			
			songPlaying = (String) jsonObject.get("playing").toString();
			
			JSONObject time = (JSONObject) jsonObject.get("time");
			
			currentTime = (String) time.get("current").toString();
			totalTime = (String) time.get("total").toString();
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}

    }
}
