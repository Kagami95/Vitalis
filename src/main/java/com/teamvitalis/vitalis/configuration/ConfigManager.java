package com.teamvitalis.vitalis.configuration;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
	
	private static Config config;

	public ConfigManager() {
		config = new Config(new File("config.yml"));
		init();
	}
	
	public void init() {
		FileConfiguration c = config.get();
		
		c.addDefault("Abilities.Magic.Pyro.PyroBlast.Enabled", true);
		c.addDefault("Abilities.Magic.Pyro.PyroBlast.Damage", 2);
		c.addDefault("Abilities.Magic.Pyro.PyroBlast.Range", 15);
		c.addDefault("Abilities.Magic.Pyro.PyroBlast.IgniteGround", true);
		
		c.addDefault("Abilities.Magic.Pyro.TestAbility.Enabled", true);
		
		c.addDefault("Abilities.Magic.Ether.RiftJump.Range", 20);
		c.addDefault("Abilities.Magic.Ether.RiftJump.Duration", 10000);
		
		config.save();
	}
	
	public Config getConfig() {
		return config;
	}
}
