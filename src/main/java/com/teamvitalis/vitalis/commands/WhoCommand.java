package com.teamvitalis.vitalis.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.object.Lang;
import com.teamvitalis.vitalis.object.Mancer;
import com.teamvitalis.vitalis.object.Mechanist;
import com.teamvitalis.vitalis.object.VitalisPlayer;

public class WhoCommand extends ACommand{

	public WhoCommand() {
		super("who", Lang.COMMAND_WHO_HELP.toString(), "/vitalis who <player>", new String[] {"who", "w"},
				new String[][] {new String[] {"%player"}});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!isCorrectLength(0, 1, args.size())) {
			sender.sendMessage(error(Lang.COMMAND_ERROR_INVALID_LENGTH.toString()));
			return;
		}
		if (args.size() == 0) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				VitalisPlayer v = VitalisPlayer.fromPlayer(player);
				if (v == null) {
					sender.sendMessage("- " + player.getName());
					continue;
				}
				String message = "- " + player.getName();
				if (v instanceof Mancer) {
					Mancer m = (Mancer) v;
					message = message + m.getMagicType().getChatColor() + "(Mancer)";
				} else if (v instanceof Mechanist) {
					message = message + "(Mechanist)";
				}
				sender.sendMessage(message);
			}
		} 
		if (args.size() == 1) {
			Player target = Bukkit.getPlayer(args.get(0));
			if (target == null) {
				sender.sendMessage(error(Lang.COMMAND_ERROR_INVALID_TARGET.toString()));
				return;
			}
			VitalisPlayer v = VitalisPlayer.fromPlayer(target);
			if (v == null) {
				sender.sendMessage(error("Player is not registered as a VitalisPlayer!"));
				return;
			}
			StringBuilder builder = new StringBuilder(ChatColor.DARK_GRAY + "----[ " + ChatColor.DARK_PURPLE + target.getName() + ChatColor.DARK_GRAY + " ]----");
			if (v instanceof Mancer) {
				Mancer m = (Mancer) v;
				if (m.getMagicType() != null) {
					builder.append("\n- " + m.getMagicType().getChatColor() + m.getMagicType().getDisplayName().toString());
				}
			} else if (v instanceof Mechanist) {
				builder.append("\n- Mechanist");
			}
			builder.append(ChatColor.WHITE + "\nAbilities:");
			for (int i = 1; i < 10; i++) {
				String ability = v.getAbility(i);
				builder.append("\n- " + ability);
			}
			sender.sendMessage(builder.toString());
		}
	}

}
