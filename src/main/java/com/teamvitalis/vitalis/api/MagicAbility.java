package com.teamvitalis.vitalis.api;

import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.object.ClassType;
import com.teamvitalis.vitalis.object.MagicType;

public abstract class MagicAbility extends CoreAbility{
	
	public MagicAbility(Player player) {
		super(player);
	}

	@Override
	public ClassType getClassType() {
		return ClassType.MANCER;
	}

	public abstract MagicType getMagicType();
}
