package com.teamvitalis.vitalis.configuration;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

public class CastConfig {
	
	private static Config config;

	public CastConfig() {
		config = new Config(new File("cast.yml"));
		init();
	}
	
	public void init() {
		FileConfiguration c = config.get();
		
		c.addDefault("Casts.Magic.Pyro.PyroBlast.Enabled", true);
		c.addDefault("Casts.Magic.Pyro.PyroBlast.Damage", 2);
		c.addDefault("Casts.Magic.Pyro.PyroBlast.Range", 15);
		c.addDefault("Casts.Magic.Pyro.PyroBlast.IgniteGround", true);
		
		c.addDefault("Casts.Magic.Pyro.TestAbility.Enabled", true);
		
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
