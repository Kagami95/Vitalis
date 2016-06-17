package com.teamvitalis.vitalis.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.configuration.Lang;
import com.teamvitalis.vitalis.object.Mancer;
import com.teamvitalis.vitalis.object.Mechanist;
import com.teamvitalis.vitalis.object.VitalisPlayer;

public class ClassCommand extends CommandBase{
	
	private String[] mechanist = {"mechanist", "mechanism", "mech"};
	private String[] mancer = {"mancer", "magic", "voodooman"};

	public ClassCommand() {
		super("Class", Lang.CLASS_COMMAND_HELP.toString(), "/v class <class> [player]", new String[] {"class", "cls"});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(error(ChatColor.RED, Lang.INVALID_USER_1.toString()));
			return;
		}
		if (!isCorrectLength(1, 2, args.size())) {
			sender.sendMessage(error(ChatColor.RED, Lang.INVALID_LENGTH.toString()));
			return;
		}
		Player player = (Player) sender;
		if (args.size() == 2) {
			Player target = Bukkit.getPlayer(args.get(1));
		
			if (target == null) {
				sender.sendMessage(error(ChatColor.RED, Lang.INVALID_TARGET.toString()));
				return;
			}
			
			if (VitalisPlayer.fromPlayer(target) != null) {
				if (VitalisPlayer.fromPlayer(target).getClassType() != null) {
					sender.sendMessage(error(ChatColor.RED, Lang.HAS_CLASS.toString()));
					return;
				}
			}
			
			if (Arrays.asList(mechanist).contains(args.get(0).toLowerCase())) {
				new Mechanist(target, new HashMap<Integer, String>());
				sender.sendMessage(ChatColor.GREEN + Lang.CLASS_CHOOSE_SENDER.toString().replace("%target%", target.getName()).replace("%class%", "Mechanist"));
				target.sendMessage(ChatColor.GREEN + Lang.CLASS_CHOOSE_TARGET.toString().replace("%sender%", sender.getName()).replace("%class%", "Mechanist"));
			} else if (Arrays.asList(mancer).contains(args.get(0).toLowerCase())) {
				new Mancer(player, new HashMap<Integer, String>());
				sender.sendMessage(ChatColor.GREEN + Lang.CLASS_CHOOSE_SENDER.toString().replace("%target%", target.getName()).replace("%class%", "Mancer"));
				target.sendMessage(ChatColor.GREEN + Lang.CLASS_CHOOSE_TARGET.toString().replace("%sender%", sender.getName()).replace("%class%", "Mancer"));
			} else {
				sender.sendMessage(error(ChatColor.RED, Lang.INVALID_CLASS.toString()));
			}
			return;
		}
		
		
		if (VitalisPlayer.fromPlayer(player) != null) {
			if (VitalisPlayer.fromPlayer(player).getClassType() != null) {
				sender.sendMessage(error(ChatColor.RED, Lang.HAS_CLASS.toString()));
				return;
			}
		}
		
		if (Arrays.asList(mechanist).contains(args.get(0).toLowerCase())) {
			new Mechanist(player, new HashMap<Integer, String>());
			sender.sendMessage(ChatColor.GREEN + Lang.CLASS_CHOOSE.toString().replace("%class%", "Mechanist"));
		} else if (Arrays.asList(mancer).contains(args.get(0).toLowerCase())) {
			new Mancer(player, new HashMap<Integer, String>());
			sender.sendMessage(ChatColor.GREEN + Lang.CLASS_CHOOSE.toString().replace("%class%", "Mancer"));
		} else {
			sender.sendMessage(error(ChatColor.RED, Lang.INVALID_CLASS.toString()));
		}
	}

	
}
