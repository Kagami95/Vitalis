package com.teamvitalis.vitalis.object;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.api.MechanistAbility;

public class Mechanist extends VitalisPlayer{
	
	private static HashMap<Player, Mechanist> mechanists = new HashMap<>();
	private ConcurrentHashMap<String, Integer> remainingUsesPerAbility = new ConcurrentHashMap<>();

	public Mechanist(Player player, HashMap<Integer, String> abilities) {
		super(player, ClassType.MECHANIST, abilities);
		mechanists.put(player, this);
		update(player);
	}
	
	/**
	 * Retrieves the remaining uses for an ability
	 * @param ability Ability being checked
	 * @return remaining uses
	 */
	public int getRemainingUses(String ability) {
		return remainingUsesPerAbility.get(ability);
	}
	
	/**
	 * Sets the remaining uses of an ability to a new amount
	 * @param ability ability being effected
	 * @param uses amount being changed to
	 */
	public void setRemainingUses(String ability, int uses) {
		remainingUsesPerAbility.put(ability, uses);
	}
	
	public static HashMap<Player, Mechanist> map() {
		return mechanists;
	}
	
	public static Set<Player> players() {
		return mechanists.keySet();
	}
	
	public static Collection<Mechanist> values() {
		return mechanists.values();
	}
	
	public void resetUses() {
		for (AbilityInfo info : AbilityInfo.getAbilitiesAsList()) {
			if (!(info.getAbility() instanceof MechanistAbility)) {
				continue;
			}
			MechanistAbility ability = (MechanistAbility) info.getAbility();
			remainingUsesPerAbility.put(info.getName(), ability.getDefaultUses());
		}
	}

	@Override
	public boolean canUse(String ability) {
		if (getRemainingUses(ability) == 0) {
			return false;
		}
		return true;
	}
}
