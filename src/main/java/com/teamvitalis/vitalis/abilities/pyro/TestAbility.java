package com.teamvitalis.vitalis.abilities.pyro;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.api.MagicAbility;
import com.teamvitalis.vitalis.object.MagicType;

public class TestAbility extends MagicAbility{
	
	public TestAbility(Player player) {
		super(player);
		start();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "TestAbility";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Hi";
	}

	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean progress() {
		// TODO Auto-generated method stub
		player.setFireTicks(60);
		return false;
	}

	@Override
	public MagicType getMagicType() {
		// TODO Auto-generated method stub
		return MagicType.PYRO;
	}
}
