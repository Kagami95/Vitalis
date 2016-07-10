package com.teamvitalis.vitalis.api;

import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.object.ClassType;

public abstract class MechCast extends BaseCast{

	public MechCast(Player player) {
		super(player);
	}
	
	@Override
	public ClassType getClassType() {
		return ClassType.MECHANIST;
	}

	public abstract int getDefaultUses();
}
