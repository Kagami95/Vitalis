package com.teamvitalis.vitalis.commands;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.teamvitalis.vitalis.object.Lang;

public abstract class ACommand implements ICommand {
	
	private static HashMap<String, ACommand> commands = new HashMap<>();

	private String name;
	private String help;
	private String properUse;
	private String[] aliases;
	private String[][] completers;
	
	public ACommand(String name, String help, String properUse, String[] aliases) {
		this(name, help, properUse, aliases, null);
	}
	
	public ACommand(String name, String help, String properUse, String[] aliases, String[][] completers) {
		this.name = name.toLowerCase();
		this.help = help;
		this.properUse = properUse;
		this.aliases = aliases;
		this.completers = completers;
		commands.put(name.toLowerCase(), this);
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
	
	public String[][] getCompleters() {
		return completers;
	}
	
	public void help(CommandSender sender, boolean detailed) {
		sender.sendMessage(Lang.COMMAND_USAGE.toString() + getProperUse());
		if (detailed) {
			sender.sendMessage(ChatColor.GRAY + getHelp());
		}
	}
	
	public boolean isCorrectLength(int min, int max, int size) {
		return (size >= min && size <= max);
	}
	
	public String error(String error) {
		return ChatColor.DARK_RED + "Error: " + ChatColor.WHITE + error;
	}
	
	protected boolean hasPermission(CommandSender sender) {
		if (sender.hasPermission("vitalis.command." + name)) {
			return true;
		} else {
			sender.sendMessage(Lang.NO_PERMISSION.toString());
			return false;
		}
	}
	
	protected boolean hasPermission(CommandSender sender, String extra) {
		return hasPermission(sender, extra, true);
	}

	protected boolean hasPermission(CommandSender sender, String extra, boolean notify) {
		if (sender.hasPermission("vitalis.command." + name + "." + extra)) {
			return true;
		} else {
			if (notify) {
				sender.sendMessage(Lang.NO_PERMISSION.toString());
			}
			return false;
		}
	}
	
	protected String[] tabComplete(String[] args) {
		return null;
	}
	
	public boolean isNumeric(String id) {
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(id, pos);
		return id.length() == pos.getIndex();
	}
	
	public List<TextComponent> getPage(String title, List<TextComponent> entries, String sub, int page) {
		List<TextComponent> strings = new ArrayList<TextComponent>();

		if (page < 1) {
			page = 1;
		}
		if ((page * 8) - 8 >= entries.size()) {
			page = Math.round(entries.size() / 8) + 1;
			if (page < 1) {
				page = 1;
			}
		}
		strings.add(new TextComponent(title + ChatColor.DARK_GRAY + " [" + ChatColor.GRAY + page + "/" + (int) Math.ceil((entries.size()+.0)/(8+.0)) + ChatColor.DARK_GRAY + "]"));
		strings.add(new TextComponent(sub));
		if (entries.size() > ((page * 8) - 8)) {
			for (int i = ((page * 8) - 8); i < entries.size(); i++) {
				if (entries.get(i) != null) {
					strings.add(entries.get(i));
				}
				if (i >= (page * 8)-1) {
					break;
				}
			}
		}

		return strings;
	}
	
	public static HashMap<String, ACommand> getCommands() {
		return commands;
	}
}
