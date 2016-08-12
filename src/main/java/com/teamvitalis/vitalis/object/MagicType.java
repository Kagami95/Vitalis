package com.teamvitalis.vitalis.object;

import java.util.Collection;
import java.util.HashMap;

import org.bukkit.ChatColor;

import com.teamvitalis.vitalis.configuration.LangConfig;

public class MagicType {

	private static final HashMap<String, MagicType> TYPES = new HashMap<>();
	private static final HashMap<String, MagicType> ALIASES = new HashMap<>();
	
	public static final MagicType PYRO = new MagicType("Pyromancy", Lang.CHAT_PYRO_DISPLAY, 100, ChatColor.valueOf(LangConfig.get().getString("Chat.Pyromancy.ChatColor")), "pyro", "p");
	public static final MagicType CRYO = new MagicType("Cryomancy", Lang.CHAT_CRYO_DISPLAY, 100, ChatColor.valueOf(LangConfig.get().getString("Chat.Cryomancy.ChatColor")), "cryo", "c");
	public static final MagicType BIO = new MagicType("Biomancy", Lang.CHAT_BIO_DISPLAY, 100, ChatColor.valueOf(LangConfig.get().getString("Chat.Biomancy.ChatColor")), "bio", "b");
	public static final MagicType NECRO = new MagicType("Necromancy", Lang.CHAT_NECRO_DISPLAY, 100, ChatColor.valueOf(LangConfig.get().getString("Chat.Necromancy.ChatColor")), "necro", "n");
	public static final MagicType AERO = new MagicType("Aeromancy", Lang.CHAT_AERO_DISPLAY, 100, ChatColor.valueOf(LangConfig.get().getString("Chat.Aeromancy.ChatColor")), "aero", "a", "air");
	public static final MagicType ELECTRO = new MagicType("Electromancy", Lang.CHAT_ELECTRO_DISPLAY, 100, ChatColor.valueOf(LangConfig.get().getString("Chat.Electromancy.ChatColor")), "electro", "el", "electric");
	public static final MagicType PSYCHO = new MagicType("Psychomancy", Lang.CHAT_PSYCHO_DISPLAY, 100, ChatColor.valueOf(LangConfig.get().getString("Chat.Psychomancy.ChatColor")), "psycho", "psy", "ps");
	public static final MagicType ETHER = new MagicType("Ethermancy", Lang.CHAT_ETHER_DISPLAY, 100, ChatColor.valueOf(LangConfig.get().getString("Chat.Ethermancy.ChatColor")), "ether", "e");
	public static final MagicType HELIO = new MagicType("Heliomancy", Lang.CHAT_HELIO_DISPLAY, 100, ChatColor.valueOf(LangConfig.get().getString("Chat.Heliomancy.ChatColor")), "helio", "h");
	public static final MagicType LUNA = new MagicType("Lunamancy", Lang.CHAT_LUNA_DISPLAY, 100, ChatColor.valueOf(LangConfig.get().getString("Chat.Lunamancy.ChatColor")), "luna", "l");
	
	private String name;
	private Lang displayName;
	private int defaultMana;
	private ChatColor color;
	private String[] aliases;
	
	public MagicType(String name, Lang displayName, int defaultMana, ChatColor color, String... aliases) {
		setName(name);
		setDisplayName(displayName);
		setDefaultMana(defaultMana);
		setChatColor(color);
		this.aliases = aliases;
		TYPES.put(name.toLowerCase(), this);
		for (String s : aliases) {
			ALIASES.put(s, this);
		}
	}
	
	public static MagicType fromName(String name) {
		return TYPES.containsKey(name.toLowerCase()) ? TYPES.get(name.toLowerCase()) : fromAlias(name);
	}
	
	public static MagicType fromAlias(String alias) {
		return ALIASES.containsKey(alias) ? ALIASES.get(alias) : null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public String[] getAliases() {
		return aliases;
	}
	
	public String toString() {
		return getName();
	}
	
	public static Collection<MagicType> values() {
		return TYPES.values();
	}
}
