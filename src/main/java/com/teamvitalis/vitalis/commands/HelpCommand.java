package com.teamvitalis.vitalis.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.object.Lang;

public class HelpCommand extends ACommand{

	public HelpCommand() {
		super("help", Lang.COMMAND_HELP_HELP.toString(), "/vitalis help [page|command]", new String[] {"help", "h", "?"}, new String[][] {new String[] {"%custom"}});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!isCorrectLength(0, 1, args.size())) {
			sender.sendMessage(error(Lang.COMMAND_ERROR_INVALID_LENGTH.toString()));
			return;
		}

		List<String> order = new ArrayList<String>();
		HashMap<String, TextComponent> components = new HashMap<String, TextComponent>();
		for (ACommand command : getCommands().values()) {
			if (hasPermissionHelp(sender, command.getName())) {
				TextComponent tc = new TextComponent(ChatColor.GRAY + command.getProperUse());
				String tooltip = WordUtils.wrap(ChatColor.GRAY + command.getHelp(), 40, "\n" + ChatColor.GRAY, false);
				tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(tooltip).create()));
				tc.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command.getTemplate()));
				order.add(command.getName());
				components.put(command.getName(), tc);
			}
		}
		order.remove(getCommands().get("help").getName());
		Collections.sort(order);
		Collections.reverse(order);
		order.add(getCommands().get("help").getName());
		Collections.reverse(order);
		
		List<TextComponent> neworder = new ArrayList<TextComponent>();
		for (String s : order) {
			neworder.add(components.get(s));
		}
		
		if (args.size() == 0) {
			for (TextComponent tc : getPage(Lang.COMMAND_TITLE.toString(), neworder, Lang.COMMAND_HELP_TITLE.toString(), 1)) {
				if (sender instanceof Player) {
					((Player) sender).spigot().sendMessage(tc);
				} else {
					sender.sendMessage(tc.toString());
				}
			}
			return;
		}
		
		String arg = args.get(0);
		
		if (isNumeric(arg)) {
			for (TextComponent tc : getPage(Lang.COMMAND_TITLE.toString(), neworder, Lang.COMMAND_HELP_TITLE.toString(), Integer.valueOf(arg))) {
				if (sender instanceof Player) {
					((Player) sender).spigot().sendMessage(tc);
				} else {
					sender.sendMessage(tc.toString());
				}
			}
		} else if (getCommands().keySet().contains(arg.toLowerCase())) {
			getCommands().get(arg).help(sender, true);
		}
	}
	
	private boolean hasPermissionHelp(CommandSender sender, String permission) {
		return sender.hasPermission("vitalis.command." + permission);
	}

	@Override
	public String[] tabComplete(String[] args) {
		String[] cmds = null;
		for (ACommand cmd : getCommands().values()) {
			cmds = (String[]) ArrayUtils.add(cmds, cmd.getName());
		}
		return cmds;
	}
}
