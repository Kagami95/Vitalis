package com.teamvitalis.vitalis.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.object.ClassType;
import com.teamvitalis.vitalis.object.VitalisPlayer;

public class ClassCommand extends CommandBase{
	
	private String[] mechanist = {"mechanist", "mechanism", "mech"};
	private String[] mancer = {"mancer", "magic", "voodooman"};

	public ClassCommand() {
		super("Class", "This allows you to select whether you want to be a Mancer or a Mechanist.", "/v class <class> [player]", new String[] {"class", "cls"});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(error(ChatColor.RED, "Invalid user, must be player!"));
			return;
		}
		if (isCorrectLength(1, 2, args.size())) {
			sender.sendMessage(error(ChatColor.RED, "Invalid length!"));
			return;
		}
		Player target = Bukkit.getPlayer(args.get(1));
		Player player = (Player) sender;
		if (target != null) {
			if (VitalisPlayer.fromPlayer(target).getClassType() != null) {
				sender.sendMessage(error(ChatColor.RED, "Player already has a class!"));
			}
			
			if (Arrays.asList(mechanist).contains(args.get(0).toLowerCase())) {
				VitalisPlayer.fromPlayer(target).setClassType(ClassType.MECHANIST);
				sender.sendMessage(ChatColor.GREEN + "Successfully set " + ChatColor.WHITE + target.getName() + ChatColor.GREEN + "'s class to Mechanist");
				target.sendMessage(ChatColor.WHITE + player.getName() + ChatColor.GREEN + " has set your class to Mechanist");
			} else if (Arrays.asList(mancer).contains(args.get(0).toLowerCase())) {
				VitalisPlayer.fromPlayer(target).setClassType(ClassType.MANCER);
				sender.sendMessage(ChatColor.GREEN + "Successfully set " + ChatColor.WHITE + target.getName() + ChatColor.GREEN + "'s class to Mancer");
				target.sendMessage(ChatColor.WHITE + player.getName() + ChatColor.GREEN + " has set your class to Mancer");
			} else {
				sender.sendMessage(error(ChatColor.RED, "Invalid class!"));
			}
			return;
		}
		
		if (VitalisPlayer.fromPlayer(player).getClassType() != null) {
			sender.sendMessage(error(ChatColor.RED, "You already have a class!"));
		}
		
		if (Arrays.asList(mechanist).contains(args.get(0).toLowerCase())) {
			VitalisPlayer.fromPlayer(player).setClassType(ClassType.MECHANIST);
			sender.sendMessage(ChatColor.GREEN + "Successfully set your class to Mechanist");
		} else if (Arrays.asList(mancer).contains(args.get(0).toLowerCase())) {
			VitalisPlayer.fromPlayer(player).setClassType(ClassType.MANCER);
			sender.sendMessage(ChatColor.GREEN + "Successfully set your class to Mancer");
		} else {
			sender.sendMessage(error(ChatColor.RED, "Invalid class!"));
		}
	}

	
}
