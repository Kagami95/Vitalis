package com.teamvitalis.vitalis.casts.luna;

import com.teamvitalis.vitalis.Vitalis;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.inventivetalent.glow.GlowAPI;

/**
 * Created by Kagami95 "Carbogen" on 04/08/2016.
 */
public class SilverShield {

	private static float shieldRadius = 1.5f;
	private static long flashTime = 250L;

	public static Vector reverseVelocity(Vector v) {
		return v.multiply(-1.0).normalize().multiply(1.4);
	}

	public static void deflect(Player p) {
		for (Entity en : p.getWorld().getEntities()) {
			if (en instanceof Arrow) {
				Arrow arrow = (Arrow) en;
				Location loc = arrow.getLocation();
				Vector v = arrow.getVelocity();
				for (float i = 1; i < 4.; i += 0.5) {
					double d = loc.add(v.normalize().multiply(i)).distance(p.getLocation());
					if (d < shieldRadius) {
						if (arrow.getLocation().distance(p.getLocation()) > 4.0 || arrow.isOnGround() || arrow.getShooter() == p)
							continue;
						arrow.setShooter(p);
						arrow.setVelocity(reverseVelocity(arrow.getVelocity()));
						p.getWorld().playSound(loc, Sound.ENTITY_ITEM_BREAK, 1, 1);
						if (Vitalis.hasHook("GlowAPI")) {
							Vitalis.plugin().getServer().getScheduler().runTaskAsynchronously(Vitalis.plugin(), new Runnable() {
								@Override
								public void run() {
									GlowAPI.setGlowing(p, GlowAPI.Color.WHITE, Bukkit.getOnlinePlayers());
									long time = System.currentTimeMillis();
									while (System.currentTimeMillis() < time + flashTime) {
									}
									GlowAPI.setGlowing(p, false, Bukkit.getOnlinePlayers());
								}
							});
						}
					}
				}
			}
		}
	}

	public static void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (LunaMethods.isLunamancer(p) && LunaMethods.getLunarPhase(p.getWorld()) == 4) {
				deflect(p);
			}
		}
	}
}
