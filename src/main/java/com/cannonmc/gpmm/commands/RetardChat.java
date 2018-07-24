package com.cannonmc.gpmm.commands;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class RetardChat extends CommandBase{
	
	Random rand = new Random();
	
	public boolean canCommandSenderUseCommand(final ICommandSender sender) {
		return true;
	}

	public String getName() {
		return "retard";
	}

	public String getUsage(ICommandSender sender) {
		return "/retard <message>";
	}

	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length == 0) {
			sender.sendMessage(new TextComponentString(TextFormatting.LIGHT_PURPLE + "NoT liKe ThaT!") );
		}else {
			String message = ""; 
			for(int i = 0; i != args.length; i++) {
				message += args[i] + " ";
			}
			Minecraft.getMinecraft().player.sendChatMessage(Retardate(message));
		}
	}
	
	public String Retardate(String text) {
		String newMessage = "";
		
		for(int i = 0; i != text.length(); i++) {
			int randomNumber = rand.nextInt(2);
			if(randomNumber == 0) {
				text = text.toLowerCase();
			}else {
				text = text.toUpperCase();
			}
			
			char letter = text.charAt(i);
			newMessage += letter; 
			
		}
		return newMessage;
	}

}
