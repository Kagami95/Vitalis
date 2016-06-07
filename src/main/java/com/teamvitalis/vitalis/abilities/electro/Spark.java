package com.teamvitalis.vitalis.abilities.electro;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.api.CoreAbility;

public class Spark extends CoreAbility{
	
	public Spark(Player player) {
		super(player);
		start();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Spark";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Basic ability, sends a message in chat.";
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
