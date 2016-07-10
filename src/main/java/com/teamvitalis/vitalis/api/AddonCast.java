package com.teamvitalis.vitalis.api;

public interface AddonCast {

	/**
	 * Returns the author of the ability
	 * @return author name
	 */
	public String getAuthor();
	
	/**
	 * Returns the version of the ability
	 * @return ability version
	 */
	public String getVersion();
	
	/**
	 * called to load the addon
	 */
	public void load();
	
	/**
	 * called to stop the addon
	 */
	public void stop();
}
