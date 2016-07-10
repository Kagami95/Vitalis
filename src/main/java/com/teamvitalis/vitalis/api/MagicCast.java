package com.teamvitalis.vitalis.api;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.object.ClassType;
import com.teamvitalis.vitalis.object.MagicType;

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
}
