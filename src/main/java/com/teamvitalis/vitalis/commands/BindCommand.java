package com.teamvitalis.vitalis.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.object.CastInfo;
import com.teamvitalis.vitalis.object.Lang;
import com.teamvitalis.vitalis.object.VitalisPlayer;

public class BindCommand extends ACommand{

	public BindCommand() {
		super("bind", Lang.COMMAND_BIND_HELP.toString(), "/vitalis bind <ability> [slot] [player]", new String[] {"bind", "b"},
				new String[][] {new String[] {"%custom"}, new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9"}, new String[] {"%player"}});
		
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!isCorrectLength(1, 3, args.size())) {
			sender.sendMessage(error(Lang.COMMAND_ERROR_INVALID_LENGTH.toString()));
			return;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(error(Lang.COMMAND_ERROR_INVALID_USER_1.toString()));
			return;
		}
		
		Player player = (Player) sender;
		int slot = -1;
		CastInfo cast = CastInfo.fromName(args.get(0));
		if (cast == null) {
			sender.sendMessage(error(Lang.COMMAND_ERROR_INVALID_ABILITY.toString()));
			return;
		}
		
		if (args.size() == 1) {
			slot = player.getInventory().getHeldItemSlot() + 1;
		} else if (args.size() == 2) {
			slot = Integer.parseInt(args.get(1));
		} else if (args.size() == 3) {
			slot = Integer.parseInt(args.get(1));
			Player target = Bukkit.getPlayer(args.get(2));
			if (target != null) {
				VitalisPlayer vPlayer = VitalisPlayer.fromPlayer(target);
				vPlayer.setAbility(slot, cast.getName());
				target.sendMessage(Lang.COMMAND_BIND_SUCCESS_TARGET.toString(true, cast.getName(), String.valueOf(slot), sender.getName()));
				sender.sendMessage(Lang.COMMAND_BIND_SUCCESS_SENDER.toString(true, cast.getName(), String.valueOf(slot), target.getName()));
				return;
			}
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(error(Lang.COMMAND_ERROR_INVALID_USER_1.toString()));
			return;
		}
		VitalisPlayer vPlayer = VitalisPlayer.fromPlayer(player);
		vPlayer.setAbility(slot, cast.getName());
		sender.sendMessage(Lang.COMMAND_BIND_SUCCESS.toString(true, cast.getName(), String.valueOf(slot)));
	}
}
