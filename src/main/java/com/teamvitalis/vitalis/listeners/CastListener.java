package com.teamvitalis.vitalis.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import com.teamvitalis.vitalis.Vitalis;
import com.teamvitalis.vitalis.abilities.ether.VoidTrap;
import com.teamvitalis.vitalis.abilities.pyro.PyroBlast;
import com.teamvitalis.vitalis.object.VitalisPlayer;

public class CastListener implements Listener{
	
	Vitalis plugin;
	
	public CastListener(Vitalis plugin) {
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
		
		if (name.equalsIgnoreCase("PyroBlast")) {
			new PyroBlast(player);
		}
	}
	
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent event) {
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
		
		if (name.equalsIgnoreCase("VoidTrap") && event.isSneaking()) {
			new VoidTrap(player);
		}
	}
	
	@EventHandler
	public void onDimensionChange(EntityPortalEvent e) {
		if (e.getFrom().getBlock().getType() == Material.ENDER_PORTAL) {
			if (VoidTrap.portalBlocks.containsKey(e.getFrom().getBlock())) {
				e.setCancelled(true);
				VoidTrap.trap(e.getEntity());
			}
		}
	}
}
