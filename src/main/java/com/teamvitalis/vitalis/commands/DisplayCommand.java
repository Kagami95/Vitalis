package com.teamvitalis.vitalis.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.object.CastInfo;
import com.teamvitalis.vitalis.object.ClassType;
import com.teamvitalis.vitalis.object.Lang;
import com.teamvitalis.vitalis.object.MagicType;

public class DisplayCommand extends ACommand{
	
	private String[] mech = {"mech", "mechanism", "mechanist"};

	public DisplayCommand() {
		super("display", "Displays the casts corresponding to a selected magic, or for mechanism", "/vitalis display <magic>", new String[] {"display", "d"});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(error(Lang.COMMAND_ERROR_INVALID_USER_1.toString()));
			return;
		}
		Player player = (Player) sender;
		if (!isCorrectLength(1, 1, args.size())) {
			sender.sendMessage(error(Lang.COMMAND_ERROR_INVALID_LENGTH.toString()));
			return;
		}
		
		StringBuilder msg = new StringBuilder();
		
		if (Arrays.asList(mech).contains(args.get(0).toLowerCase())) {
			msg.append(ChatColor.GOLD + "Mechanism\n");
			for (CastInfo info : CastInfo.getListByClassType(ClassType.MECHANIST)) {
				if (info.isEnabled()) {
					msg.append(ChatColor.GOLD + info.getName() + ChatColor.GREEN + " ENABLED\n");
				} else {
					msg.append(ChatColor.GOLD + info.getName() + ChatColor.RED + " DISABLED\n");
				}
			}
			return;
		} else {
			for (MagicType magic : MagicType.values()) {
				if (!magic.getName().equalsIgnoreCase(args.get(0)) && !Arrays.asList(magic.getAliases()).contains(args.get(0))) {
					continue;
				}
				
				msg.append(magic.getChatColor() + "-- " + magic.getName() + " --\n");
				for (CastInfo info : CastInfo.getListByMagicType(magic)) {
					if (info.isEnabled()) {
						msg.append(magic.getChatColor() + info.getName() + ChatColor.GREEN + " ENABLED\n");
					} else {
						msg.append(magic.getChatColor() + info.getName() + ChatColor.RED + " DISABLED\n");
					}
				}
				return;
			}
			
			player.sendMessage(error(Lang.COMMAND_ERROR_INVALID_ARG.toString()));
		}
	}
}
