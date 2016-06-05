package com.teamvitalis.vitalis.api;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import com.teamvitalis.vitalis.object.VitalisPlayer;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public abstract class CoreAbility implements Ability{

	private static ConcurrentHashMap<Player, CoreAbility> playerInstances = new ConcurrentHashMap<>();
	private static HashMap<String, CoreAbility> abilityMap = new HashMap<>();
	private static HashMap<Class<? extends CoreAbility>, CoreAbility> classMap = new HashMap<>();
	
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
		Class<? extends CoreAbility> clase = getClass();
		
		if (!playerInstances.containsKey(player)){
			playerInstances.put(player, classMap.get(clase));
		}
	}
	
	public void remove() {
		if (player == null) {
			return;
		}
		
		playerInstances.remove(player);
	}
	
	public void registerPluginAbilities(JavaPlugin plugin, String packageName) {
		List<String> disabled = new ArrayList<String>();
		if (!plugin.isEnabled()) {
			return;
		}
		
		Class<?> pluginClass = plugin.getClass();
		ClassLoader loader = pluginClass.getClassLoader();
		
		try {
			for (final ClassInfo info : ClassPath.from(loader).getAllClasses()) {
				if (!info.getPackageName().startsWith(packageName)) {
					continue;
				}
				
				Class<?> clase = info.getClass();
				if (!CoreAbility.class.isAssignableFrom(clase) || clase.isInterface() || Modifier.isAbstract(clase.getModifiers())) {
					continue;
				}
				
				Constructor<?> objDef = CoreAbility.class.getDeclaredConstructor();
				CoreAbility ability = (CoreAbility) clase.cast(objDef.newInstance());
				
				if (ability == null || ability.getName() == null) {
					continue;
				} else if (!ability.isEnabled() && !disabled.contains(ability.getName())) {
					disabled.add(ability.getName());
					continue;
				}
				
				abilityMap.put(ability.getName(), ability);
				classMap.put(ability.getClass(), ability);
				
				if (ability instanceof AddonAbility) {
					AddonAbility addon = (AddonAbility) ability;
					addon.load();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
