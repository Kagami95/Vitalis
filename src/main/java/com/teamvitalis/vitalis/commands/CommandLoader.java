package com.teamvitalis.vitalis.commands;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

import com.teamvitalis.vitalis.Vitalis;

public class CommandLoader {

	Vitalis plugin;
	
	public CommandLoader(Vitalis plugin) {
		this.plugin = plugin;
	}
	
	public void loadCommands() {
		PluginCommand main = plugin.getCommand("vitalis");
		
		//TODO: call constructors of commands
		new HelpCommand();
		new ChooseCommand();
		new BindCommand();
		new ClassCommand();
		
		CommandExecutor exe = new CommandExecutor() {

			@Override
			public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
				if (args.length == 0) {
					//TODO: decide on basic message
					sender.sendMessage("Hi");
					return true;
				}
				
				for (CommandBase base : CommandBase.commands()) {
					if (Arrays.asList(base.getAliases()).contains(args[0].toLowerCase())) {
						base.execute(sender, Arrays.asList(args).subList(1, args.length));
						break;
					}
				}
				return true;
			}
			
		};
		
		main.setExecutor(exe);
	}
}
