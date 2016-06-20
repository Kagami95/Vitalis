package com.teamvitalis.vitalis.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.teamvitalis.vitalis.api.AddonAbility;
import com.teamvitalis.vitalis.api.CoreAbility;
import com.teamvitalis.vitalis.configuration.Lang;
import com.teamvitalis.vitalis.object.AbilityInfo;
import com.teamvitalis.vitalis.object.MagicType;

public class HelpCommand extends CommandBase{
	
	String[] magic = {"magic", "m", "voodoo", "magics"};

	public HelpCommand() {
		super("Help", Lang.HELP_COMMAND_HELP.toString(), "/v help <topic>", new String[] {"help", "h", "?"});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!isCorrectLength(0, 1, args.size())) {
			sender.sendMessage(error(ChatColor.RED, Lang.INVALID_LENGTH.toString()));
			return;
		}
		
		for (CommandBase base : commands()) {
			if (Arrays.asList(base.getAliases()).contains(args.get(0).toLowerCase())) {
				sender.sendMessage(ChatColor.GRAY + base.getName() + " -");
				sender.sendMessage(ChatColor.YELLOW + base.getHelp());
				return;
			}
		}
		
		for (AbilityInfo info : AbilityInfo.getAbilitiesAsList()) {
			if (info.getName().equalsIgnoreCase(args.get(0))) {
				CoreAbility ability = info.getAbility();
				sender.sendMessage(ChatColor.GRAY + info.getName() + " -");
				if (info.isAddon()) {
					AddonAbility addon = (AddonAbility) ability;
					sender.sendMessage(ChatColor.GRAY + "Author: " + ChatColor.YELLOW + addon.getAuthor());
					sender.sendMessage(ChatColor.GRAY + "Version: " + ChatColor.YELLOW + addon.getVersion());
				}
				sender.sendMessage(ChatColor.YELLOW + ability.getDescription());
				if (info.canCollide()) {
					sender.sendMessage(ChatColor.GRAY + "This ability will collide with entities, blocks, and other abilities!");
				}
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
