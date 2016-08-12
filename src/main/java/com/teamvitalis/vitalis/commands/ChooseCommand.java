package com.teamvitalis.vitalis.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.object.Lang;
import com.teamvitalis.vitalis.object.MagicType;
import com.teamvitalis.vitalis.object.Mancer;
import com.teamvitalis.vitalis.object.VitalisPlayer;

public class ChooseCommand extends ACommand{

	public ChooseCommand() {
		super("choose", Lang.COMMAND_CHOOSE_HELP.toString(), "/vitalis choose <magic>", new String[] {"choose", "ch"});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!isCorrectLength(1, 1, args.size())) {
			sender.sendMessage(error(Lang.COMMAND_ERROR_INVALID_LENGTH.toString()));
			return;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(error(Lang.COMMAND_ERROR_INVALID_USER_1.toString()));
			return;
		}
		Player player = (Player) sender;
		VitalisPlayer vPlayer = VitalisPlayer.fromPlayer(player);
		if (!(vPlayer instanceof Mancer)) {
			sender.sendMessage(error(Lang.COMMAND_ERROR_INVALID_USER_2.toString()));
			return;
		}
		Mancer mancer = (Mancer) vPlayer;
		if (mancer.getMagicType() != null) {
			sender.sendMessage(error(Lang.COMMAND_ERROR_HAS_MAGIC.toString()));
			return;
		}
		MagicType type = MagicType.fromName(args.get(0));
		if (type == null) {
			String message = Lang.COMMAND_ERROR_INVALID_MAGIC.toString();
			sender.sendMessage(error(message));
			for (MagicType type2 : MagicType.values()) {
				sender.sendMessage("- " + type2.getChatColor() + type2.getName());
			}
			return;
		}
		mancer.setMagicType(type);
		sender.sendMessage(Lang.COMMAND_CHOOSE_SUCCESS.toString(true, type.getChatColor() + type.getName()));
	}

}
