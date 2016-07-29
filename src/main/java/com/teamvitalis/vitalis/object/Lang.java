package com.teamvitalis.vitalis.object;

import org.bukkit.ChatColor;

import com.teamvitalis.vitalis.configuration.LangConfig;

public enum Lang {
	
	//General
	PLUGIN_PREFIX ("General.PluginPrefix"),
	NO_PERMISSION ("General.NoPermission"),

	//Magic display names
	CHAT_PYRO_DISPLAY ("Chat.Pyromancy.DisplayName"),
	CHAT_CRYO_DISPLAY ("Chat.Cryomancy.DisplayName"),
	CHAT_BIO_DISPLAY ("Chat.Biomancy.DisplayName"),
	CHAT_NECRO_DISPLAY ("Chat.Necromancy.DisplayName"),
	CHAT_AERO_DISPLAY ("Chat.Aeromancy.DisplayName"),
	CHAT_ELECTRO_DISPLAY ("Chat.Electromancy.DisplayName"),
	CHAT_PSYCHO_DISPLAY ("Chat.Psychomancy.DisplayName"),
	CHAT_ETHER_DISPLAY ("Chat.Ethermancy.DisplayName"),
	CHAT_HELIO_DISPLAY ("Chat.Heliomancy.DisplayName"),
	CHAT_LUNA_DISPLAY ("Chat.Lunamancy.DisplayName"),
	CHAT_MECHANIST_DISPLAY ("Chat.Mechanist.DisplayName"),
	CHAT_MANCER_DISPLAY ("Chat.Mancer.DisplayName"),
	
	//Command help general
	COMMAND_USAGE ("Commands.Usage"),
	COMMAND_TITLE ("Commands.Title"),
	COMMAND_SUGGESTION ("Commands.Suggestion"),
	COMMAND_UNKNOWN ("Commands.Unknown"),
	
	//Command help messages
	COMMAND_HELP_TITLE ("Commands.Help.Title"),
	COMMAND_HELP_HELP ("Commands.Help.HelpMessage"),
	COMMAND_BIND_HELP ("Commands.Bind.HelpMessage"),
	COMMAND_CHOOSE_HELP ("Commands.Choose.HelpMessage"),
	COMMAND_CLASS_HELP ("Commands.Class.HelpMessage"),
	COMMAND_RELOAD_HELP ("Commands.Reload.HelpMessage"),
	COMMAND_REMOVE_HELP ("Commands.Remove.HelpMessage"),
	COMMAND_WHO_HELP ("Commands.Who.HelpMessage"),
	
	//Possible errors in commands
	COMMAND_ERROR_UNKNOWN ("Commands.Errors.Unknown"),
	COMMAND_ERROR_INVALID_LENGTH ("Commands.Errors.InvalidLength"),
	COMMAND_ERROR_INVALID_USER_1 ("Commands.Errors.PlayerOnlyCommand"),
	COMMAND_ERROR_INVALID_USER_2 ("Commands.Errors.MancerOnlyCommand"),
	COMMAND_ERROR_INVALID_ARG ("Commands.Errors.InvalidArgument"),
	COMMAND_ERROR_INVALID_ABILITY ("Commands.Errors.InvalidAbility"),
	COMMAND_ERROR_INVALID_TARGET ("Commands.Errors.InvalidTargetPlayer"),
	COMMAND_ERROR_INVALID_MAGIC ("Commands.Errors.InvalidMagicType"),
	COMMAND_ERROR_INVALID_CLASS ("Commands.Errors.InvalidClassType"),
	COMMAND_ERROR_INVALID_TOPIC ("Commands.Errors.InvalidHelpTopic"),
	COMMAND_ERROR_HAS_MAGIC ("Commands.Errors.PlayerHasMagic"),
	COMMAND_ERROR_HAS_CLASS ("Commands.Errors.PlayerHasClass"),
	
	//Success in command messages
	COMMAND_BIND_SUCCESS ("Commands.Bind.BindSuccess", new String[] {"%ability%", "%slot%"}),
	COMMAND_BIND_SUCCESS_SENDER ("Commands.Bind.Other.BindSuccessSender", new String[] {"%ability%", "%slot%", "%target%"}),
	COMMAND_BIND_SUCCESS_TARGET ("Commands.Bind.Other.BindSuccessTarget", new String[] {"%ability%", "%slot%", "%sender%"}),
	
	COMMAND_CHOOSE_SUCCESS ("Commands.Choose.ChooseSuccess", new String[] {"%magic%"}),
	
	COMMAND_CLASS_CHOOSE ("Commands.Class.ChooseSuccess", new String[] {"%class%"}),
	COMMAND_CLASS_CHOOSE_SENDER ("Commands.Class.Other.ChooseSuccessSender", new String[] {"%class%", "%target%"}),
	COMMAND_CLASS_CHOOSE_TARGET ("Commands.Class.Other.ChooseSuccessTarget", new String[] {"%class%", "%sender%"}),
	
	COMMAND_RELOAD_COMPLETE ("Commands.Reload.Complete"),
	
	COMMAND_REMOVE_SUCCESS ("Commands.Remove.RemoveSuccess"),
	COMMAND_REMOVE_SUCCESS_SENDER ("Commands.Remove.Other.RemoveSuccessSender", new String[] {"%target%"}),
	COMMAND_REMOVE_SUCCESS_TARGET ("Commands.Remove.Other.RemoveSuccessTarget", new String[] {"%sender%"}),
	
	//Gui
	GUI_SKILL_TREE ("Gui.SkillTree.Title"),
	GUI_SKILL_TREE_ALREADY_UNLOCKED ("Gui.SkillTree.AlreadyUnlocked"),
	GUI_SKILL_TREE_NEW_UNLOCK ("Gui.SkillTree.NewUnlock", new String[] {"%skill"}),
	GUI_SKILL_TREE_LOCKED ("Gui.SkillTree.Locked");
	
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
		return toString(false);
	}
	
	/**
	 * Returns a string with no replaced args.
	 * @param prefix Append a prefix to beginning of sentence.
	 * @return String without replaced variables.
	 */
	public String toString(boolean prefix) {
		if (!LangConfig.get().contains(path)) {
			return ChatColor.RED + "Error: " + ChatColor.WHITE + "String missing from lang.yml! Missing: " + this.name() + " Path: " + path;
		}
		return ChatColor.translateAlternateColorCodes('&', (prefix ? PLUGIN_PREFIX : "") + LangConfig.get().getString(path));
	}
	
	/**
	 * Returns a string with replaced args with NO appended prefix.
	 * @param args Variables to be replaced in string.
	 * @return String with replaced variables.
	 */
	public String toString(String... args) {
		return toString(false, args);
	}
	
	/**
	 * Returns a string with replaced args.
	 * @param prefix Append prefix to beginning of sentence.
	 * @param args Variables to be replaced in string.
	 * @return String with replaced variables.
	 */
	public String toString(boolean prefix, String... args) {
		String s = toString();
		if (args != null && args.length > 0 && getArgs() != null && getArgs().length > 0) {
			for (int i = 0; i < getArgs().length; i++) {
				if (args.length < i || args[i] == null) {
					continue;
				}
				s = s.replace(getArgs()[i], args[i]);
			}
		}
		return ChatColor.translateAlternateColorCodes('&', (prefix ? PLUGIN_PREFIX : "") + s);
	}
	
	public String[] getArgs() {
		return args;
	}
}
