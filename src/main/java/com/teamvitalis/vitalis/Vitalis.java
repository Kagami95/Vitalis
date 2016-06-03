package com.teamvitalis.vitalis;

import java.util.logging.Logger;

import com.teamvitalis.vitalis.database.Database;
import org.bukkit.plugin.java.JavaPlugin;

import com.teamvitalis.vitalis.listeners.GuiListener;

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
	}
	
	@Override
	public void onDisable() {
		//TODO
	}

	public static Database getVitalisDatabase() {
		return database;
	}

}
