package com.teamvitalis.vitalis.api;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.object.VitalisPlayer;

public abstract class CoreAbility implements Ability{

	private static ConcurrentHashMap<Player, CoreAbility> playerInstances = new ConcurrentHashMap<>();
	
	private long startTime;
	private Player player;
	private VitalisPlayer vPlayer;
	private boolean started;
	
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
		
		if (!playerInstances.containsKey(player)){
			playerInstances.put(player, this);
		}
	}
	
	public void remove() {
		if (player == null) {
			return;
		}
		
		playerInstances.remove(player);
	}
	
	public void progressAll() {
		for (Player player : playerInstances.keySet()) {
			playerInstances.get(player).progress();
		}
	}
	
	public void removeAll() {
		for (Player player : playerInstances.keySet()) {
			playerInstances.get(player).remove();
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

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
