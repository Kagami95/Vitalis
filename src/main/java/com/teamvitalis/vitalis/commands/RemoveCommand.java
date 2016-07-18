package com.teamvitalis.vitalis.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.object.Lang;
import com.teamvitalis.vitalis.object.Mancer;
import com.teamvitalis.vitalis.object.VitalisPlayer;

public class RemoveCommand extends ACommand{

	public RemoveCommand() {
		super("remove", Lang.COMMAND_REMOVE_HELP.toString(), "/vitalis remove [player]", new String[] {"remove", "r"});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!isCorrectLength(0, 1, args.size())) {
			sender.sendMessage(error(Lang.COMMAND_ERROR_INVALID_LENGTH.toString()));
			return;
		}
		
		if (args.size() == 0) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(error(Lang.COMMAND_ERROR_INVALID_USER_1.toString()));
				return;
			}
			Player player = (Player) sender;
			if (!player.hasPermission("vitalis.command.remove")) {
				sender.sendMessage(error(Lang.NO_PERMISSION.toString()));
				return;
			}
			VitalisPlayer vp = VitalisPlayer.fromPlayer(player);
			if (vp == null) {
				sender.sendMessage(error("Player not registered as a VitalisPlayer"));
				return;
			}
			if (vp instanceof Mancer) {
				Mancer m = (Mancer) vp;
				m.setMagicType(null);
				VitalisPlayer.update(player);
				sender.sendMessage(ChatColor.GREEN + Lang.COMMAND_REMOVE_SUCCESS.toString());
				return;
			} else {
				sender.sendMessage(error(Lang.COMMAND_ERROR_INVALID_USER_2.toString()));
				return;
			}
		} else if (args.size() == 1) {
			Player target = Bukkit.getPlayer(args.get(0));
			if (target == null) {
				sender.sendMessage(error(Lang.COMMAND_ERROR_INVALID_TARGET.toString()));
				return;
			}
			if (!sender.hasPermission("vitalis.command.remove.others")) {
				sender.sendMessage(error(Lang.NO_PERMISSION.toString()));
				return;
			}
			VitalisPlayer vp = VitalisPlayer.fromPlayer(target);
			if (vp == null) {
				sender.sendMessage(error("Player not registered as a VitalisPlayer"));
				return;
			}
			if (vp instanceof Mancer) {
				Mancer m = (Mancer) vp;
				m.setMagicType(null);
				VitalisPlayer.update(target);
				if (sender instanceof Player) {
					target.sendMessage(ChatColor.GREEN + Lang.COMMAND_REMOVE_SUCCESS_TARGET.toString(true, sender.getName()));
				} else {
					target.sendMessage(ChatColor.GREEN + Lang.COMMAND_REMOVE_SUCCESS_TARGET.toString(true, "Console"));
				}
				sender.sendMessage(ChatColor.GREEN + Lang.COMMAND_REMOVE_SUCCESS_SENDER.toString(true, target.getName()));
				return;
			} else {
				sender.sendMessage(error(Lang.COMMAND_ERROR_INVALID_USER_2.toString()));
				return;
			}
		}
	}

}
