package com.teamvitalis.vitalis.abilities.pyro;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.teamvitalis.vitalis.api.Collision;
import com.teamvitalis.vitalis.api.MagicCast;
import com.teamvitalis.vitalis.object.CollisionPriority;
import com.teamvitalis.vitalis.object.MagicType;
import com.teamvitalis.vitalis.utils.DamageHandler;

public class PyroBlast extends MagicCast implements Collision{
	
	private double particleOffset = 0.3;
	private int particleAmount = 4;
	private Location loc;
	private Vector direction;
	private int currRange = 0;
	private static double damage;
	private static int range;
	private static boolean igniteGround;
	
	public PyroBlast() {
		super("PyroBlast", UUID.fromString("6868ff61-7611-4f50-9c74-5d98c43dcdf9"));
	}

	public PyroBlast(Player player) {
		super(player);
		loc = player.getEyeLocation();
		direction = player.getLocation().getDirection();
		start();
	}

	@Override
	public String getDescription() {
		return "Click to throw a blast of fire in the clicked direction. As the blast goes, it will slowly increase in size.";
	}

	@Override
	public List<Location> getLocations() {
		return Arrays.asList(loc);
	}

	@Override
	public boolean progress() {
		if (player == null) {
			return false;
		}
		
		if (!player.isOnline() || player.isDead()) {
			return false;
		}
		
		loc = loc.add(direction.multiply(1));
		currRange += 1;
		
		if (currRange >= range) {
			return false;
		}
		
		if (loc.getBlock().isLiquid()) {
			return false;
		}
		
		loc.getWorld().spawnParticle(Particle.FLAME, loc, particleAmount, particleOffset, particleOffset, particleOffset, 0.0123);
		particleAmount += 1;
		particleOffset += 0.1/3;
		return true;
		
	}

	@Override
	public MagicType getMagicType() {
		return MagicType.PYRO;
	}

	@Override
	public void onCastsCollision() {
		player.sendMessage("Collided!");
	}

	@Override
	public void onBlockCollision(Block b) {
		double length = Math.floor(particleOffset);
		for (double x = -length; x < (length + 1); x+=1) {
			for (double z = -length; z < (length + 1); z+=1) {
				Block bb = b.getLocation().add(x, 0, z).getBlock();
				for (int i = -1; i < 2; i++) {
					Block top = bb.getLocation().add(0, i, 0).getBlock();
					if (top.getRelative(BlockFace.UP).getType().equals(Material.AIR)) {
						if (igniteGround) {
							top.getRelative(BlockFace.UP).setType(Material.FIRE);
							break;
						}
					}
				}
			}
		}
	}

	@Override
	public void onEntitiesCollision(List<LivingEntity> le) {
		for (LivingEntity l : le) {
			DamageHandler.damageEntity(l, player, damage, this);
		}
	}

	@Override
	public CollisionPriority getPriority() {
		return CollisionPriority.NORMAL;
	}
	
	@Override
	public boolean removeOnCollide() {
		return true;
	}
	
	@Override
	public boolean collideWithUser() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getCollisionRadius() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public void load() {
		damage = config.getDouble("Casts.Magic.Pyro.PyroBlast.Damage");
		range = config.getInt("Casts.Magic.Pyro.PyroBlast.Range");
		igniteGround = config.getBoolean("Casts.Magic.Pyro.PyroBlast.IgniteGround");
	}
}
