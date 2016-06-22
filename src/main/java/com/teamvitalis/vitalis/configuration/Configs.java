package com.teamvitalis.vitalis.configuration;

public class Configs {
	
	public Configs() {
		new AbilityConfig();
		new DefaultConfig();
		new LangConfig();
	}
	
	public static void reload() {
		AbilityConfig.reload();
		DefaultConfig.reload();
		LangConfig.reload();
	}

}
