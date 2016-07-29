package com.teamvitalis.vitalis.object;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.api.BaseCast;
import com.teamvitalis.vitalis.api.MechCast;

public class Mechanist extends VitalisPlayer{
	
	private static HashMap<Player, Mechanist> mechanists = new HashMap<>();
	private ConcurrentHashMap<String, Integer> remainingUsesPerAbility = new ConcurrentHashMap<>();

	public Mechanist(Player player, HashMap<Integer, String> abilities, Integer[] unlockedSkillTreeObjects) {
		super(player, ClassType.MECHANIST, abilities, unlockedSkillTreeObjects);
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
		for (BaseCast cast : BaseCast.getAllCasts()) {
			if (!(cast instanceof MechCast)) {
				continue;
			}
			MechCast mech = (MechCast) cast;
			remainingUsesPerAbility.put(cast.getName(), mech.getDefaultUses());
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
