package com.teamvitalis.vitalis;

import com.teamvitalis.vitalis.listeners.GuiListener;
import com.teamvitalis.vitalis.listeners.PlayerListener;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Vitalis extends JavaPlugin {
	
	public static Vitalis plugin;
	public static Logger log;
	
	@Override
	public void onEnable() {
		plugin = this;
		log = this.getLogger();
		
		new GuiListener(this);
		new PlayerListener(this);
	}
	
	@Override
	public void onDisable() {
		//TODO
	}

}
