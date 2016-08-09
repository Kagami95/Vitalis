package com.teamvitalis.vitalis.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.guis.SkillTreeInventory;
import com.teamvitalis.vitalis.object.Lang;
import com.teamvitalis.vitalis.object.VitalisPlayer;

public class SkillTreeCommand extends ACommand{

	public SkillTreeCommand() {
		super("skilltree", "Opens the skill tree for unlocking new casts and binding unlocked ones.", "/vitalis skilltree", new String[] {"skilltree", "sk"});
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(error(Lang.COMMAND_ERROR_INVALID_USER_1.toString()));
			return;
		}
		Player player = (Player) sender;
		
		if (isCorrectLength(0, 0, args.size())) {
			sender.sendMessage(error(Lang.COMMAND_ERROR_INVALID_LENGTH.toString(getProperUse())));
			return;
		}
		VitalisPlayer vp = VitalisPlayer.fromPlayer(player);
		
		if (vp == null) {
			sender.sendMessage(ChatColor.RED + "You aren't registered as a VitalisPlayer");
			return;
		}
		
		if (vp.getClassType() == null) {
			sender.sendMessage(ChatColor.RED + "You have no class to get skills of");
			return;
		}
		
		SkillTreeInventory sti = new SkillTreeInventory(vp, 0);
		sti.open(player);
		sender.sendMessage(ChatColor.GREEN + "Opening SkillTree page " + sti.getPageID());
	}

}
