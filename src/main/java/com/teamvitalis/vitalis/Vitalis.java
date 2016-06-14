package com.teamvitalis.vitalis;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.teamvitalis.vitalis.commands.CommandLoader;
import com.teamvitalis.vitalis.configuration.Config;
import com.teamvitalis.vitalis.configuration.LangConfig;
import com.teamvitalis.vitalis.database.Database;
import com.teamvitalis.vitalis.listeners.AbilityListener;
import com.teamvitalis.vitalis.listeners.GuiListener;
import com.teamvitalis.vitalis.listeners.PlayerListener;
import com.teamvitalis.vitalis.object.AbilityLoader;

public class Vitalis extends JavaPlugin {
	
	private static Vitalis plugin;
	private static Logger log;
	private static Config config;
	private static LangConfig lang;
	private static Database database;
	
	@Override
	public void onEnable() {
		plugin = this;
		log = this.getLogger();
		config = new Config(new File("config.yml"));
		lang = new LangConfig();
		Database.initiateFile();
		database = new Database();
		
		new GuiListener(this);
		new PlayerListener(this);
		new AbilityListener(this);
	
		new CommandLoader(this).loadCommands();
		new AbilityLoader(this).loadAbilities("com.teamvitalis.vitalis.abilities.");
	}
	
	@Override
	public void onDisable() {
		//TODO
		plugin = null;
		log = null;
		database = null;
	}

	public static Database database() {
		return database;
	}

	public static Vitalis plugin() {
		return plugin;
	}
	
	public static Logger logger() {
		return log;
	}
	
	public static LangConfig langConfig() {
		return lang;
	}
	
	public static Config config() {
		return config;
	}
}
