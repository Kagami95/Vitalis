package com.teamvitalis.vitalis.object;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.database.DBMethods;

public abstract class VitalisPlayer {

	private static final ConcurrentHashMap<UUID, VitalisPlayer> PLAYERS = new ConcurrentHashMap<>();
	
	private UUID uuid;
	private String name;
	private long joinDate;
	private long lastJoin;
	private ClassType classType;
	private HashMap<Integer, String> abilities;
	private Integer[] unlockedSkillTreeObjects;
	
	public VitalisPlayer(Player player, ClassType classType, HashMap<Integer, String> abilities, Integer[] unlockedSkillTreeObjects) {
		this(player.getUniqueId(), player.getName(), classType, abilities, unlockedSkillTreeObjects);
	}
	
	public VitalisPlayer(UUID uuid, String name, ClassType classType, HashMap<Integer, String> abilities, Integer[] unlockedSkillTreeObjects) {
		this.setUniqueId(uuid);
		this.setName(name);
		this.setClassType(classType);
		this.setAbilities(abilities);
		this.setUnlockedSkillTreeObjects(unlockedSkillTreeObjects);
		PLAYERS.put(this.getUniqueId(), this);
	}
	
	public static boolean load(Player player) {
		UUID uuid = player.getUniqueId();
		ResultSet rs = DBMethods.readQuery("SELECT * FROM vitalis_players WHERE uuid = '" + uuid.toString() + "';");
		try {
			if (!rs.next()) {
				//Do not create a new instance yet, created
				//when player uses '/v class' command
				return false;
			} else {
				ClassType type = ClassType.valueOf(rs.getString("class"));
				HashMap<Integer, String> abilities = new HashMap<>();
				for (int i = 1; i < 10; i++) {
					String ability = rs.getString("slot" + i);
					if (ability == null) {
						continue;
					}
					abilities.put(i, ability);
				}
				Integer[] unlockedSkillTreeObjects = new Integer[]{};
				String unlockedArray = rs.getString("unlocked_skills");
				if (unlockedArray != null && !unlockedArray.isEmpty()) {
					for (String s : unlockedArray.split(",[ ]*")) {
						if (!isNumeric(s)) {
							continue;
						}
						ArrayUtils.add(unlockedSkillTreeObjects, Integer.valueOf(s));
					}
				}
				if (type == ClassType.MANCER) {
					new Mancer(player, abilities, unlockedSkillTreeObjects);
				} else if (type == ClassType.MECHANIST) {
					new Mechanist(player, abilities, unlockedSkillTreeObjects);
				}
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void update(Player player) {
		VitalisPlayer v = fromPlayer(player);
		ResultSet rs = DBMethods.readQuery("SELECT * FROM vitalis_players WHERE uuid = '" + player.getUniqueId().toString() + "';");
		try {
			if (!rs.next()) {
				if (v == null) {
					return;
				}
				DBMethods.modifyQuery("INSERT INTO vitalis_players (uuid, name, class) VALUES ('" + player.getUniqueId().toString() + "', '" + player.getName() + "', '" + v.getClassType().toString() + "');");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		DBMethods.modifyQuery("UPDATE vitalis_players SET uuid = '" + player.getUniqueId().toString() + "', name = '" + player.getName() + "', class = '" + v.getClassType().toString() + "' WHERE uuid = '" + v.getUniqueId().toString() + "';");
		for (int i = 1; i < 10; i++) {
			String ability = v.getAbility(i);
			DBMethods.modifyQuery("UPDATE vitalis_players SET slot" + i + " = '" + ability + "' WHERE uuid = '" + player.getUniqueId().toString() + "';");
		}
		DBMethods.modifyQuery("UPDATE vitalis_players SET unlocked_skills = '" + ArrayUtils.toString(v.getUnlockedSkillTreeObjects()).replaceAll("[\\[\\] ]", "") + "' WHERE uuid = '" + player.getUniqueId().toString() + "';");
	}
	
	/**
	 * Returns the {@link VitalisPlayer} associated with the given Player.
	 * @param player Player used to get {@link VitalisPlayer}
	 * @return {@link VitalisPlayer} of Player
	 */
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
	 * Gets a {@link HashMap} of the {@link VitalisPlayer}'s slots and binds
	 * @return {@link HashMap} of the {@link VitalisPlayer}'s slots and binds
	 */
	public HashMap<Integer, String> getAbilities() {
		return abilities;
	}
	
	/**
	 * Get the {@link ClassType} of the {@link VitalisPlayer}
	 * @return {@link ClassType} of the {@link VitalisPlayer}
	 */
	public ClassType getClassType() {
		return classType;
	}
	
	/**
	 * Set the {@link ClassType} of the {@link VitalisPlayer}
	 * @param classType {@link ClassType} of the {@link VitalisPlayer}
	 */
	public void setClassType(ClassType classType) {
		this.classType = classType;
	}
	
	/**
	 * Set the {@link VitalisPlayer}'s slots and binds
	 * @param spells {@link HashMap} containing the {@link VitalisPlayer}'s slots and binds
	 */
	public void setAbilities(HashMap<Integer, String> abilities) {
		this.abilities = abilities;
	}
	
	/**
	 * Gets an ability on a specific slot of the {@link VitalisPlayer}
	 * @param slot Integer of the slot to get (0 - 8)
	 * @return String name of the spell bound to the specified slot
	 */
	public String getAbility(int slot) {
		return getAbilities().get(slot);
	}
	
	/**
	 * Sets a spell on a specific slot of the {@link VitalisPlayer}
	 * @param slot Integer of the slot to modify (0 - 8)
	 * @param spell String name of the spell to be bound to the specified slot
	 */
	public void setAbility(int slot, String ability) {
		getAbilities().put(slot, ability);
	}
	
	/**
	 * Gets the {@link SkillTreeObject}s that the player has unlocked
	 * @return Integer array of the {@link SkillTreeObject} IDs
	 */
	public Integer[] getUnlockedSkillTreeObjects() {
		return unlockedSkillTreeObjects;
	}
	
	/**
	 * Sets the {@link SkillTreeObject}s that the player has unlocked
	 * @param unlockedSkillTreeObjects Integer array of {@link SkillTreeObject} IDs
	 */
	public void setUnlockedSkillTreeObjects(Integer[] unlockedSkillTreeObjects) {
		this.unlockedSkillTreeObjects = unlockedSkillTreeObjects;
	}
	
	private static boolean isNumeric(String id) {
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(id, pos);
		return id.length() == pos.getIndex();
	}
	
	public boolean canUse() {
		boolean canUse = true;
		Player player = Bukkit.getPlayer(uuid);
		CastInfo info = CastInfo.fromName(getAbility(player.getInventory().getHeldItemSlot()));
		if (this instanceof Mancer) {
			Mancer m = (Mancer) this;
			if (info.getMagicType() == null) {
				canUse = false;
			} else if (m.getMagicType() != info.getMagicType()) {
				canUse = false;
			}
		} else if (this instanceof Mechanist) {
			Mechanist m = (Mechanist) this;
			if (info.getMagicType() != null) {
				canUse = false;
			} else if (m.getRemainingUses(info.getName()) == 0) {
				canUse = false;
			}
		} else {
			canUse = false;
		}
		return canUse;
	}
}
