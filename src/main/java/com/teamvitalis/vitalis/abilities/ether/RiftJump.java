package com.teamvitalis.vitalis.abilities.ether;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.teamvitalis.vitalis.Vitalis;
import com.teamvitalis.vitalis.api.Collision;
import com.teamvitalis.vitalis.api.MagicAbility;
import com.teamvitalis.vitalis.object.CollisionPriority;
import com.teamvitalis.vitalis.object.MagicType;

public class RiftJump extends MagicAbility implements Collision{
	
	private List<Location> locs = new ArrayList<>();
	private int range;
	private long duration;
	private Rift one, two;

	public RiftJump(Player player) {
		super(player);
		range = Vitalis.config().get().getInt("Abilities.Magic.Ether.RiftJump.Range");
		duration = Vitalis.config().get().getLong("Abilities.Magic.Ether.RiftJump.Duration");
		one = new Rift(player.getEyeLocation());
		two = new Rift(player.getTargetBlock((Set<Material>)null, range).getLocation());
		locs.add(one.getLocation());
		locs.add(two.getLocation());
		start();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "RiftJump";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Open two rifts between dimensions that allow you to jump through space.";
	}

	@Override
	public List<Location> getLocations() {
		// TODO Auto-generated method stub
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
		
		if (player.getWorld() != one.getLocation().getWorld()) {
			return false;
		}
		
		if (getStartTime() + duration > System.currentTimeMillis()) {
			return false;
		}
		
		one.display(true);
		two.display(false);
		return true;
	}

	@Override
	public void onAbilitiesCollision() {
		
	}

	@Override
	public void onBlockCollision(Block b) {
		
	}

	@Override
	public void onEntitiesCollision(List<LivingEntity> le) {
		for (LivingEntity l : le) {
			Vector velocity = l.getVelocity().clone();
			Vector direction = l.getLocation().getDirection().clone();
			l.teleport(two.getLocation().clone().setDirection(direction));
			l.setVelocity(velocity);
		}
	}

	@Override
	public CollisionPriority getPriority() {
		// TODO Auto-generated method stub
		return CollisionPriority.HIGH;
	}

	@Override
	public boolean removeOnCollide() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MagicType getMagicType() {
		// TODO Auto-generated method stub
		return MagicType.ETHER;
	}

	public class Rift {
		
		private Location loc;
		
		public Rift(Location loc) {
			this.loc = loc;
		}
		
		public Location getLocation() {
			return loc;
		}
		
		public void display(boolean start) {
			if (start) {
				loc.getWorld().spawnParticle(Particle.DRAGON_BREATH, loc, 5, 0.3, 0.3, 0.3, 0);
				loc.getWorld().spawnParticle(Particle.END_ROD, loc, 3, 0.3, 0.3, 0.3, 0);
			} else {
				loc.getWorld().spawnParticle(Particle.DRAGON_BREATH, loc, 5, 0.3, 0.3, 0.3, 0);
			}
		}
	}

	@Override
	public double getCollisionRadius() {
		// TODO Auto-generated method stub
		return 1;
	}
}
