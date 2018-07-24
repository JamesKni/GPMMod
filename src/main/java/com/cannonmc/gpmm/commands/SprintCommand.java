package com.cannonmc.gpmm.commands;

import com.cannonmc.gpmm.MusicMod;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class SprintCommand extends CommandBase {

	public boolean canCommandSenderUseCommand(final ICommandSender sender) {
		return true;
	}

	public String getName() {
		return "sprint";
	}

	public String getUsage(ICommandSender sender) {
		return "/sprint";
	}

	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		MusicMod.sprintToggle();
		if (MusicMod.sprinting) {
			sender.sendMessage(new TextComponentString(TextFormatting.GOLD + "Sprint: " + TextFormatting.GREEN + "ON"));
		}else {
			sender.sendMessage(new TextComponentString(TextFormatting.GOLD + "Sprint: " + TextFormatting.RED +  "OFF"));
		}
		
	}

}
