package com.teamvitalis.vitalis.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.object.Lang;
import com.teamvitalis.vitalis.object.Mancer;
import com.teamvitalis.vitalis.object.Mechanist;
import com.teamvitalis.vitalis.object.VitalisPlayer;

public class ClassCommand extends ACommand{
	
	private String[] mechanist = {"mechanist", "mechanism", "mech"};
	private String[] mancer = {"mancer", "magic", "voodooman"};

	public ClassCommand() {
		super("class", Lang.COMMAND_CLASS_HELP.toString(), "/vitalis class <class> [player]", new String[] {"class", "cls"}, 
				new String[][] {new String[] {"mechanist", "mancer"}, new String[] {"%player"}});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(error(Lang.COMMAND_ERROR_INVALID_USER_1.toString()));
			return;
		}
		if (!isCorrectLength(1, 2, args.size())) {
			sender.sendMessage(error(Lang.COMMAND_ERROR_INVALID_LENGTH.toString()));
			return;
		}
		Player player = (Player) sender;
		if (args.size() == 2) {
			Player target = Bukkit.getPlayer(args.get(1));
		
			if (target == null) {
				sender.sendMessage(error(Lang.COMMAND_ERROR_INVALID_TARGET.toString()));
				return;
			}
			
			if (VitalisPlayer.fromPlayer(target) != null) {
				if (VitalisPlayer.fromPlayer(target).getClassType() != null) {
					sender.sendMessage(error(Lang.COMMAND_ERROR_HAS_CLASS.toString()));
					return;
				}
			}
			
			if (Arrays.asList(mechanist).contains(args.get(0).toLowerCase())) {
				new Mechanist(target, new HashMap<Integer, String>(), new Integer[]{});
				sender.sendMessage(Lang.COMMAND_CLASS_CHOOSE_SENDER.toString(true, Lang.CHAT_MECHANIST_DISPLAY.toString(), target.getName()));
				target.sendMessage(Lang.COMMAND_CLASS_CHOOSE_SENDER.toString(true, Lang.CHAT_MECHANIST_DISPLAY.toString(), sender.getName()));
			} else if (Arrays.asList(mancer).contains(args.get(0).toLowerCase())) {
				new Mancer(player, new HashMap<Integer, String>(), new Integer[]{});
				sender.sendMessage(Lang.COMMAND_CLASS_CHOOSE_SENDER.toString(true, Lang.CHAT_MANCER_DISPLAY.toString(), target.getName()));
				target.sendMessage(Lang.COMMAND_CLASS_CHOOSE_SENDER.toString(true, Lang.CHAT_MANCER_DISPLAY.toString(), sender.getName()));
			} else {
				sender.sendMessage(error(Lang.COMMAND_ERROR_INVALID_CLASS.toString()));
			}
			return;
		}
		
		
		if (VitalisPlayer.fromPlayer(player) != null) {
			if (VitalisPlayer.fromPlayer(player).getClassType() != null) {
				sender.sendMessage(error(Lang.COMMAND_ERROR_HAS_CLASS.toString()));
				return;
			}
		}
		
		if (Arrays.asList(mechanist).contains(args.get(0).toLowerCase())) {
			new Mechanist(player, new HashMap<Integer, String>(), new Integer[]{});
			sender.sendMessage(Lang.COMMAND_CLASS_CHOOSE.toString(true, Lang.CHAT_MECHANIST_DISPLAY.toString()));
		} else if (Arrays.asList(mancer).contains(args.get(0).toLowerCase())) {
			new Mancer(player, new HashMap<Integer, String>(), new Integer[]{});
			sender.sendMessage(Lang.COMMAND_CLASS_CHOOSE.toString(true, Lang.CHAT_MANCER_DISPLAY.toString()));
		} else {
			sender.sendMessage(error(Lang.COMMAND_ERROR_INVALID_CLASS.toString()));
		}
	}
}
