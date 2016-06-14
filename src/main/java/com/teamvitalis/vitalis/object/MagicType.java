package com.teamvitalis.vitalis.object;

import java.util.HashMap;

import org.bukkit.ChatColor;

import com.teamvitalis.vitalis.configuration.Lang;

public class MagicType {

	private static final HashMap<String, MagicType> TYPES = new HashMap<>();
	
	public static final MagicType PYRO = new MagicType("Pyromancy", Lang.PYRO_DISPLAY, 100, ChatColor.RED);
	public static final MagicType CRYO = new MagicType("Cryomancy", Lang.CRYO_DISPLAY, 100, ChatColor.AQUA);
	public static final MagicType BIO = new MagicType("Biomancy", Lang.BIO_DISPLAY, 100, ChatColor.GREEN);
	public static final MagicType NECRO = new MagicType("Necromancy", Lang.NECRO_DISPLAY, 100, ChatColor.DARK_GRAY);
	public static final MagicType AERO = new MagicType("Aeromancy", Lang.AERO_DISPLAY, 100, ChatColor.GRAY);
	public static final MagicType ELECTRO = new MagicType("Electromancy", Lang.ELECTRO_DISPLAY, 100, ChatColor.YELLOW);
	public static final MagicType PSYCHO = new MagicType("Psychomancy", Lang.PSYCHO_DISPLAY, 100, ChatColor.LIGHT_PURPLE);
	public static final MagicType ETHER = new MagicType("Ethermancy", Lang.ETHER_DISPLAY, 100, ChatColor.DARK_PURPLE);
	public static final MagicType SOLIS = new MagicType("Solismancy", Lang.SOLIS_DISPLAY, 100, ChatColor.GOLD);
	public static final MagicType LUNA = new MagicType("Lunamancy", Lang.LUNA_DISPLAY, 100, ChatColor.DARK_AQUA);
	
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
	
	public static MagicType fromName(String name) {
		return TYPES.containsKey(name) ? TYPES.get(name) : null;
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
