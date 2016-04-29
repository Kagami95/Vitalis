package com.teamvitalis.vitalis.configuration;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

public class LangConfig {

	private static Config lang;

	public LangConfig() {
		lang = new Config(new File("lang.yml"));
		init();
	}

	private void init() {
		FileConfiguration config = lang.get();
		config.addDefault("example", "&fhello &6%player");
		lang.save();
	}

	public static FileConfiguration get() {
		return lang.get();
	}

	public static void reload() {
		lang.reload();
	}
}
