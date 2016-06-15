package com.teamvitalis.vitalis.object;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.teamvitalis.vitalis.api.CoreAbility;
import com.teamvitalis.vitalis.api.MagicAbility;

public class Mancer extends VitalisPlayer{
	
	private static HashMap<Player, Mancer> mancers = new HashMap<>();
	
	private MagicType type;
	private double mana;
	private double maxMana;
	private ItemStack[] manaInventory;

	public Mancer(Player player, HashMap<Integer, String> abilities) {
		super(player, ClassType.MANCER, abilities);
		mancers.put(player, this);
		update(player);
	}
	
	public MagicType getMagicType() {
		return type;
	}
	
	public void setMagicType(MagicType type) {
		this.type = type;
	}
	
	public double getMana() {
		return mana;
	}
	
	public void setMana(double mana) {
		this.mana = mana;
	}
	
	public double getMaxMana() {
		return maxMana;
	}
	
	public void setMaxMana(double maxMana) {
		this.maxMana = maxMana;
	}
	
	public ItemStack[] getManaInventory() {
		return manaInventory;
	}
	
	public void setManaInventory(ItemStack[] manaInventory) {
		this.manaInventory = manaInventory;
	}
	
	public static HashMap<Player, Mancer> map() {
		return mancers;
	}
	
	public static Set<Player> players() {
		return mancers.keySet();
	}
	
	public static Collection<Mancer> values() {
		return mancers.values();
	}

	@Override
	public boolean canUse(String ability) {
		if (!(AbilityInfo.fromName(ability).getAbility() instanceof CoreAbility)) {
			return false;
		}
		MagicAbility ability2 = (MagicAbility) AbilityInfo.fromName(ability).getAbility();
		if (!(VitalisPlayer.fromPlayer(Bukkit.getPlayer(getUniqueId())) instanceof Mancer)) {
			return false;
		}
		Mancer mancer = (Mancer) VitalisPlayer.fromPlayer(Bukkit.getPlayer(getUniqueId()));
		if (mancer.getMagicType() == ability2.getMagicType()) {
			return true;
		}
		return false;
	}
}
