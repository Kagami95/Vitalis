package com.teamvitalis.vitalis.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import com.teamvitalis.vitalis.Vitalis;
import com.teamvitalis.vitalis.abilities.ether.RiftJump;
import com.teamvitalis.vitalis.abilities.pyro.PyroBlast;
import com.teamvitalis.vitalis.abilities.pyro.TestAbility;
import com.teamvitalis.vitalis.object.VitalisPlayer;

public class AbilityListener implements Listener{
	
	Vitalis plugin;
	
	public AbilityListener(Vitalis plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onClick(PlayerAnimationEvent event) {
		if (event.isCancelled()) return;
		
		Player player = event.getPlayer();
		int slot = player.getInventory().getHeldItemSlot();
		VitalisPlayer vPlayer = VitalisPlayer.fromPlayer(player);
		if (vPlayer == null) {
			return;
		}
		String name = vPlayer.getAbility(slot);
		if (name == null) {
			return;
		}
		
		if (name.equalsIgnoreCase("TestAbility")) {
			new TestAbility(player);
		} else if (name.equalsIgnoreCase("PyroBlast")) {
			new PyroBlast(player);
		} else if (name.equalsIgnoreCase("RiftJump")) {
			new RiftJump(player);
		}
	}
	
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent event) {
		
	}
}
