package com.teamvitalis.vitalis.configuration;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.teamvitalis.vitalis.Vitalis;

public class Config {

	Vitalis plugin;

	private File file;
	public FileConfiguration config;

	public Config(File file) {
		this.plugin = Vitalis.plugin();
		this.file = new File(plugin.getDataFolder() + File.separator + file);
		this.config = YamlConfiguration.loadConfiguration(this.file);
		reload();
	}

	public void create() {
		if (!file.getParentFile().exists()) {
			try {
				file.getParentFile().mkdir();
			} catch (Exception e) {
				plugin.getLogger().info("Failed to generate directory!");
				e.printStackTrace();
			}
		}

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				plugin.getLogger().info("Failed to generate " + file.getName() + "!");
				e.printStackTrace();
			}
		}
	}

	public FileConfiguration get() {
		return config;
	}
	
	public void reload() {
		create();
		try {
			config.load(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save() {
		try {
			config.options().copyDefaults(true);
			config.save(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean delete() {
		try {
			if (file.delete()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}