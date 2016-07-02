package com.teamvitalis.vitalis.commands;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

import com.teamvitalis.vitalis.Vitalis;
import com.teamvitalis.vitalis.object.Lang;

public class Commands {

	Vitalis plugin;

	public Commands(Vitalis plugin) {
		this.plugin = plugin;
	}

	public void loadCommands() {
		PluginCommand main = plugin.getCommand("vitalis");

		//Developer note: Please keep these in alphabetical order.
		new BindCommand();
		new ChooseCommand();
		new ClassCommand();
		new GuideCommand();
		new HelpCommand();
		new ReloadCommand();
		new RemoveCommand();
		new WhoCommand();

		CommandExecutor exe = new CommandExecutor() {

			@Override
			public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
				try {
					for (int i = 0; i < args.length; i++) {
						args[i] = args[i];
					}

					if (args.length == 0) {
						sender.sendMessage(Lang.COMMAND_SUGGESTION.toString(true));
						return true;
					}

					for (ACommand base : ACommand.getCommands().values()) {
						if (Arrays.asList(base.getAliases()).contains(args[0].toLowerCase())) {
							base.execute(sender, Arrays.asList(args).subList(1, args.length));
							return true;
						}
					}
					sender.sendMessage(Lang.COMMAND_UNKNOWN.toString(true));
				} catch (Exception e) {
					sender.sendMessage(Lang.COMMAND_ERROR_UNKNOWN.toString(true));
					e.printStackTrace();
				}
				return true;
			}
		};

		main.setExecutor(exe);
		main.setTabCompleter(new VTabCompleter());
	}
}
