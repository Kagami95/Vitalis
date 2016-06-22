package com.teamvitalis.vitalis.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class VTabCompleter implements TabCompleter {

	/**
	 * Breaks down the possible list and returns what is applicable depending on
	 * what the user has currently typed.
	 * 
	 * @author D4rKDeagle
	 * 
	 * @param args Args of the command. Provide all of them.
	 * @param possibilitiesOfCompletion List of things that can be given
	 */
	public static List<String> getPossibleCompletionsForGivenArgs(String[] args, List<String> possibilitiesOfCompletion) {
		String argumentToFindCompletionFor = args[args.length - 1];

		List<String> listOfPossibleCompletions = new ArrayList<String>();

		for (String foundString : possibilitiesOfCompletion) {
			if (foundString.regionMatches(true, 0, argumentToFindCompletionFor, 0, argumentToFindCompletionFor.length())) {
				listOfPossibleCompletions.add(foundString);
			}
		}
		Collections.sort(listOfPossibleCompletions);
		return listOfPossibleCompletions;
	}

	public static List<String> getPossibleCompletionsForGivenArgs(String[] args, String[] possibilitiesOfCompletion) {
		return getPossibleCompletionsForGivenArgs(args, Arrays.asList(possibilitiesOfCompletion));
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		List<String> complete = new ArrayList<String>();
		if (cmd.getName().equalsIgnoreCase("vitalis")) {
			if (args.length == 1) {
				List<String> possible = new ArrayList<String>();
				for (ACommand command : ACommand.getCommands().values()) {
					if (hasPermission(sender, command.getName())) {
						possible.add(command.getAliases()[0]);
					}
				}
				return getPossibleCompletionsForGivenArgs(args, possible);
			}
			if (args.length > 1) {
				for (ACommand command : ACommand.getCommands().values()) {
					if (args[0].equalsIgnoreCase(command.getName())) {
						if (command.getCompleters() != null) {
							if (command.getCompleters().length >= (args.length - 1)) {
								String[] completers = command.getCompleters()[(args.length - 2)];
								if (Arrays.asList(completers).contains("%custom")) {
									completers = (String[]) ArrayUtils.removeElement(completers, "%custom");
									completers = (String[]) ArrayUtils.addAll(completers, command.tabComplete(args));
								}
								if (Arrays.asList(completers).contains("%player")) {
									completers = (String[]) ArrayUtils.removeElement(completers, "%player");
									List<String> array = new ArrayList<String>();
									for (Player p : Bukkit.getOnlinePlayers()) {
										array.add(p.getName());
									}
									completers = (String[]) ArrayUtils.addAll(completers, array.toArray(new String[array.size()]));
								}
								return getPossibleCompletionsForGivenArgs(args, completers);
							}
						}
					}
				}
			}
		}
		return complete;
	}
	
	private boolean hasPermission(CommandSender sender, String permission) {
		return sender.hasPermission("vitalis.command." + permission);
	}
}