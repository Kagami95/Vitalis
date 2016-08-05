package com.teamvitalis.vitalis.casts.luna;

import com.teamvitalis.vitalis.Vitalis;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Kagami95 "Carbogen" on 04/08/2016.
 */
public class Empowerment {
	public static PotionEffectType[] effects = new PotionEffectType[] {
		PotionEffectType.DAMAGE_RESISTANCE, PotionEffectType.INCREASE_DAMAGE, PotionEffectType.SPEED, PotionEffectType.JUMP
	};

	public static int getEffect(Player p) {
		int phase = LunaMethods.getLunarPhase(p.getWorld());

		int effect = (--phase < 1) ? 0 : phase;
		return effect;
	}

	public static void apply(Player p) {
		for (PotionEffectType type : effects)
			p.addPotionEffect(new PotionEffect(type, 30, getEffect(p)), true);
	}

	public static void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (LunaMethods.isLunamancer(p) && LunaMethods.getLunarPhase(p.getWorld()) > 1) {
				apply(p);
			}
		}
	}
}
