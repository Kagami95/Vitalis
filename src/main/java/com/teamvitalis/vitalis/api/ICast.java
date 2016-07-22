package com.teamvitalis.vitalis.api;

import java.util.List;

import org.bukkit.Location;

import com.teamvitalis.vitalis.object.ClassType;

public interface ICast {
	
	/**
	 * Get the name of the cast
	 * @return Name of cast
	 */
	public String getName();
	
	/**
	 * Get the description of how to use the cast
	 * @return cast description
	 */
	public String getDescription();
	
	/**
	 * Get the {@link ClassType} of the cast
	 * @return {@link ClassType} of the cast
	 */
	public ClassType getClassType();
	
	/**
	 * Gets a list of Location for the cast. The main location should be located at index of 0.
	 * @return locations of cast
	 */
	public List<Location> getLocations();
	
	/**
	 * Get whether the ability is enabled or not
	 * @return enabled
	 */
	public boolean isEnabled();
	
	/**
	 * Loads cast
	 */
	public boolean load();
	
	/**
	 * Update cast
	 */
	public boolean progress();
	
	/**
	 * Remove cast
	 */
	public void remove();
	
}
