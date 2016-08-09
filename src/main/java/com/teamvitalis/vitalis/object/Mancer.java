package com.teamvitalis.vitalis.object;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.teamvitalis.vitalis.database.DBMethods;

public class Mancer extends VitalisPlayer{
	
	private static HashMap<Player, Mancer> mancers = new HashMap<>();
	
	private MagicType type;
	private double mana;
	private double maxMana;
	private ItemStack[] manaInventory;

	public Mancer(Player player, HashMap<Integer, String> abilities, Integer[] unlockedSkillTreeObjects) {
		super(player, ClassType.MANCER, abilities, unlockedSkillTreeObjects);
		mancers.put(player, this);
		update(player);
		ResultSet rs = DBMethods.readQuery("SELECT magic FROM vitalis_players WHERE uuid = '" + player.getUniqueId().toString() + "';");
		try {
			if (rs.next()) {
				MagicType magic = MagicType.fromName(rs.getString(1));
				if (magic != null) {
					setMagicType(magic);
				}
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public MagicType getMagicType() {
		return type;
	}
	
	public void setMagicType(MagicType type) {
		this.type = type;
		DBMethods.modifyQuery("UPDATE vitalis_players SET magic = '" + (type != null ? type.getName() : null) + "' WHERE uuid = '" + getUniqueId().toString() + "';");
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
}
