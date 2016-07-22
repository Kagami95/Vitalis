package com.teamvitalis.vitalis.api;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;

import com.teamvitalis.vitalis.object.CollisionPriority;

public interface Collision {

	public void onCastsCollision();
	
	public void onBlockCollision(Block b);
	
	public void onEntitiesCollision(List<LivingEntity> le);
	
	public CollisionPriority getPriority();
	
	public boolean removeOnCollide();
	
	public boolean collideWithUser();
	
	public double getCollisionRadius();
}
