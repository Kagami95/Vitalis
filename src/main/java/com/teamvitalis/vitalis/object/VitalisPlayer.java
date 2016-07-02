package com.teamvitalis.vitalis.object;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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
	
	public VitalisPlayer(Player player, ClassType classType, HashMap<Integer, String> abilities) {
		this(player.getUniqueId(), player.getName(), classType, abilities);
	}
	
	public VitalisPlayer(UUID uuid, String name, ClassType classType, HashMap<Integer, String> abilities) {
		this.setUniqueId(uuid);
		this.setName(name);
		this.setClassType(classType);
		this.setAbilities(abilities);
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
				String name = player.getName();
				if (name != rs.getString("name")) {
					DBMethods.modifyQuery("UPDATE vitalis_players SET name = '" + name + "' WHERE uuid = '" + uuid.toString() + "';"); 
				}
				ClassType type = ClassType.valueOf(rs.getString("class"));
				HashMap<Integer, String> abilities = new HashMap<>();
				for (int i = 1; i < 10; i++) {
					String ability = rs.getString("slot" + i);
					if (ability == null) {
						continue;
					}
					abilities.put(i, ability);
				}
				if (type == ClassType.MANCER) {
					new Mancer(player, abilities);
				} else if (type == ClassType.MECHANIST) {
					new Mechanist(player, abilities);
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
	
	public abstract boolean canUse(String ability);
}
