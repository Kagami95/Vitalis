package com.teamvitalis.vitalis.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

public interface ICommand {
	
	/**
	 * Gets the name of the command.
	 * 
	 * @return The command's name
	 */
	public String getName();

	/**
	 * Gets the aliases for the command.
	 * 
	 * @return All aliases for the command
	 */
	public String[] getAliases();

	/**
	 * Gets the correct usage of the command.
	 * 
	 * @return the proper use of the command
	 */
	public String getProperUse();

	/**
	 * Gets the description of the command.
	 * 
	 * @return the description
	 */
	public String getHelp();
	
	/**
	 * Gets an 2d array of possible completers.
	 * @return
	 */
	public String[][] getCompleters();

	/**
	 * Outputs the correct usage, and optionally the description, of a command
	 * to the given {@link CommandSender}.
	 * 
	 * @param sender The CommandSender to output the help to
	 * @param description Whether or not to output the description of the
	 *            command
	 */
	public void help(CommandSender sender, boolean description);

	/**
	 * Executes the command.
	 * 
	 * @param sender The CommandSender who issued the command
	 * @param args the command's arguments
	 */
	public void execute(CommandSender sender, List<String> args);
}
