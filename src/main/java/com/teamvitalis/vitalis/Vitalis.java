package com.teamvitalis.vitalis;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.teamvitalis.vitalis.api.CoreAbility;
import com.teamvitalis.vitalis.database.Database;
import com.teamvitalis.vitalis.listeners.AbilityListener;
import com.teamvitalis.vitalis.listeners.GuiListener;
import com.teamvitalis.vitalis.listeners.PlayerListener;
import com.teamvitalis.vitalis.object.AbilityLoader;

public class Vitalis extends JavaPlugin {
	
	private static Vitalis plugin;
	private static Logger log;

	private static Database database;
	
	@Override
	public void onEnable() {
		plugin = this;
		log = this.getLogger();
		Database.initiateFile();
		database = new Database();
		
		new GuiListener(this);
		new PlayerListener(this);
		new AbilityListener(this);
		
		//Load internal abilities
		AbilityLoader loader = new AbilityLoader(this);
		loader.loadAbilities("com.teamvitalis.vitalis.abilities");
	}
	
	@Override
	public void onDisable() {
		//TODO
		plugin = null;
		log = null;
		database = null;
		
		CoreAbility.removeAll();
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
}
