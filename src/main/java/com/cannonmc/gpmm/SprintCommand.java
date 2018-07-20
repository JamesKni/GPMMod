package com.cannonmc.gpmm;

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
		MusicMod.sprintToggle();
		sender.addChatMessage(new ChatComponentText("Sprint toggled"));
	}

}
