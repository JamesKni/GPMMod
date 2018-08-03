package com.cannonmc.gpmm.commands;

import com.cannonmc.gpmm.MusicMod;
import com.cannonmc.gpmm.util.ToggleSprint;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class SprintCommand extends CommandBase {

	public boolean canCommandSenderUseCommand(final ICommandSender sender) {
		return true;
	}

	public String getCommandName() {
		return "sprint";
	}

	public String getCommandUsage(ICommandSender sender) {
		return "/sprint";
	}

	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		ToggleSprint.sprintToggle();
		if (ToggleSprint.sprinting) {
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Sprint: " + EnumChatFormatting.GREEN + "ON"));
		}else {
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Sprint: " + EnumChatFormatting.RED +  "OFF"));
		}
		
	}

}
