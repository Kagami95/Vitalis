package com.teamvitalis.vitalis;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.teamvitalis.vitalis.database.Database;
import com.teamvitalis.vitalis.listeners.GuiListener;
import com.teamvitalis.vitalis.listeners.PlayerListener;
import com.teamvitalis.vitalis.object.AbilityLoader;

public class Vitalis extends JavaPlugin {
	
	public static Vitalis plugin;
	public static Logger log;

	private static Database database;
	
	@Override
	public void onEnable() {
		plugin = this;
		log = this.getLogger();
		database = new Database();
		
		new GuiListener(this);
		new PlayerListener(this);
		
		//Load internal abilities
		AbilityLoader loader = new AbilityLoader(this);
		loader.loadAbilities("com.teamvitalis.vitalis.abilities");
	}
	
	@Override
	public void onDisable() {
		//TODO
	}

	public static Database getVitalisDatabase() {
		return database;
	}

}
