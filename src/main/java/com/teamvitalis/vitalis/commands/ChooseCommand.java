package com.teamvitalis.vitalis.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.object.MagicType;
import com.teamvitalis.vitalis.object.Mancer;
import com.teamvitalis.vitalis.object.VitalisPlayer;

public class ChooseCommand extends CommandBase{

	public ChooseCommand() {
		super("Choose", 
				"Choose any magic type that you wish to control. Only usable once without explicit permissions!", 
				"/v choose <magic>", 
				new String[] {"choose", "ch"});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!isCorrectLength(1, 1, args.size())) {
			sender.sendMessage(error(ChatColor.RED, "Invalid length!"));
			return;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(error(ChatColor.RED, "Only a player can run this command!"));
			return;
		}
		Player player = (Player) sender;
		VitalisPlayer vPlayer = VitalisPlayer.fromPlayer(player);
		if (!(vPlayer instanceof Mancer)) {
			sender.sendMessage(error(ChatColor.RED, "Only a mancer can use this command!"));
			return;
		}
		Mancer mancer = (Mancer) vPlayer;
		if (mancer.getMagicType() != null) {
			sender.sendMessage(error(ChatColor.RED, "You already control a magic!"));
			return;
		}
		MagicType type = MagicType.fromName(args.get(0));
		if (type == null) {
			sender.sendMessage(error(ChatColor.RED, "Invalid magic type! Try one of these:"));
			for (MagicType type2 : MagicType.getMagicTypes().values()) {
				sender.sendMessage("- " + type2.getChatColor() + type2.getName());
			}
			return;
		}
		mancer.setMagicType(type);
		sender.sendMessage(ChatColor.GREEN + "You have successfully chosen your magic!");
	}

}
