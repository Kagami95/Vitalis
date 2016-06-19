package com.teamvitalis.vitalis.configuration;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

public class LangConfig {

	private static Config lang;

	public LangConfig() {
		lang = new Config(new File("lang.yml"));
		init();
	}

	private void init() {
		FileConfiguration config = lang.get();
		
		config.addDefault("Chat.Aeromancy.DisplayName", "Aeromancy");
		config.addDefault("Chat.Aeromancy.ChatColor", "GRAY");
		
		config.addDefault("Chat.Biomancy.DisplayName", "Biomancy");
		config.addDefault("Chat.Biomancy.ChatColor", "GREEN");

		config.addDefault("Chat.Cryomancy.DisplayName", "Cryomancy");
		config.addDefault("Chat.Cryomancy.ChatColor", "DARK_AQUA");

		config.addDefault("Chat.Electromancy.DisplayName", "Electromancy");
		config.addDefault("Chat.Electromancy.ChatColor", "YELLOW");

		config.addDefault("Chat.Ethermancy.DisplayName", "Ethermancy");
		config.addDefault("Chat.Ethermancy.ChatColor", "LIGHT_PURPLE");

		config.addDefault("Chat.Heliomancy.DisplayName", "Heliomancy");
		config.addDefault("Chat.Heliomancy.ChatColor", "GOLD");

		config.addDefault("Chat.Lunamancy.DisplayName", "Lunamancy");
		config.addDefault("Chat.Lunamancy.ChatColor", "DARK_GRAY");

		config.addDefault("Chat.Necromancy.DisplayName", "Necromancy");
		config.addDefault("Chat.Necromancy.ChatColor", "DARK_PURPLE");

		config.addDefault("Chat.Psychomancy.DisplayName", "Psychomancy");
		config.addDefault("Chat.Psychomancy.ChatColor", "DARK_GREEN");

		config.addDefault("Chat.Pyromancy.DisplayName", "Pyromancy");
		config.addDefault("Chat.Pyromancy.ChatColor", "DARK_RED");
		
		config.addDefault("Commands.Bind.HelpMessage", "This command allows you to bind an ability to your hotbar.");
		config.addDefault("Commands.Bind.BindSuccess", "Successfully bound %ability% to slot %slot%!");
		config.addDefault("Commands.Bind.Other.BindSuccessSender", "Successfully bound %ability% to slot %slot% of %player%!");
		config.addDefault("Commands.Bind.Other.BindSuccessTarget", "%sender% has bound %ability% to slot %slot% for you!");
		
		config.addDefault("Commands.Choose.HelpMessage", "Choose any magic type that you wish to control. Only usable once without explicit permissions!");
		config.addDefault("Commands.Choose.ChooseSuccess", "Successfully chose %magic%!");
		
		config.addDefault("Commands.Class.HelpMessage", "This allows you to select whether you want to be a Mancer or a Mechanist.");
		config.addDefault("Commands.Class.ChooseSuccess", "Successfully chose %class%!");
		config.addDefault("Commands.Class.Other.ChooseSuccessSender", "Successfully chose %class% for %target%!");
		config.addDefault("Commands.Class.Other.ChooseSuccessTarget", "%sender% has chose %class% for you!");
		
		config.addDefault("Commands.Help.HelpMessage", "This command displays the help message of any topic.");
		
		config.addDefault("Commands.Who.HelpMessage", "Retrieves Vitalis based info on a player.");
		
		config.addDefault("Commands.Errors.InvalidLength", "Invalid length!");
		config.addDefault("Commands.Errors.InvalidClassType", "Invalid class type!");
		config.addDefault("Commands.Errors.InvalidMagicType", "Invalid magic type! %magiclist%");
		config.addDefault("Commands.Errors.InvalidHelpTopic", "Invalid help topic!");
		config.addDefault("Commands.Errors.InvalidArgument", "Invalid argument!");
		config.addDefault("Commands.Errors.InvalidAbility", "Ability doesn't exist!");
		config.addDefault("Commands.Errors.InvalidTargetPlayer", "Player not found!");
		config.addDefault("Commands.Errors.PlayerHasMagic", "Player already has magic!");
		config.addDefault("Commands.Errors.PlayerHasClass", "Player already has class!");
		config.addDefault("Commands.Errors.PlayerOnlyCommand", "Command only usable by a player!");
		config.addDefault("Commands.Errors.MancerOnlyCommand", "Command only usable by a mancer!");
		
		lang.save();
	}

	public static FileConfiguration get() {
		return lang.get();
	}

	public static void reload() {
		lang.reload();
	}
}
