package com.teamvitalis.vitalis.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.teamvitalis.vitalis.object.MagicType;

public class HelpCommand extends CommandBase{
	
	String[] magic = {"magic", "m", "voodoo", "magics"};

	public HelpCommand() {
		super("Help", "Shows the help message for any command.", "/v help <topic>", new String[] {"help", "h", "?"});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!isCorrectLength(0, 1, args.size())) {
			sender.sendMessage(error(ChatColor.RED, "Invalid length"));
		}
		
		for (CommandBase base : commands()) {
			if (Arrays.asList(base.getAliases()).contains(args.get(0).toLowerCase())) {
				sender.sendMessage(ChatColor.GRAY + base.getName() + " -");
				sender.sendMessage(ChatColor.YELLOW + base.getHelp());
				return;
			}
		}
		
		if (Arrays.asList(magic).contains(args.get(0).toLowerCase())) {
			sender.sendMessage("---------[ Magic Types ]---------");
			for (MagicType magic : MagicType.getMagicTypes().values()) {
				sender.sendMessage(ChatColor.WHITE + "- " + magic.getChatColor() + magic.getName());
			}
		}
		
		//TODO: Add any other topics
	}
}
