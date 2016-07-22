package com.teamvitalis.vitalis.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.object.ClassType;
import com.teamvitalis.vitalis.object.Mechanist;
import com.teamvitalis.vitalis.object.VitalisPlayer;

public abstract class MechCast extends BaseCast{

	public MechCast(Player player) {
		super(player);
	}
	
	@Override
	public ClassType getClassType() {
		return ClassType.MECHANIST;
	}

	public abstract int getDefaultUses();
	
	@Override
	public boolean canUse(VitalisPlayer player) {
		boolean canUse = true;
		Player p = Bukkit.getPlayer(player.getUniqueId());
		MechCast cast = this;
		BaseCast curr = BaseCast.getByName(player.getAbility(p.getInventory().getHeldItemSlot()));
		if (!(player instanceof Mechanist)) {
			canUse = false;
		} else if (!p.hasPermission("vitalis.cast." + cast.getName())) {
			canUse = false;
		} else if (cast.getUniqueId() != curr.getUniqueId()) {
			canUse = false;
		} else {
			Mechanist m = (Mechanist) player;
			if (m.getRemainingUses(cast.getName()) <= 0) {
				canUse = false;
			}
		}
		return canUse;
	}
}
