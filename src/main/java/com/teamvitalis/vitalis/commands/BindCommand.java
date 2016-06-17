package com.teamvitalis.vitalis.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.configuration.Lang;
import com.teamvitalis.vitalis.object.VitalisPlayer;

public class BindCommand extends CommandBase{

	public BindCommand() {
		super("Bind", Lang.BIND_COMMAND_HELP.toString(), "/v bind <ability> [slot] [player]", new String[] {"bind", "b"});
		
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!isCorrectLength(1, 3, args.size())) {
			sender.sendMessage(error(ChatColor.RED, Lang.INVALID_LENGTH.toString()));
			return;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(error(ChatColor.RED, Lang.INVALID_USER_1.toString()));
			return;
		}
		
		Player player = (Player) sender;
		int slot = -1;
		String ability = args.get(0);
		
		if (args.size() == 1) {
			slot = player.getInventory().getHeldItemSlot();
		} else if (args.size() == 2) {
			slot = Integer.parseInt(args.get(1))-1;
		} else if (args.size() == 3) {
			slot = Integer.parseInt(args.get(1))-1;
			Player target = Bukkit.getPlayer(args.get(2));
			if (target != null) {
				VitalisPlayer vPlayer = VitalisPlayer.fromPlayer(target);
				vPlayer.setAbility(slot, ability);
				if (sender instanceof Player) {
					target.sendMessage(ChatColor.GREEN + Lang.BIND_SUCCESS_TARGET.toString().replace("%sender%", sender.getName()).replace("%slot%", "" + slot).replace("%ability%", ability));
				} else {
					target.sendMessage(ChatColor.GREEN + Lang.BIND_SUCCESS_TARGET.toString().replace("%sender%", "Console").replace("%slot%", "" + slot).replace("%ability%", ability));
				}
				sender.sendMessage(ChatColor.GREEN + Lang.BIND_SUCCESS_SENDER.toString().replace("%target%", target.getName()).replace("%slot%", "" + slot).replace("%ability%", ability));
				return;
			}
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(error(ChatColor.RED, Lang.INVALID_USER_1.toString()));
			return;
		}
		VitalisPlayer vPlayer = VitalisPlayer.fromPlayer(player);
		vPlayer.setAbility(slot, ability);
		sender.sendMessage(ChatColor.GREEN + Lang.BIND_SUCCESS.toString().replace("%slot%", "" + slot).replace("%ability%", ability));
	}
}
