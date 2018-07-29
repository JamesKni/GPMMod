package com.cannonmc.gpmm.commands;

import com.cannonmc.gpmm.MusicMod;
import com.cannonmc.gpmm.config.Config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class MusicCommand extends CommandBase {
	
	public boolean canCommandSenderUseCommand(final ICommandSender sender) {
		return true;
	}

	public String getCommandName() {
		return "gpm";
	}

	public String getCommandUsage(ICommandSender sender) {
		return "/gpm <Colour>";
	}

	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (args.length == 0) {
			showHelp();
		} else if (args.length == 1) {
			switch (args[0].toLowerCase()) {
			case "white": {
				MusicMod.hexColour = "ffffff";
				break;
			}
			case "red": {
				MusicMod.hexColour = "f43030";
				break;
			}
			case "green": {
				MusicMod.hexColour = "09c454";
				break;
			}
			case "blue": {
				MusicMod.hexColour = "77e2ea";
				break;
			}
			case "pink": {
				MusicMod.hexColour = "FF00FF";
				break;
			}
			case "hide": {
				MusicMod.updateUI = false;
				break;
			}
			case "show": {
				MusicMod.updateUI = true;
				break;
			}
			case "update": {
				Config.updateConfig();
				break;
			}
			case "help": {
				showHelp();
				break;
			}
			default : {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "Invalid command"));
				break;
			}
			}
		}

	}
	public void showHelp() {
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "GPM Help"));
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "--------------------------------------------"));
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "/gpm red - Changes the GPM HUD colour to red"));
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "/gpm green - Changes the GPM HUD colour to green"));
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "/gpm blue - Changes the GPM HUD colour to blue"));
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "/gpm white - Changes the GPM HUD colour to white"));
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "/gpm pink - Changes the GPM HUD colour to pink"));
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "/gpm hide - Hides the GPM HUD"));
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "/gpm show - Shows the GPM HUD"));
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "/gpm update - updates GPM HUD with changes made"));

	}

}
