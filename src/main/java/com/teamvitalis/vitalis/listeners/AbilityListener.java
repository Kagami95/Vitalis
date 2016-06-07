package com.teamvitalis.vitalis.listeners;

import org.bukkit.event.Listener;

import com.teamvitalis.vitalis.Vitalis;

public class AbilityListener implements Listener{
	
	Vitalis plugin;
	
	public AbilityListener(Vitalis plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
}
