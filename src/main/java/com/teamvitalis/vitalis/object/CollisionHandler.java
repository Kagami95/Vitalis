package com.teamvitalis.vitalis.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.teamvitalis.vitalis.Vitalis;
import com.teamvitalis.vitalis.api.Collision;
import com.teamvitalis.vitalis.api.CoreAbility;
import com.teamvitalis.vitalis.configuration.DefaultConfig;

public class CollisionHandler {
	
	private static double collisionRadius;
	
	private static BukkitRunnable run = new BukkitRunnable() {

		@Override
		public void run() {
			checkForCollision();
		}
		
	};
	
	public CollisionHandler() {
		collisionRadius = DefaultConfig.get().getDouble("Physics.Collisions.Radius");
		Vitalis.plugin().getServer().getScheduler().scheduleSyncRepeatingTask(Vitalis.plugin(), (Runnable) run, 0L, 1L);
	}
	
	public static boolean checkForCollision() {
		List<CoreAbility> activeAbilities = CoreAbility.getActiveAbilities();
		
		if (activeAbilities.isEmpty()) {
			return false;
		}
		
		boolean collision = false;
		for (CoreAbility ability : activeAbilities) {
			if (!(ability instanceof Collision)) {
				continue;
			}
			Bukkit.broadcastMessage("Checking for collision");
			List<CoreAbility> colliders = new ArrayList<>();
			for (CoreAbility ability2 : activeAbilities) {
				if (ability.getInstanceUUID() == ability2.getInstanceUUID()) {
					continue;
				}
				if (!(ability2 instanceof Collision)) {
					continue;
				}
				if (ability.getLocation().getWorld() != ability2.getLocation().getWorld()) {
					continue;
				}
				if (ability.getLocation().distanceSquared(ability2.getLocation()) > collisionRadius * collisionRadius) {
					continue;
				}
				colliders.add(ability2);
			}
			
			if (colliders.size() > 1) {
				runAbilitiesCollision(colliders);
				collision = true;
			}
		
			if (!collision) {
				List<Entity> entities = ability.getLocation().getWorld().getEntities();
				List<LivingEntity> newList = new ArrayList<>();
				
				for (Entity entity : entities) {
					if (entity instanceof Player && ((Player)entity).getGameMode().equals(GameMode.SPECTATOR)) {
						continue;
					} else if (entity.getLocation().clone().add(0, 1, 0).distanceSquared(ability.getLocation()) > collisionRadius * collisionRadius) {
						continue;
					} else if (!(entity instanceof LivingEntity)) {
						continue;
					} else if (entity.getEntityId() == ability.getPlayer().getEntityId()) {
						continue;
					}
					newList.add((LivingEntity)entity);
				}
				
				if (!newList.isEmpty()) {
					runEntitiesCollision(ability, newList);
					collision = true;
				}
			}
			
			if (!collision) {
				Material m = ability.getLocation().getBlock().getType();
				if (m.isSolid()) {
					runBlockCollision(ability, ability.getLocation().getBlock());
				}
			}
		}
		return true;
	}
	
	public static void runAbilitiesCollision(List<CoreAbility> abils) {
		HashMap<Collision, Integer> levels = new HashMap<>();
		HashMap<Collision, CoreAbility> map = new HashMap<>();
		for (int i = 0; i < abils.size(); i++) {
			Collision collision = (Collision) abils.get(i);
			levels.put(collision, collision.getPriority().getLevel());
			map.put(collision, abils.get(i));
		}
		
		for (int i = 5; i > 0; i--) {
			for (Collision collision : levels.keySet()) {
				if (levels.get(collision) == i) {
					collision.onAbilitiesCollision();
					if (collision.removeOnCollide()) {
						map.get(collision).remove();
					}
				}
			}
		}
	}
	
	public static void runEntitiesCollision(CoreAbility ability, List<LivingEntity> le) {
		Collision collision = (Collision) ability;
		collision.onEntitiesCollision(le);
		if (collision.removeOnCollide()) {
			ability.remove();
		}
	}
	
	public static void runBlockCollision(CoreAbility ability, Block b) {
		Collision collision = (Collision) ability;
		collision.onBlockCollision(b);
		if (collision.removeOnCollide()) {
			ability.remove();
		}
	}
	
	public static BukkitRunnable getRunnable() {
		return run;
	}
}
