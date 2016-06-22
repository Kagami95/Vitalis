package com.teamvitalis.vitalis.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.teamvitalis.vitalis.configuration.Configs;
import com.teamvitalis.vitalis.object.Lang;

public class ReloadCommand extends ACommand {
	
	public ReloadCommand() {
		super("reload", Lang.COMMAND_RELOAD_HELP.toString(), "/vitalis reload", new String[] {"reload"});
	}
	
	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender) || !isCorrectLength(args.size(), 0, 0)) {
			return;
		}
		
		Configs.reload();
		sender.sendMessage(Lang.COMMAND_RELOAD_COMPLETE.toString(true));
	}

}
