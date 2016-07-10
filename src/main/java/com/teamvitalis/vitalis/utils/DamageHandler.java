package com.teamvitalis.vitalis.utils;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.teamvitalis.vitalis.api.ICast;
import com.teamvitalis.vitalis.events.AbilityDamageEntityEvent;

public class DamageHandler {

	@SuppressWarnings("deprecation")
	public static void damageEntity(LivingEntity entity, Player source, double damage, ICast abil) {
		double finalDmg = damage;
		AbilityDamageEntityEvent event = new AbilityDamageEntityEvent(entity, source, damage, abil);
		if (event.isCancelled()) {
			return;
		}
		finalDmg = event.getDamage();
		entity.damage(finalDmg);
		entity.setLastDamageCause(new EntityDamageEvent(entity, DamageCause.CUSTOM, finalDmg));
	}
}
