package com.teamvitalis.vitalis.casts.luna;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by Kagami95 "Carbogen" on 04/08/2016.
 */
public class Longevity {
	public static int getEffect(Player p) {
		int phase = LunaMethods.getLunarPhase(p.getWorld());
		return (6 - (6 - phase * 2)) * 2;
	}

	public static void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (LunaMethods.isLunamancer(p)) {
				int effect = getEffect(p);
				if (p.getMaxHealth() != (20 + effect)) {
					p.setMaxHealth(20 + effect);
					p.sendMessage(ChatColor.GRAY + "A change in moonlight has changed your health buffer to " + p.getMaxHealth());
				}
			}
		}
	}
}
