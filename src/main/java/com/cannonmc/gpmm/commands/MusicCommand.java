package com.cannonmc.gpmm.commands;

import com.cannonmc.gpmm.MusicMod;
import com.cannonmc.gpmm.config.Config;

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
			sender.addChatMessage(new ChatComponentText("Colours: WHITE, RED, GREEN, BLUE"));
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
			}
		}

	}

}
