package com.teamvitalis.vitalis.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.teamvitalis.vitalis.Vitalis;
import com.teamvitalis.vitalis.configuration.CastConfig;
import com.teamvitalis.vitalis.object.VitalisPlayer;

public abstract class BaseCast implements ICast{

	private static ConcurrentHashMap<Player, HashMap<UUID, BaseCast>> playerInstances = new ConcurrentHashMap<>();
	public static FileConfiguration config = CastConfig.get();

	private long startTime;
	public Player player;
	public VitalisPlayer vPlayer;
	private boolean started;
	private UUID instance;
	private BukkitRunnable run = new BukkitRunnable() {

		@Override
		public void run() {
			if (!progress() || !playerInstances.get(player).containsKey(instance)) {
				remove();
				cancel();
			}
		}
	};
	
	public BaseCast(Player player) {
		this.player = player;
		this.vPlayer = VitalisPlayer.fromPlayer(player);
		this.instance = UUID.randomUUID();
	}
	
	public void start() {
		if (player == null) {
			return;
		}
		
		this.started = true;
		this.startTime = System.currentTimeMillis();
		
		if (!playerInstances.containsKey(player)){
			playerInstances.put(player, new HashMap<>());
		}
		
		if (!playerInstances.get(player).containsKey(instance)) {
			playerInstances.get(player).put(instance, this);
		}
		
		run.runTaskTimer(Vitalis.plugin(), 0, getTickRate());
	}
	
	public void remove() {
		if (player == null) {
			return;
		}
		playerInstances.get(player).remove(instance);
	}
	
	public static void removeAll() {
		for (Player player : playerInstances.keySet()) {
			for (BaseCast ability : playerInstances.get(player).values()) {
				ability.remove();
				playerInstances.remove(player);
			}
		}
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public boolean hasStarted() {
		return started;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public VitalisPlayer getVitalisPlayer() {
	    return vPlayer;
	}
	
	public UUID getInstanceUUID() {
		return instance;
	}
	
	public static Set<Player> getPlayers() {
		return playerInstances.keySet();
	}
	
	public static List<BaseCast> getActiveCasts() {
		List<BaseCast> abils = new ArrayList<>();
		for (Player player : playerInstances.keySet()) {
			abils.addAll(playerInstances.get(player).values());
		}
		return abils;
	}
	
	public static Collection<BaseCast> getCastsFromPlayer(Player player) {
		return playerInstances.containsKey(player) ? playerInstances.get(player).values() : null;
	}
	
	/**
	 * This is the tick rate of the runnable for the ability. Override this to change it.
	 * Defaults to 0.
	 * @return tick rate
	 */
	public int getTickRate() {
		return 0;
	}
}
