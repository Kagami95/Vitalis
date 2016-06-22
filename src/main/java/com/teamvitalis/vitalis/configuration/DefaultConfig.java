package com.teamvitalis.vitalis.configuration;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

public class DefaultConfig {
	
	private static Config config;

	public DefaultConfig() {
		config = new Config(new File("config.yml"));
		init();
	}
	
	public void init() {
		FileConfiguration c = config.get();
		
		c.addDefault("Physics.Collisions.Radius", 2);
		
		config.save();
	}
	
	public static FileConfiguration get() {
		return config.get();
	}
	
	public static void reload() {
		config.reload();
	}
	
	public static void save() {
		config.save();
	}
}
