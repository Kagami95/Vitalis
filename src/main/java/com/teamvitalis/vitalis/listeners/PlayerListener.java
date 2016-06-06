package com.teamvitalis.vitalis.listeners;

import com.teamvitalis.vitalis.Vitalis;
import com.teamvitalis.vitalis.events.PlayerJumpEvent;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerListener implements Listener{
	
	Vitalis plugin;
	
	private static final Map<Player, Integer> JUMPS = new HashMap<>();
	
	public PlayerListener(Vitalis plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			JUMPS.put(player, player.getStatistic(Statistic.JUMP));
		}
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		JUMPS.put(player, player.getStatistic(Statistic.JUMP));
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if (event.isCancelled()) return;
		
		Player player = event.getPlayer();
		if (event.getTo().getY() > event.getFrom().getY()) {
			if (!(player.getLocation().getBlock().getType() == Material.VINE) && !(player.getLocation().getBlock().getType() == Material.LADDER)) {
				int current = player.getStatistic(Statistic.JUMP);
				int last = JUMPS.get(player);
				
				if (last != current) {
					JUMPS.put(player, current);
					
					double yDif = event.getTo().getY() - event.getFrom().getY();
					
					if ((yDif < 0.035 || yDif > 0.037) && (yDif < 0.116 || yDif > 0.118)) {
						Bukkit.getServer().getPluginManager().callEvent(new PlayerJumpEvent(player));
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		JUMPS.remove(player);
	}
}
