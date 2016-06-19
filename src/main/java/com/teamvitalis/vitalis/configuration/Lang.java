package com.teamvitalis.vitalis.configuration;

import org.bukkit.ChatColor;

public enum Lang {

	//Magic display names
	PYRO_DISPLAY("Chat.Pyromancy.DisplayName"),
	CRYO_DISPLAY("Chat.Cryomancy.DisplayName"),
	BIO_DISPLAY("Chat.Biomancy.DisplayName"),
	NECRO_DISPLAY("Chat.Necromancy.DisplayName"),
	AERO_DISPLAY("Chat.Aeromancy.DisplayName"),
	ELECTRO_DISPLAY("Chat.Electromancy.DisplayName"),
	PSYCHO_DISPLAY("Chat.Psychomancy.DisplayName"),
	ETHER_DISPLAY("Chat.Ethermancy.DisplayName"),
	HELIO_DISPLAY("Chat.Heliomancy.DisplayName"),
	LUNA_DISPLAY("Chat.Lunamancy.DisplayName"),
	//Command help messages
	HELP_COMMAND_HELP("Commands.Help.HelpMessage"),
	BIND_COMMAND_HELP("Commands.Bind.HelpMessage"),
	CLASS_COMMAND_HELP("Commands.Class.HelpMessage"),
	CHOOSE_COMMAND_HELP("Commands.Choose.HelpMessage"),
	WHO_COMMAND_HELP("Commands.Who.HelpMessage"),
	//Possible errors in commands
	INVALID_LENGTH("Commands.Errors.InvalidLength"),
	INVALID_USER_1("Commands.Errors.PlayerOnlyCommand"),
	INVALID_USER_2("Commands.Errors.MancerOnlyCommand"),
	INVALID_ARG("Commands.Errors.InvalidArgument"),
	INVALID_ABILITY("Commands.Errors.InvalidAbility"),
	INVALID_TARGET("Commands.Errors.InvalidTargetPlayer"),
	INVALID_MAGIC("Commands.Errors.InvalidMagicType"),
	INVALID_CLASS("Commands.Errors.InvalidClassType"),
	INVALID_TOPIC("Commands.Errors.InvalidHelpTopic"),
	HAS_MAGIC("Commands.Errors.PlayerHasMagic"),
	HAS_CLASS("Commands.Errors.PlayerHasClass"),
	//Success in command messages
	CLASS_CHOOSE("Commands.Class.ChooseSuccess"),
	MAGIC_CHOOSE("Commands.Choose.ChooseSuccess"),
	BIND_SUCCESS("Commands.Bind.BindSuccess"), 
	CLASS_CHOOSE_SENDER("Commands.Class.Other.ChooseSuccessSender"),
	CLASS_CHOOSE_TARGET("Commands.Class.Other.ChooseSuccessTarget"),
	BIND_SUCCESS_SENDER("Commands.Bind.Other.BindSuccessSender"),
	BIND_SUCCESS_TARGET("Commands.Bind.Other.BindSuccessTarget");
	
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
