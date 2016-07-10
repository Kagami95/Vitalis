package com.teamvitalis.vitalis.configuration;

public class Configs {
	
	public Configs() {
		new CastConfig();
		new DefaultConfig();
		new LangConfig();
	}
	
	public static void reload() {
		CastConfig.reload();
		DefaultConfig.reload();
		LangConfig.reload();
	}

}
