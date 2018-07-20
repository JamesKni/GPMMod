package com.cannonmc.gpmm;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class RetardChat extends CommandBase{
	
	Random rand = new Random();
	
	public boolean canCommandSenderUseCommand(final ICommandSender sender) {
		return true;
	}

	public String getCommandName() {
		return "retard";
	}

	public String getCommandUsage(ICommandSender sender) {
		return "/retard <message>";
	}

	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if(args.length == 0) {
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.LIGHT_PURPLE + "NoT liKe ThaT!") );
		}else {
			String message = ""; 
			for(int i = 0; i != args.length; i++) {
				message += args[i] + " ";
			}
			Minecraft.getMinecraft().thePlayer.sendChatMessage(Retardate(message));
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
