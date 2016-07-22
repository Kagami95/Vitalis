package com.teamvitalis.vitalis.api;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.object.ClassType;
import com.teamvitalis.vitalis.object.MagicType;
import com.teamvitalis.vitalis.object.Mancer;
import com.teamvitalis.vitalis.object.VitalisPlayer;

public abstract class MagicCast extends BaseCast{
	
	public MagicCast(String name, UUID uuid) {
		super(name, uuid);
	}
	
	public MagicCast(Player player) {
		super(player);
	}

	@Override
	public ClassType getClassType() {
		return ClassType.MANCER;
	}

	public abstract MagicType getMagicType();
	
	@Override
	public boolean canUse(VitalisPlayer player) {
		boolean canUse = true;
		Player p = Bukkit.getPlayer(player.getUniqueId());
		MagicCast cast = this;
		BaseCast currCast = BaseCast.getByName(player.getAbility(p.getInventory().getHeldItemSlot()));
		if (!(player instanceof Mancer)) {
			canUse = false;
		} else if (cast.getUniqueId() != currCast.getUniqueId()) {
			canUse = false;
		} else if (!p.hasPermission("vitalis.cast." + cast.getName())) {
			canUse = false;
		} else {
			Mancer m = (Mancer) player;
			if (m.getMagicType() != cast.getMagicType()) {
				canUse = false;
			}
		}
		return canUse;
	}
}
