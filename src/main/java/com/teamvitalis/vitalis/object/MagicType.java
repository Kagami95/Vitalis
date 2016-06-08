package com.teamvitalis.vitalis.object;

import java.util.HashMap;

import org.bukkit.ChatColor;

import com.teamvitalis.vitalis.configuration.Lang;

public class MagicType {

	private static final HashMap<String, MagicType> TYPES = new HashMap<>();
	
	private String name;
	private Lang displayName;
	private int defaultMana;
	private ChatColor color;
	
	public MagicType(String name, Lang displayName, int defaultMana, ChatColor color) {
		setName(name);
		setDisplayName(displayName);
		setDefaultMana(defaultMana);
		setChatColor(color);
		TYPES.put(getName(), this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.toLowerCase();
	}

	public Lang getDisplayName() {
		return displayName;
	}

	public void setDisplayName(Lang displayName) {
		this.displayName = displayName;
	}

	public int getDefaultMana() {
		return defaultMana;
	}

	public void setDefaultMana(int defaultMana) {
		this.defaultMana = defaultMana;
	}
	
	public ChatColor getChatColor() {
		return color;
	}
	
	public void setChatColor(ChatColor color) {
		this.color = color;
	}
	
	public String toString() {
		return getName();
	}
	
	public static HashMap<String, MagicType> getMagicTypes() {
		return TYPES;
	}
	
}
