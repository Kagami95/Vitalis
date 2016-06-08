package com.teamvitalis.vitalis.api;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.teamvitalis.vitalis.Vitalis;
import com.teamvitalis.vitalis.object.VitalisPlayer;

public abstract class CoreAbility implements Ability{

	private static ConcurrentHashMap<Player, CoreAbility> playerInstances = new ConcurrentHashMap<>();
	
	private long startTime;
	public Player player;
	public VitalisPlayer vPlayer;
	private boolean started;
	private BukkitRunnable run = new BukkitRunnable() {

		@Override
		public void run() {
			if (!playerInstances.containsKey(player)) {
				cancel();
			}
			progress();
			
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
		
		if (!playerInstances.containsKey(player)){
			playerInstances.put(player, this);
		}
		
		run.runTaskTimer(Vitalis.plugin(), 0, getTickRate());
	}
	
	public void remove() {
		if (player == null) {
			return;
		}
		playerInstances.remove(player);
	}
	
	public static void removeAll() {
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
	
	/**
	 * This is the tick rate of the runnable for the ability. Override this to change it.
	 * Defaults to 0.
	 * @return tick rate
	 */
	public int getTickRate() {
		return 0;
	}
}
