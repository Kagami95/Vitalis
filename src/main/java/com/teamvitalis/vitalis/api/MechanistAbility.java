package com.teamvitalis.vitalis.api;

import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.object.ClassType;

public abstract class MechanistAbility extends CoreAbility{

	public MechanistAbility(Player player) {
		super(player);
	}
	
	@Override
	public ClassType getClassType() {
		return ClassType.MECHANIST;
	}

	public abstract int getDefaultUses();
}
