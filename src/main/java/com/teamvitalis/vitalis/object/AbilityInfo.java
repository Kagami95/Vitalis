package com.teamvitalis.vitalis.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.teamvitalis.vitalis.api.AddonAbility;
import com.teamvitalis.vitalis.api.CoreAbility;

public class AbilityInfo {
	
	private static HashMap<String, AbilityInfo> abilities = new HashMap<>();
	private static HashMap<Class<? extends CoreAbility>, AbilityInfo> classMap = new HashMap<>();
	
	private String name;
	private Class<? extends CoreAbility> clase;
	private boolean addon;
	private CoreAbility ability;

	public AbilityInfo(CoreAbility abil) {
		this.ability = abil;
		this.name = abil.getName();
		this.clase = abil.getClass();
		this.addon = (abil instanceof AddonAbility);
		abilities.put(name, this);
		classMap.put(clase, this);
	}
	
	/**
	 * Used to get the AbilityInfo from a name. Case must be matching
	 * @param name Name of ability
	 * @return AbilityInfo, null if non-existent
	 */
	public static AbilityInfo fromName(String name) {
		return abilities.containsKey(name) ? abilities.get(name) : null;
	}
	
	/**
	 * Used to get the AbilityInfo from a class. Class must extend CoreAbilty
	 * @param clase Class of ability
	 * @return AbilityInfo, null if non-existent
	 */
	public static AbilityInfo fromClass(Class<? extends CoreAbility> clase) {
		return classMap.containsKey(clase) ? classMap.get(clase) : null;
	}
	
	/**
	 * Used to get the Class correlating to the AbilityInfo. The class will extend CoreAbility
	 * @return Class of Ability
	 */
	public Class<? extends CoreAbility> getClazz() {
		return clase;
	}
	
	/**
	 * Used to retrieve the CoreAbility associated with the AbilityInfo.
	 * @return
	 */
	public CoreAbility getAbility() {
		return ability;
	}
	
	/**
	 * Used to get the name of the Ability.
	 * @return Name of Ability
	 */
	public String getName() {
		return name;
	}
	
	public static List<AbilityInfo> getAbilitiesAsList() {
		List<AbilityInfo> list = new ArrayList<>();
		for (AbilityInfo info : abilities.values()) {
			list.add(info);
		}
		return list;
	}
	
	public static HashMap<String, AbilityInfo> getAbilityMap() {
		return abilities;
	}
	
	public static List<String> getAbilityNamesAsList() {
		List<String> list = new ArrayList<>();
		for (String name : abilities.keySet()) {
			list.add(name);
		}
		return list;
	}
	
	/**
	 * Checks if the ability is an addon
	 * @return true if ability is addon.
	 */
	public boolean isAddon() {
		return addon;
	}
}
