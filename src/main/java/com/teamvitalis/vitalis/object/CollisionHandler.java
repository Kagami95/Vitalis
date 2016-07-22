package com.teamvitalis.vitalis.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.teamvitalis.vitalis.Vitalis;
import com.teamvitalis.vitalis.api.Collision;
import com.teamvitalis.vitalis.api.BaseCast;

public class CollisionHandler {
	
	private static double collisionRadius;
	
	private static BukkitRunnable run = new BukkitRunnable() {

		@Override
		public void run() {
			checkForCollision();
		}
		
	};
	
	public CollisionHandler() {
		Vitalis.plugin().getServer().getScheduler().scheduleSyncRepeatingTask(Vitalis.plugin(), (Runnable) run, 0L, 1L);
	}
	
	public static boolean checkForCollision() {
		List<BaseCast> activeAbilities = BaseCast.getActiveCasts();
		List<BaseCast> clone = BaseCast.getActiveCasts();
		
		if (activeAbilities.isEmpty()) {
			return false;
		}
		
		for (BaseCast cast : activeAbilities) {
			clone.remove(cast);
			if (!(cast instanceof Collision)) {
				continue;
			}
			
			Collision collider = (Collision) cast;
			collisionRadius = collider.getCollisionRadius();
			boolean collision = false;
			
			//Casts collision
			List<BaseCast> colliders = new ArrayList<>();
			colliders.add(cast);
			for (BaseCast ability2 : clone) {
				if (cast.getPlayer() == ability2.getPlayer()) {
					continue;
				}
				if (!(ability2 instanceof Collision)) {
					continue;
				}
				Collision collider2 = (Collision) ability2;
				if (cast.getLocation().distanceSquared(ability2.getLocation()) > collisionRadius * collisionRadius && ability2.getLocation().distanceSquared(cast.getLocation()) > collider2.getCollisionRadius() * collider2.getCollisionRadius()) {
					continue;
				}
				colliders.add(ability2);
			}
			
			if (colliders.size() > 1) {
				runCastsCollision(colliders);
				collision = true;
			}
		
			//Cast v Entities collision
			if (!collision) {
				List<Entity> entities = cast.getLocation().getWorld().getEntities();
				List<LivingEntity> newList = new ArrayList<>();
				
				for (Entity entity : entities) {
					if (entity instanceof Player && ((Player)entity).getGameMode().equals(GameMode.SPECTATOR)) {
						continue;
					} else if (entity.getLocation().clone().add(0, 1, 0).distanceSquared(cast.getLocation()) > collisionRadius * collisionRadius) {
						continue;
					} else if (!(entity instanceof LivingEntity)) {
						continue;
					} else if (!collider.collideWithUser() && entity.getEntityId() == cast.getPlayer().getEntityId()) {
						continue;
					}
					newList.add((LivingEntity)entity);
				}
				
				if (!newList.isEmpty()) {
					runEntitiesCollision(cast, newList);
					collision = true;
				}
			}
			
			//Cast v Block collision
			if (!collision) {
				Material m = cast.getLocation().getBlock().getType();
				if (m.isSolid()) {
					runBlockCollision(cast, cast.getLocation().getBlock());
				}
			}
		}
		return true;
	}
	
	public static void runCastsCollision(List<BaseCast> abils) {
		HashMap<Collision, Integer> levels = new HashMap<>();
		HashMap<Collision, BaseCast> map = new HashMap<>();
		for (int i = 0; i < abils.size(); i++) {
			Collision collision = (Collision) abils.get(i);
			levels.put(collision, collision.getPriority().getLevel());
			map.put(collision, abils.get(i));
		}
		
		for (int i = 5; i > 0; i--) {
			for (Collision collision : levels.keySet()) {
				if (levels.get(collision) == i) {
					collision.onCastsCollision();
					if (collision.removeOnCollide()) {
						map.get(collision).remove();
					}
					levels.remove(collision);
				}
			}
		}
	}
	
	public static void runEntitiesCollision(BaseCast ability, List<LivingEntity> le) {
		Collision collision = (Collision) ability;
		collision.onEntitiesCollision(le);
		if (collision.removeOnCollide()) {
			ability.remove();
		}
	}
	
	public static void runBlockCollision(BaseCast ability, Block b) {
		Collision collision = (Collision) ability;
		collision.onBlockCollision(b);
		if (collision.removeOnCollide()) {
			ability.remove();
		}
	}
	
	public static BukkitRunnable getRunnable() {
		return run;
	}
	
	public static double getCurrentCollisionRadius() {
		return collisionRadius;
	}
}
