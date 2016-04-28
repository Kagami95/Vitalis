package com.teamvitalis.vitalis;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class Vitalis extends JavaPlugin {
	
	public static Vitalis plugin;
	public static Logger log;
	
	@Override
	public void onEnable() {
		plugin = this;
		log = this.getLogger();
	}
	
	@Override
	public void onDisable() {
		//TODO
	}

}
