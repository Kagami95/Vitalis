package com.teamvitalis.vitalis;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.teamvitalis.vitalis.casts.GlobalPassives;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.teamvitalis.vitalis.api.BaseCast;
import com.teamvitalis.vitalis.commands.Commands;
import com.teamvitalis.vitalis.configuration.Configs;
import com.teamvitalis.vitalis.database.DBMethods;
import com.teamvitalis.vitalis.database.Database;
import com.teamvitalis.vitalis.listeners.CastListener;
import com.teamvitalis.vitalis.listeners.GuiListener;
import com.teamvitalis.vitalis.listeners.PlayerListener;
import com.teamvitalis.vitalis.object.CollisionHandler;
import com.teamvitalis.vitalis.object.VitalisPlayer;

public class Vitalis extends JavaPlugin {
	
	private static Vitalis plugin;
	private static Logger log;
	private static Database database;
	private static List<String> hooks;

	@Override
	public void onEnable() {
		plugin = this;
		log = this.getLogger();
		new Configs();
		Database.initiateFile();
		database = new Database();
		new DBMethods(database).configureDatabase();

		hooks = new ArrayList<>();
		for (String name : new String[] {"GlowAPI", "PacketListenerApi"}) { // Add Optional Hooks here
			if (getServer().getPluginManager().isPluginEnabled(name)) {
				logger().info("Found plugin hook \"" + name + "\"!");
				hooks.add(name);
			}
		}

		for (Player player : Bukkit.getOnlinePlayers()) {
			VitalisPlayer.load(player);
		}
		
		new GuiListener(this);
		new PlayerListener(this);
		new CastListener(this);
	
		new Commands(this).loadCommands();
		BaseCast.loadAll();
		new CollisionHandler();

		GlobalPassives.start();
	}
	
	@Override
	public void onDisable() {
		DBMethods.close();
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

	public static boolean hasHook(String name) { return hooks.contains(name); }
}
