package com.teamvitalis.vitalis.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public abstract class CommandBase{
	
	private static HashMap<String, CommandBase> commands = new HashMap<>();

	private String name;
	private String help;
	private String properUse;
	private String[] aliases;
	
	public CommandBase(String name, String help, String properUse, String[] aliases) {
		this.name = name;
		this.help = help;
		this.properUse = properUse;
		this.aliases = aliases;
		commands.put(name, this);
	}
	
	public String getName() {
		return name;
	}
	
	public String getHelp() {
		return help;
	}
	
	public String getProperUse() {
		return properUse;
	}
	
	public String[] getAliases() {
		return aliases;
	}
	
	public static List<CommandBase> commands() {
		List<CommandBase> list = new ArrayList<>();
		for (CommandBase base : commands.values()) {
			list.add(base);
		}
		return list;
	}
	
	public abstract void execute(CommandSender sender, List<String> args);
	
	//- Useful methods -
	
	public boolean isCorrectLength(int min, int max, int size) {
		return (size >= min && size <= max);
	}
	
	public String error(ChatColor color, String type) {
		return color + "Error: " + type;
	}
}
