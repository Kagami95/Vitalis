package com.teamvitalis.vitalis.api;

import org.bukkit.Location;

import com.teamvitalis.vitalis.object.ClassType;

public interface Ability {
	
	/**
	 * Get the name of the ability
	 * @return Name of ability
	 */
	public String getName();
	
	/**
	 * Get the description of how to use the ability
	 * @return ability description
	 */
	public String getDescription();
	
	/**
	 * Get the {@link ClassType} of the ability
	 * @return {@link ClassType} of the ability
	 */
	public ClassType getClassType();
	
	/**
	 * Get the location of the ability
	 * @return location of ability
	 */
	public Location getLocation();
	
	/**
	 * Get whether the ability is enabled or not
	 * @return enabled
	 */
	public boolean isEnabled();
	
	/**
	 * Update ability
	 */
	public boolean progress();
	
	/**
	 * Remove ability
	 */
	public void remove();
	
}
