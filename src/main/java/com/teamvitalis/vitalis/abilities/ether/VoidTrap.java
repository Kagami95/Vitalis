package com.teamvitalis.vitalis.abilities.ether;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.teamvitalis.vitalis.Vitalis;
import com.teamvitalis.vitalis.api.MagicCast;
import com.teamvitalis.vitalis.object.MagicType;
import com.teamvitalis.vitalis.utils.BlockUtils;
import com.teamvitalis.vitalis.utils.ParticleEffect;

public class VoidTrap extends MagicCast {
	
	private Location loc;
	private enum VoidTrapState {CHARGING, OPENING, OPEN, CLOSING, IDLE};
	private VoidTrapState state;
	private long startTime;
	private double radius;
	private Map<Block, MaterialData> blocks = new HashMap<Block, MaterialData>();
	
	
	public static long chargeTime = 6000L;
	public static long openTime = 2000L;
	public static long duration = 9000L;
	public static long dimensionTime = 30000L;
	public static double maxRadius = 5;
	public static double suctionPower = 1;
	public static int suctionAmount = 5;
	private static Random rand = new Random();
	
	public static Map<Block, Player> portalBlocks = new ConcurrentHashMap<Block, Player>();
	public static Map<Player, Long> trappedPlayers = new ConcurrentHashMap<Player, Long>();
	
	public VoidTrap() {
		super("VoidTrap", UUID.fromString("56486fb9-70ab-47e1-9e69-b7e99375e154"));
	}
	
	public VoidTrap(Player player) {
		super(player);
		
		this.loc = player.getTargetBlock((HashSet<Material>) null, 6).getRelative(BlockFace.UP).getLocation();
		this.radius = 0;
		this.state = VoidTrapState.CHARGING;
		this.startTime = System.currentTimeMillis();
		player.sendMessage("Start");
		start();
		return;
		
		//Vector direction = player.getLocation().clone().getDirection().normalize();
		/*for (int i = 6; i > 0; i--) {
			if (fixLocation(direction.clone().multiply(i).toLocation(player.getWorld())) != null) {
				this.loc = fixLocation(player.getLocation().clone().add(direction.clone().multiply(i))).getLocation();
				
				this.state = VoidTrapState.CHARGING;
				this.startTime = System.currentTimeMillis();
				player.sendMessage("Start");
				start();
				return;
			}
		}*/
	}
	
	public static Block fixLocation(Location location) {
		Block b = location.getBlock();
		if (BlockUtils.isTransparent(b)) {
			Bukkit.broadcastMessage("BU: isTransparent " + b.toString());
			for (int i = 0; i < 3; i++) {
				if (BlockUtils.isTransparent(b) && !BlockUtils.isTransparent(b.getRelative(BlockFace.DOWN))) {
					Bukkit.broadcastMessage("BU: Bellow is solid");
					return b;
				}
				b = b.getRelative(BlockFace.DOWN);
			}
		} else if (BlockUtils.isTransparent(b.getRelative(BlockFace.UP))) {
			Bukkit.broadcastMessage("BU: Above is transparent");
			return b.getRelative(BlockFace.UP);
		}
		return null;
		
	}
	
	@SuppressWarnings("deprecation")
	public static void trap(Entity entity) {
		Bukkit.broadcastMessage("Trapped");
		if (entity instanceof Player) {
			Player p = (Player) entity;
			p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, (int)(dimensionTime / 50), 0, true, false));
			int x = rand.nextInt(30) - 15;
			int z = rand.nextInt(30) - 15;
			entity.teleport(entity.getLocation().add(x, entity.getWorld().getHighestBlockYAt(entity.getLocation()), z));
			p.sendMessage(ChatColor.LIGHT_PURPLE + "You have been prisoned into a space beyond time itself!");
			p.sendMessage(ChatColor.LIGHT_PURPLE + "Hit rapidly to break the dimensional barrier and return to your world!");
			for (int bx = -1; bx < 1; bx++) {
				for (int by = -1; by < 2; by++) {
					for (int bz = -1; bz < 1; bz++) {
						p.sendBlockChange(p.getLocation().add(bx, by, bz), Material.WOOL, (byte)15);
					}
				}
			}
			trappedPlayers.put(p, System.currentTimeMillis() + dimensionTime);
		} else if (entity instanceof LivingEntity) {
			LivingEntity le = (LivingEntity) entity;
			le.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, (int)(dimensionTime / 50), 0, true, false));
			le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int)(dimensionTime / 50), 5, true, false));
			le.setSilent(true);
			le.setAI(false);
			new BukkitRunnable() {
				@Override
				public void run() {
					le.setAI(true);
					le.setSilent(false);
				}
			}.runTaskLater(Vitalis.plugin(), (int)(dimensionTime / 50));
			Random random = new Random();
			int x = random.nextInt(30) - 15;
			int z = random.nextInt(30) - 15;
			entity.teleport(entity.getLocation().add(x, entity.getWorld().getHighestBlockYAt(entity.getLocation()), z));
		} else {
			Random random = new Random();
			int x = random.nextInt(30) - 15;
			int z = random.nextInt(30) - 15;
			entity.teleport(entity.getLocation().add(x, 200, z));
		}
	}

	@Override
	public String getName() {
		return "VoidTrap";
	}

	@Override
	public String getDescription() {
		return "Create a black hole that sucks all living things into the void. All things sucked into the void must break out by smashing the dimensional barrier (hit rapidy lots). Hold Sneak to charge until a black hole forms.";
	}

	@Override
	public Location getLocation() {
		return this.loc;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean progress() {
		Bukkit.broadcastMessage("Progress1");
		if (!vPlayer.canUse("VoidTrap")) {
			Bukkit.broadcastMessage("Progress Exit: canUse");
			remove();
			return false;
		}
		
		if (state == VoidTrapState.CHARGING) {
			if (!player.isSneaking()) {
				ParticleEffect.SMOKE_LARGE.display(0.5F, 0.5F, 0.5F, 0.5F, 10, player.getLocation(), 32D);
				remove();
				Bukkit.broadcastMessage("Progress Exit: not sneaking");
				return false;
			}
			
			if (System.currentTimeMillis() > this.startTime + chargeTime) {
				this.state = VoidTrapState.OPENING;
			} else {
				ParticleEffect.SPELL_WITCH.display(0.5F, 0.5F, 0.5F, 0.5F, 3, player.getLocation(), 32D);
			}
		} else if (state == VoidTrapState.OPENING) {
			if (!player.isSneaking()) {
				ParticleEffect.SMOKE_LARGE.display(0.5F, 0.5F, 0.5F, 0.5F, 10, player.getLocation(), 32D);
				remove();
				Bukkit.broadcastMessage("Progress Exit: not sneaking");
				return false;
			}
			
			Bukkit.broadcastMessage("Opening radius " + radius);
			double deg = 360 / (radius * 8);
			for (double i = 0; i < radius * 8; i++) {
				double angle = deg * i + (radius * 3);
				double x = Math.sin(Math.toRadians(angle));
				double z = Math.cos(Math.toRadians(angle));
				ParticleEffect.SPELL_WITCH.display(0, 0, 0, 0.5F, 1, this.getLocation().clone().add(x, 0, z));
			}
			this.radius = this.radius + 0.1D;
			//radius = Math.round(radius * 10) / 10;
			
			if (radius >= 1.5 && radius * 10 % 5 == 0) {
				double radius2 = radius - 1;
				for (double dx = -radius2; dx < radius2; dx++) {
					for (double dz = -radius2; dz < radius2; dz++) {
						if (dx * dx + dz * dz <= radius2 * radius2) {
							Block b = this.getLocation().clone().add(dx, 0, dz).getBlock();
							if (!blocks.containsKey(b)) {
								blocks.put(b, b.getState().getData());
							}
							b.setType(Material.WOOL);
							b.setData((byte)15);
						}
						
					}
				}
			}
			if (radius >= 3 && radius * 10 % 5 == 0) {
				double radius2 = radius - 2;
				for (double dx = -radius2; dx < radius2; dx++) {
					for (double dz = -radius2; dz < radius2; dz++) {
						if (dx * dx + dz * dz <= radius2 * radius2) {
							Block b = this.getLocation().clone().add(dx, 0, dz).getBlock();
							if (blocks.containsKey(b)) {
								b.setType(Material.ENDER_PORTAL);
								portalBlocks.put(b, getPlayer());
							}
							
						}
						
					}
				}
			}
			
			if (radius >= maxRadius) {
				this.state = VoidTrapState.OPEN;
				this.startTime = System.currentTimeMillis();
			}
		} else if (state == VoidTrapState.OPEN) {
			if (this.startTime + duration > System.currentTimeMillis()) {
				this.state = VoidTrapState.CLOSING;
			}
			
			double range = radius * 1.5 * suctionPower;
			for (int i = 0; i < suctionAmount; i++) {
				double x = rand.nextInt((int) (range * 2)) + rand.nextDouble() - range;
				double z = rand.nextInt((int) (range * 2)) + rand.nextDouble() - range;
				double y = range * 2 - (Math.abs(x) + Math.abs(z)) / 2; 
				Location suctionLoc = this.loc.clone().add(x, y, z);
				
				Vector vec = new Vector(suctionLoc.getX() - this.loc.getX(), suctionLoc.getY() - this.loc.getY(), suctionLoc.getZ() - this.loc.getZ());
				
				ParticleEffect.SMOKE_LARGE.display(vec, (float) suctionPower, suctionLoc, 256);
			}
		} else if (state == VoidTrapState.CLOSING) {
			remove();
			Bukkit.broadcastMessage("Progress Exit: closing");
			return false;
			
		}
		
		return true;
	}

	@Override
	public MagicType getMagicType() {
		return MagicType.ETHER;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void remove() {
		super.remove();
		
		for (int i = blocks.size() - 1; i > -1; i--) {
			final Block block = (Block) blocks.keySet().toArray()[i];
			new BukkitRunnable() {
				@Override
				public void run() {
					
				}
			}.runTaskLater(Vitalis.plugin(), (long) (i / 5));

			block.setType(blocks.get(block).getItemType());
			block.setData(blocks.get(block).getData());
		}
	}
	
	@Override
	public int getTickRate() {
		return 1;
	}

	@Override
	public List<Location> getLocations() {
		return null;
	}

	@Override
	public void load() {
		
	}

}
