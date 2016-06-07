package com.teamvitalis.vitalis.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;

import com.teamvitalis.vitalis.abilities.electro.Spark;

public class AbilityListener implements Listener{

	@EventHandler
	public void onClick(PlayerAnimationEvent event) {
		new Spark(event.getPlayer());
	}
}
