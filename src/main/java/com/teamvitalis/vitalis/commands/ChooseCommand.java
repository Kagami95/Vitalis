package com.teamvitalis.vitalis.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.configuration.Lang;
import com.teamvitalis.vitalis.object.MagicType;
import com.teamvitalis.vitalis.object.Mancer;
import com.teamvitalis.vitalis.object.VitalisPlayer;

public class ChooseCommand extends CommandBase{

	public ChooseCommand() {
		super("Choose", 
				Lang.CHOOSE_COMMAND_HELP.toString(), 
				"/v choose <magic>", 
				new String[] {"choose", "ch"});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!isCorrectLength(1, 1, args.size())) {
			sender.sendMessage(error(ChatColor.RED, Lang.INVALID_LENGTH.toString()));
			return;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(error(ChatColor.RED, Lang.INVALID_USER_1.toString()));
			return;
		}
		Player player = (Player) sender;
		VitalisPlayer vPlayer = VitalisPlayer.fromPlayer(player);
		if (!(vPlayer instanceof Mancer)) {
			sender.sendMessage(error(ChatColor.RED, Lang.INVALID_USER_2.toString()));
			return;
		}
		Mancer mancer = (Mancer) vPlayer;
		if (mancer.getMagicType() != null) {
			sender.sendMessage(error(ChatColor.RED, Lang.HAS_MAGIC.toString()));
			return;
		}
		MagicType type = MagicType.fromName(args.get(0));
		if (type == null) {
			String message = Lang.INVALID_MAGIC.toString();
			if (Lang.INVALID_MAGIC.toString().contains("%magiclist%")) {
				message = message.replace("%magiclist%", "");
			}
			sender.sendMessage(error(ChatColor.RED, message));
			if (Lang.INVALID_MAGIC.toString().contains("%magiclist%")) {
				for (MagicType type2 : MagicType.getMagicTypes().values()) {
					sender.sendMessage("- " + type2.getChatColor() + type2.getName());
				}
			}
			return;
		}
		mancer.setMagicType(type);
		sender.sendMessage(ChatColor.GREEN + Lang.MAGIC_CHOOSE.toString().replace("%magic%", type.getChatColor() + type.getName() + ChatColor.GREEN));
	}

}
