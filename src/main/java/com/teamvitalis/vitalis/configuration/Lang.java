package com.teamvitalis.vitalis.configuration;

import org.bukkit.ChatColor;

public enum Lang {

	EXAMPLE ("example", new String[] {"%player"});
	
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
