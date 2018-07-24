package com.cannonmc.gpmm.commands;

import com.cannonmc.gpmm.MusicMod;
import com.cannonmc.gpmm.config.Config;
import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class MusicCommand extends CommandBase {

	public boolean canCommandSenderUseCommand(final ICommandSender sender) {
		return true;
	}

	public String getName() {
		return "gpm";
	}

	public String getUsage(ICommandSender sender) {
		return "/gpm <Colour>";
	}

	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
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
			}
		}

	}
	public void showHelp() {
		Minecraft.getMinecraft().player.sendMessage(new TextComponentString(ChatFormatting.GOLD + "GPM Help"));
		Minecraft.getMinecraft().player.sendMessage(new TextComponentString(ChatFormatting.GOLD + "--------------------------------------------"));
		Minecraft.getMinecraft().player.sendMessage(new TextComponentString(ChatFormatting.GOLD + "/gpm red - Changes the GPM HUD colour to red"));
		Minecraft.getMinecraft().player.sendMessage(new TextComponentString(ChatFormatting.GOLD + "/gpm green - Changes the GPM HUD colour to green"));
		Minecraft.getMinecraft().player.sendMessage(new TextComponentString(ChatFormatting.GOLD + "/gpm blue - Changes the GPM HUD colour to blue"));
		Minecraft.getMinecraft().player.sendMessage(new TextComponentString(ChatFormatting.GOLD + "/gpm white - Changes the GPM HUD colour to white"));
		Minecraft.getMinecraft().player.sendMessage(new TextComponentString(ChatFormatting.GOLD + "/gpm pink - Changes the GPM HUD colour to pink"));
		Minecraft.getMinecraft().player.sendMessage(new TextComponentString(ChatFormatting.GOLD + "/gpm hide - Hides the GPM HUD"));
		Minecraft.getMinecraft().player.sendMessage(new TextComponentString(ChatFormatting.GOLD + "/gpm show - Shows the GPM HUD"));
		Minecraft.getMinecraft().player.sendMessage(new TextComponentString(ChatFormatting.GOLD + "/gpm update - updates GPM HUD with changes made"));

	}

}
