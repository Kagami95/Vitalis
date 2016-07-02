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
		
		config.addDefault("General.PluginPrefix", "&8[&5Vitalis&8] &r");
		config.addDefault("General.NoPermission", "&cSorry, but you do not have permission to do that!");
		
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
		
		config.addDefault("Chat.Mancer.DisplayName", "&eMancer");
		config.addDefault("Chat.Mechanist.DisplayName", "&6Mechanist");
		
		config.addDefault("Commands.Usage", "&5Command Usage: &7");
		config.addDefault("Commands.Title", "&8------------.[&5Vitalis&8].------------");
		config.addDefault("Commands.Suggestion", "&7Use '&b/vitalis help&7' to view all available commands!");
		config.addDefault("Commands.Unknown", "&cUnknown command! &7Use '&b/vitalis help&7' to view a list of all commands!");
		
		config.addDefault("Commands.Bind.HelpMessage", "This command allows you to bind an ability to your hotbar.");
		config.addDefault("Commands.Bind.BindSuccess", "&aSuccessfully bound &f%ability%&a to slot &f%slot%&a!");
		config.addDefault("Commands.Bind.Other.BindSuccessSender", "&aSuccessfully bound &f%ability%&a to slot &f%slot%&a of &f%player%&a!");
		config.addDefault("Commands.Bind.Other.BindSuccessTarget", "&f%sender%&a has bound &f%ability%&a to slot &f%slot%&a for you!");
		
		config.addDefault("Commands.Choose.HelpMessage", "Choose any magic type that you wish to control. Only usable once without explicit permissions!");
		config.addDefault("Commands.Choose.ChooseSuccess", "Successfully chose %magic%!");
		
		config.addDefault("Commands.Class.HelpMessage", "This allows you to select whether you want to be a Mancer or a Mechanist.");
		config.addDefault("Commands.Class.ChooseSuccess", "Successfully chose %class%!");
		config.addDefault("Commands.Class.Other.ChooseSuccessSender", "Successfully chose %class% for %target%!");
		config.addDefault("Commands.Class.Other.ChooseSuccessTarget", "%sender% has chose %class% for you!");
		
		config.addDefault("Commands.Help.HelpMessage", "This command displays the help message of any topic.");
		config.addDefault("Commands.Help.Title", "&bCommands: &3<required> [optional]");
		
		config.addDefault("Commands.Reload.HelpMessage", "Reloads Vitalis and Vitalis configs.");
		config.addDefault("Commands.Reload.Complete", "Vitalis & Vitalis configs reloaded.");
		
		config.addDefault("Commands.Remove.HelpMessage", "Allows the removal of magic from a player.");
		config.addDefault("Commands.Remove.RemoveSuccess", "Successfully removed your magic.");
		config.addDefault("Commands.Remove.Other.RemoveSuccessSender", "Successfully removed the magic of %target%.");
		config.addDefault("Commands.Remove.Other.RemoveSuccessTarget", "Your magic has been removed by %sender%.");
		
		config.addDefault("Commands.Who.HelpMessage", "Retrieves Vitalis based info on a player.");
		
		config.addDefault("Commands.Errors.Unknown", "&cUh oh, something went wrong!");
		config.addDefault("Commands.Errors.InvalidLength", "&cInvalid length!");
		config.addDefault("Commands.Errors.InvalidClassType", "&cInvalid class type!");
		config.addDefault("Commands.Errors.InvalidMagicType", "&cInvalid magic type!");
		config.addDefault("Commands.Errors.InvalidHelpTopic", "&cInvalid help topic!");
		config.addDefault("Commands.Errors.InvalidArgument", "&cInvalid argument!");
		config.addDefault("Commands.Errors.InvalidAbility", "&cAbility doesn't exist!");
		config.addDefault("Commands.Errors.InvalidTargetPlayer", "&cPlayer not found!");
		config.addDefault("Commands.Errors.PlayerHasMagic", "&cPlayer already has magic!");
		config.addDefault("Commands.Errors.PlayerHasClass", "&cPlayer already has class!");
		config.addDefault("Commands.Errors.PlayerOnlyCommand", "&cCommand only usable by a player!");
		config.addDefault("Commands.Errors.MancerOnlyCommand", "&cCommand only usable by a mancer!");
		
		lang.save();
	}

	public static FileConfiguration get() {
		return lang.get();
	}

	public static void reload() {
		lang.reload();
	}
}
