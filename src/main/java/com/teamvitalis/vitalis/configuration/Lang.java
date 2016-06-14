package com.teamvitalis.vitalis.configuration;

import org.bukkit.ChatColor;

public enum Lang {

	PYRO_DISPLAY("Chat.DisplayNames.Pyromancy"),
	CRYO_DISPLAY("Chat.DisplayNames.Cryomancy"),
	BIO_DISPLAY("Chat.DisplayNames.Biomancy"),
	NECRO_DISPLAY("Chat.DisplayNames.Necromancy"),
	AERO_DISPLAY("Chat.DisplayNames.Aeromancy"),
	ELECTRO_DISPLAY("Chat.DisplayNames.Electromancy"),
	PSYCHO_DISPLAY("Chat.DisplayNames.Psychomancy"),
	ETHER_DISPLAY("Chat.DisplayNames.Ethermancy"),
	SOLIS_DISPLAY("Chat.DisplayNames.Solismancy"),
	LUNA_DISPLAY("Chat.DisplayNames.Lunamancy");
	
	private String path;
	private String[] args;
	
	Lang(String path) {
		this(path, null);
	}
	
	Lang(String path, String[] args) {
		this.path = path;
		this.args = args;
	}
	
	@Override
	public String toString() {
		if (!LangConfig.get().contains(path)) {
			return ChatColor.RED + "Error: " + ChatColor.WHITE + "String missing from lang.yml! Missing: " + this.name() + " Path: " + path;
		}
		return ChatColor.translateAlternateColorCodes('&', LangConfig.get().getString(path));
	}
	
	public String[] getArgs() {
		return args;
	}
}
