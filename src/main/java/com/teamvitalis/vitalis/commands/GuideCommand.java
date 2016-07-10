package com.teamvitalis.vitalis.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.teamvitalis.vitalis.api.AddonCast;
import com.teamvitalis.vitalis.api.BaseCast;
import com.teamvitalis.vitalis.api.Collision;
import com.teamvitalis.vitalis.object.Lang;
import com.teamvitalis.vitalis.object.MagicType;

public class GuideCommand extends ACommand{

	String[] magic = {"magic", "m", "voodoo", "magics"};

	public GuideCommand() {
		super("guide", Lang.COMMAND_HELP_HELP.toString(), "/vitalis guide <topic>", new String[] {"guide"}, new String[][] {new String[] {"%custom"}});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!isCorrectLength(0, 1, args.size())) {
			sender.sendMessage(error(Lang.COMMAND_ERROR_INVALID_LENGTH.toString()));
			return;
		}

		for (ACommand base : getCommands().values()) {
			if (Arrays.asList(base.getAliases()).contains(args.get(0).toLowerCase())) {
				sender.sendMessage(ChatColor.GRAY + base.getName() + " -");
				sender.sendMessage(ChatColor.YELLOW + base.getHelp());
				return;
			}
		}

		for (BaseCast cast : BaseCast.getAllCasts()) {
			if (cast.getName().equalsIgnoreCase(args.get(0))) {
				sender.sendMessage(ChatColor.GRAY + cast.getName() + " -");
				if (cast instanceof AddonCast) {
					AddonCast addon = (AddonCast) cast;
					sender.sendMessage(ChatColor.GRAY + "Author: " + ChatColor.YELLOW + addon.getAuthor());
					sender.sendMessage(ChatColor.GRAY + "Version: " + ChatColor.YELLOW + addon.getVersion());
				}
				sender.sendMessage(ChatColor.YELLOW + cast.getDescription());
				if (cast instanceof Collision) {
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
