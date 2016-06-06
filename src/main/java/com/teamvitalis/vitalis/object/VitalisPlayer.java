package com.teamvitalis.vitalis.object;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class VitalisPlayer {

	private static final ConcurrentHashMap<UUID, VitalisPlayer> PLAYERS = new ConcurrentHashMap<>();
	
	private UUID uuid;
	private String name;
	private long joinDate;
	private long lastJoin;
	private MagicType magicType;
	private double mana;
	private double maxMana;
	private ItemStack[] manaInventory;
	private HashMap<Integer, String> spells;
	
	public VitalisPlayer(Player player, MagicType magicType, double mana, double maxMana, ItemStack[] manaInventory, HashMap<Integer, String> spells) {
		this(player.getUniqueId(), player.getName(), magicType, mana, maxMana, manaInventory, spells);
	}
	
	public VitalisPlayer(UUID uuid, String name, MagicType magicType, double mana, double maxMana, ItemStack[] manaInventory, HashMap<Integer, String> spells) {
		this.setUniqueId(uuid);
		this.setName(name);
		this.setMagicType(magicType);
		this.setMana(mana);
		this.setMaxMana(maxMana);
		this.setManaInventory(manaInventory);
		this.setSpells(spells);
		PLAYERS.put(this.uuid, this);
	}
	
	public static VitalisPlayer fromPlayer(Player player) {
		UUID uuid = player.getUniqueId();
		if (!PLAYERS.containsKey(uuid)) return null;
		return PLAYERS.get(uuid);
	}
	
	/**
	 * Get the UUID of the {@link VitalisPlayer}
	 * @return UUID of the {@link VitalisPlayer}
	 */
	public UUID getUniqueId() {
		return uuid;
	}

	/**
	 * Set the UUID of the {@link VitalisPlayer}
	 * @param uuid UUID of the {@link VitalisPlayer}
	 */
	public void setUniqueId(UUID uuid) {
		this.uuid = uuid;
	}
	
	/**
	 * Get the name of the {@link VitalisPlayer}
	 * @return String name of the {@link VitalisPlayer}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the {@link VitalisPlayer}
	 * @param name String name of the {@link VitalisPlayer}
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the date the {@link VitalisPlayer} first joined
	 * @return Long timestamp of when {@link VitalisPlayer} first joined
	 */
	public long getJoinDate() {
		return joinDate;
	}

	/**
	 * Set timestamp for when the {@link VitalisPlayer} first joined
	 * @param joinDate Long timestamp of when the {@link VitalisPlayer} first joined
	 */
	public void setJoinDate(long joinDate) {
		this.joinDate = joinDate;
	}

	/**
	 * Get the last join timestamp of when the {@link VitalisPlayer} last joined
	 * @return Long timestamp of when the {@link VitalisPlayer} last joined
	 */
	public long getLastJoin() {
		return lastJoin;
	}

	/**
	 * Set the last join timestamp of when the {@link VitalisPlayer} last joined
	 * @param lastJoin Long timestamp of when the {@link VitalisPlayer} last joined
	 */
	public void setLastJoin(long lastJoin) {
		this.lastJoin = lastJoin;
	}

	/**
	 * Get the MagicType of the {@link VitalisPlayer}
	 * @return MagicType of the {@link VitalisPlayer}
	 */
	public MagicType getMagicType() {
		return magicType;
	}

	/**
	 * Set the MagicType of the {@link VitalisPlayer}
	 * @param magicType MagicType of the {@link VitalisPlayer}
	 */
	public void setMagicType(MagicType magicType) {
		this.magicType = magicType;
	}

	/**
	 * Get the {@link VitalisPlayer}'s current mana
	 * @return Double value of the {@link VitalisPlayer}'s current mana
	 */
	public double getMana() {
		return mana;
	}

	/**
	 * Set the {@link VitalisPlayer}'s current mana
	 * @param mana Double value of the {@link VitalisPlayer}'s current mana
	 */
	public void setMana(double mana) {
		this.mana = mana;
	}

	/**
	 * Get the {@link VitalisPlayer}'s max mana
	 * @return Double value of the {@link VitalisPlayer}'s max mana
	 */
	public double getMaxMana() {
		return maxMana;
	}

	/**
	 * Set the {@link VitalisPlayer}'s max mana
	 * @param maxMana Double value of the {@link VitalisPlayer}'s max mana
	 */
	public void setMaxMana(double maxMana) {
		this.maxMana = maxMana;
	}

	/**
	 * Gets an ItemStack array of the {@link VitalisPlayer}'s mana inventory
	 * @return ItemStack array of the {@link VitalisPlayer}'s mana inventory
	 */
	public ItemStack[] getManaInventory() {
		return manaInventory;
	}

	/**
	 * Set the {@link VitalisPlayer}'s mana inventory
	 * @param manaInventory ItemStack[] array of items to be saved as the {@link VitalisPlayer}'s mana inventory
	 */
	public void setManaInventory(ItemStack[] manaInventory) {
		this.manaInventory = manaInventory;
	}

	/**
	 * Gets a {@link HashMap} of the {@link VitalisPlayer}'s slots and binds
	 * @return {@link HashMap} of the {@link VitalisPlayer}'s slots and binds
	 */
	public HashMap<Integer, String> getSpells() {
		return spells;
	}
	
	/**
	 * Set the {@link VitalisPlayer}'s slots and binds
	 * @param spells {@link HashMap} containing the {@link VitalisPlayer}'s slots and binds
	 */
	public void setSpells(HashMap<Integer, String> spells) {
		this.spells = spells;
	}
	
	/**
	 * Gets a spell on a specific slot of the {@link VitalisPlayer}
	 * @param slot Integer of the slot to get (0 - 8)
	 * @return String name of the spell bound to the specified slot
	 */
	public String getSpell(int slot) {
		return getSpells().get(slot);
	}
	
	/**
	 * Sets a spell on a specific slot of the {@link VitalisPlayer}
	 * @param slot Integer of the slot to modify (0 - 8)
	 * @param spell String name of the spell to be bound to the specified slot
	 */
	public void setSpell(int slot, String spell) {
		getSpells().put(slot, spell);
	}
}
