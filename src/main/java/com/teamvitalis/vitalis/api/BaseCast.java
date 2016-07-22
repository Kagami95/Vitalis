package com.teamvitalis.vitalis.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.teamvitalis.vitalis.Vitalis;
import com.teamvitalis.vitalis.casts.ether.VoidTrap;
import com.teamvitalis.vitalis.casts.pyro.PyroBlast;
import com.teamvitalis.vitalis.configuration.CastConfig;
import com.teamvitalis.vitalis.object.CastLog;
import com.teamvitalis.vitalis.object.VitalisPlayer;

public abstract class BaseCast implements ICast{

	private static ConcurrentHashMap<Player, HashMap<UUID, BaseCast>> playerInstances = new ConcurrentHashMap<>();
	private static HashMap<String, BaseCast> nameMap = new HashMap<>();
	private static HashMap<UUID, BaseCast> uuidMap = new HashMap<>();
	public static FileConfiguration config = CastConfig.get();
	
	private String name;
	private UUID uuid;
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
	
	/**
	 * This is a loading constructor, it should never
	 * be called in any other circumstance. Use getter 
	 * methods provided.
	 * @param name Name of Cast
	 * @param uuid UUID of Cast
	 */
	public BaseCast(String name, UUID uuid) {
		this.name = name;
		this.uuid = uuid;
		nameMap.put(name.toLowerCase(), this);
		uuidMap.put(uuid, this);
	}
	
	public BaseCast(Player player) {
		this.player = player;
		this.vPlayer = VitalisPlayer.fromPlayer(player);
		this.uuid = UUID.randomUUID();
	}
	
	public static void loadAll(){
		CastLog log = new CastLog(Vitalis.plugin(), "Vitalis");
		log.modifyLine("Vitalis CastLog");
		log.modifyLine("Created: " + log.getDateString());
		log.skipLine();
		if (new PyroBlast().load()) {
			log.modifyLine("PyroBlast cast loaded");
		}
		
		if (new VoidTrap().load()) {
			log.modifyLine("VoidTrap cast loaded!");
		}
	}
	
	public void start() {
		if (player == null) {
			return;
		}
		
		if (!canUse(vPlayer)) {
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
		playerInstances.get(player).remove(uuid);
	}
	
	public static void removeAll() {
		for (Player player : playerInstances.keySet()) {
			for (BaseCast ability : playerInstances.get(player).values()) {
				ability.remove();
			}
		}
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public UUID getUniqueId() {
		return uuid;
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
	
	public static List<BaseCast> getActiveCasts() {
		List<BaseCast> abils = new ArrayList<>();
		for (Player player : playerInstances.keySet()) {
			for (BaseCast ability : playerInstances.get(player).values()) {
				abils.add(ability);
			}
		}
		return abils;
	}
	
	public static Collection<BaseCast> getCastsFromPlayer(Player player) {
		return playerInstances.containsKey(player) ? playerInstances.get(player).values() : null;
	}
	
	public static BaseCast getByName(String name) {
		return nameMap.containsKey(name.toLowerCase()) ? nameMap.get(name.toLowerCase()) : null;
	}
	
	public static BaseCast getByUUID(UUID uuid) {
		return uuidMap.containsKey(uuid) ? nameMap.get(uuid) : null;
	}
	
	public static List<BaseCast> getAllCasts() {
		List<BaseCast> list = new ArrayList<>();
		list.addAll(nameMap.values());
		return list;
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
	
	public abstract boolean canUse(VitalisPlayer player); 
}
