package com.teamvitalis.vitalis.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

public class WhoCommand extends CommandBase{

	public WhoCommand() {
		super("Who", "Use this to find out Vitalis info on a player", "/v who <player>", new String[] {"who", "w"});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		
	}

}
