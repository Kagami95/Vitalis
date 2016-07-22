package com.teamvitalis.vitalis.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.teamvitalis.vitalis.api.ICast;

public class AbilityDamageEntityEvent extends Event implements Cancellable{
	
	private static final HandlerList handlers = new HandlerList();
	
	private LivingEntity entity;
	private Player source;
	private double damage;
	private ICast ability;
	private boolean cancelled;

	public AbilityDamageEntityEvent(LivingEntity entity, Player source, double damage, ICast ability) {
		this.entity = entity;
		this.source = source;
		this.damage = damage;
		this.ability = ability;
	}
	
	public LivingEntity getDamaged() {
		return entity;
	}
	
	public Player getDamager() {
		return source;
	}
	
	public double getDamage() {
		return damage;
	}
	
	public ICast getAbility() {
		return ability;
	}
	
	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
