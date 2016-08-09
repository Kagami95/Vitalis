package com.teamvitalis.vitalis.api;

import org.bukkit.Location;

import com.teamvitalis.vitalis.object.CastInfo;

public interface ICast {
	
	/**
	 * Get the {@link CastInfo} for the cast
	 * @return {@link CastInfo} of cast
	 */
	public CastInfo getInfo();
	
	/**
	 * Gets the Location for the cast.
	 * @return location of cast
	 */
	public Location getLocation();
	
	/**
	 * Update cast
	 */
	public boolean progress();
	
	/**
	 * Remove cast
	 */
	public void remove();
	
}
