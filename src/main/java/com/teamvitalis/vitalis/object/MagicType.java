package com.teamvitalis.vitalis.object;

import java.util.HashMap;

import org.bukkit.ChatColor;

import com.teamvitalis.vitalis.configuration.Lang;
import com.teamvitalis.vitalis.configuration.LangConfig;

public class MagicType {

	private static final HashMap<String, MagicType> TYPES = new HashMap<>();
	
	public static final MagicType PYRO = new MagicType("Pyromancy", Lang.PYRO_DISPLAY, 100, ChatColor.valueOf(LangConfig.get().getString("Chat.Pyromancy.ChatColor")), "pyro", "p");
	public static final MagicType CRYO = new MagicType("Cryomancy", Lang.CRYO_DISPLAY, 100, ChatColor.valueOf(LangConfig.get().getString("Chat.Cryomancy.ChatColor")), "cryo", "c");
	public static final MagicType BIO = new MagicType("Biomancy", Lang.BIO_DISPLAY, 100, ChatColor.valueOf(LangConfig.get().getString("Chat.Biomancy.ChatColor")), "bio", "b");
	public static final MagicType NECRO = new MagicType("Necromancy", Lang.NECRO_DISPLAY, 100, ChatColor.valueOf(LangConfig.get().getString("Chat.Necromancy.ChatColor")), "necro", "n");
	public static final MagicType AERO = new MagicType("Aeromancy", Lang.AERO_DISPLAY, 100, ChatColor.valueOf(LangConfig.get().getString("Chat.Aeromancy.ChatColor")), "aero", "a", "air");
	public static final MagicType ELECTRO = new MagicType("Electromancy", Lang.ELECTRO_DISPLAY, 100, ChatColor.valueOf(LangConfig.get().getString("Chat.Electromancy.ChatColor")), "electro", "el", "electric");
	public static final MagicType PSYCHO = new MagicType("Psychomancy", Lang.PSYCHO_DISPLAY, 100, ChatColor.valueOf(LangConfig.get().getString("Chat.Psychomancy.ChatColor")), "psycho", "psy", "ps");
	public static final MagicType ETHER = new MagicType("Ethermancy", Lang.ETHER_DISPLAY, 100, ChatColor.valueOf(LangConfig.get().getString("Chat.Ethermancy.ChatColor")), "ether", "e");
	public static final MagicType HELIO = new MagicType("Heliomancy", Lang.HELIO_DISPLAY, 100, ChatColor.valueOf(LangConfig.get().getString("Chat.Heliomancy.ChatColor")), "helio", "h");
	public static final MagicType LUNA = new MagicType("Lunamancy", Lang.LUNA_DISPLAY, 100, ChatColor.valueOf(LangConfig.get().getString("Chat.Lunamancy.ChatColor")), "luna", "l");
	
	private String name;
	private Lang displayName;
	private int defaultMana;
	private ChatColor color;
	
	public MagicType(String name, Lang displayName, int defaultMana, ChatColor color, String... aliases) {
		setName(name);
		setDisplayName(displayName);
		setDefaultMana(defaultMana);
		setChatColor(color);
		TYPES.put(getName(), this);
		for (String s : aliases) {
			TYPES.put(s.toLowerCase(), this);
		}
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
