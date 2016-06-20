package com.teamvitalis.vitalis.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.teamvitalis.vitalis.Vitalis;
import com.teamvitalis.vitalis.object.VitalisPlayer;

public abstract class CoreAbility implements Ability{

	private static ConcurrentHashMap<Player, HashMap<UUID, CoreAbility>> playerInstances = new ConcurrentHashMap<>();
	
	private long startTime;
	public Player player;
	public VitalisPlayer vPlayer;
	private boolean started;
	private UUID uuid;
	private BukkitRunnable run = new BukkitRunnable() {

		@Override
		public void run() {
			if (!progress() || !playerInstances.get(player).containsKey(uuid)) {
				remove();
				cancel();
			}
		}
	};
	
	public CoreAbility() {}
	
	public CoreAbility(Player player) {
		this.player = player;
		this.vPlayer = VitalisPlayer.fromPlayer(player);
	}
	
	public void start() {
		if (player == null) {
			return;
		}
		
		this.started = true;
		this.startTime = System.currentTimeMillis();
		this.uuid = UUID.randomUUID();
		
		if (!playerInstances.containsKey(player)){
			playerInstances.put(player, new HashMap<>());
		}
		
		if (!playerInstances.get(player).containsKey(uuid)) {
			playerInstances.get(player).put(uuid, this);
		}
		
		run.runTaskTimer(Vitalis.plugin(), 0, getTickRate());
	}
	
	public void remove() {
		if (player == null) {
			return;
		}
		playerInstances.get(player).remove(uuid);
	}
	
	public static void removeAll() {
		for (Player player : playerInstances.keySet()) {
			for (CoreAbility ability : playerInstances.get(player).values()) {
				ability.remove();
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
		return uuid;
	}
	
	public static Set<Player> getPlayers() {
		return playerInstances.keySet();
	}
	
	public static List<CoreAbility> getActiveAbilities() {
		List<CoreAbility> abils = new ArrayList<>();
		for (Player player : playerInstances.keySet()) {
			for (CoreAbility ability : playerInstances.get(player).values()) {
				abils.add(ability);
			}
		}
		return abils;
	}
	
	public static Collection<CoreAbility> getAbilitiesFromPlayer(Player player) {
		return playerInstances.containsKey(player) ? playerInstances.get(player).values() : null;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	/**
	 * This is the tick rate of the runnable for the ability. Override this to change it.
	 * Defaults to 0.
	 * @return tick rate
	 */
	public int getTickRate() {
		return 0;
	}
	
	public Location getLocation() {
		return getLocations().get(0);
	}
}
