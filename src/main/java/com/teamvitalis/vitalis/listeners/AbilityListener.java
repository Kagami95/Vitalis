package com.teamvitalis.vitalis.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;

import com.teamvitalis.vitalis.Vitalis;
import com.teamvitalis.vitalis.abilities.TestAbility;

public class AbilityListener implements Listener{
	
	Vitalis plugin;
	
	public AbilityListener(Vitalis plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onClick(PlayerAnimationEvent event) {
		new TestAbility(event.getPlayer());
	}
}
