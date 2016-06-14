package com.teamvitalis.vitalis.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.object.VitalisPlayer;

public class BindCommand extends CommandBase{

	public BindCommand() {
		super("Bind", "This command allows you to bind an ability to your hotbar.", "/v bind <ability> [slot] [player]", new String[] {"bind", "b"});
		
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!isCorrectLength(1, 3, args.size())) {
			sender.sendMessage(error(ChatColor.RED, "Invalid length!"));
			return;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(error(ChatColor.RED, "Player only command!"));
			return;
		}
		Player player = (Player) sender;
		int slot = -1;
		String ability = args.get(0);
		if (args.size() == 1) {
			slot = player.getInventory().getHeldItemSlot();
		} else if (args.size() == 2) {
			slot = Integer.parseInt(args.get(1));
		} else if (args.size() == 3) {
			slot = Integer.parseInt(args.get(1));
			Player target = Bukkit.getPlayer(args.get(2));
			if (target != null) {
				VitalisPlayer vPlayer = VitalisPlayer.fromPlayer(target);
				vPlayer.setAbility(slot, ability);
				if (sender instanceof Player) {
					target.sendMessage(ChatColor.GREEN + player.getName() + " has set your slot " + (slot + 1) + " ability to " + ability);
				} else {
					target.sendMessage(ChatColor.GREEN + "Console has set your slot " + (slot + 1) + " ability to " + ability);
				}
				sender.sendMessage(ChatColor.GREEN + "You have set " + target.getName() + "'s slot " + (slot + 1) + " ability to " + ability);
				return;
			}
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage("That version of the command must be run by a player.");
			return;
		}
		VitalisPlayer vPlayer = VitalisPlayer.fromPlayer(player);
		vPlayer.setAbility(slot, ability);
		sender.sendMessage("Your slot" + (slot + 1) + " ability has been set to " + ability);
	}
}
