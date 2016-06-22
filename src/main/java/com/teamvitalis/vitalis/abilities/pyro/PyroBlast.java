package com.teamvitalis.vitalis.abilities.pyro;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.teamvitalis.vitalis.Vitalis;
import com.teamvitalis.vitalis.api.Collision;
import com.teamvitalis.vitalis.api.MagicAbility;
import com.teamvitalis.vitalis.object.CollisionPriority;
import com.teamvitalis.vitalis.object.MagicType;
import com.teamvitalis.vitalis.utils.DamageHandler;

public class PyroBlast extends MagicAbility implements Collision{
	
	private double particleOffset = 0.3;
	private int particleAmount = 4;
	private Location loc;
	private Vector direction;
	private double damage;
	private int range, currRange = 0;
	private boolean igniteGround;

	public PyroBlast(Player player) {
		super(player);
		loadVariables();
		start();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "PyroBlast";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Click to throw a blast of fire in the clicked direction. As the blast goes, it will slowly increase in size.";
	}

	@Override
	public List<Location> getLocations() {
		List<Location> locs = new ArrayList<>();
		locs.add(loc);
		return locs;
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
		particleOffset += 0.025;
		return true;
	}

	@Override
	public MagicType getMagicType() {
		// TODO Auto-generated method stub
		return MagicType.PYRO;
	}

	@Override
	public void onAbilitiesCollision() {
		
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

	private void loadVariables() {
		this.loc = player.getEyeLocation();
		this.direction = player.getLocation().getDirection();
		this.damage = Vitalis.config().get().getDouble("Abilities.Magic.Pyro.PyroBlast.Damage");
		this.range = Vitalis.config().get().getInt("Abilities.Magic.Pyro.PyroBlast.Range")*4;
		this.igniteGround = Vitalis.config().get().getBoolean("Abilities.Magic.Pyro.PyroBlast.IgniteGround");
	}

	@Override
	public double getCollisionRadius() {
		// TODO Auto-generated method stub
		return 2;
	}
}
