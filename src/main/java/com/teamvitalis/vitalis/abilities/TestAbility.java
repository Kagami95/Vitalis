package com.teamvitalis.vitalis.abilities;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.api.CoreAbility;

public class TestAbility extends CoreAbility{
	
	public TestAbility(Player player) {
		super(player);
		start();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Test";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void progress() {
		// TODO Auto-generated method stub
		player.sendMessage("Hi");
		remove();
	}

}
