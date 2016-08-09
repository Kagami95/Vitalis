package com.teamvitalis.vitalis.object;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.teamvitalis.vitalis.Vitalis;
import com.teamvitalis.vitalis.casts.ether.VoidTrap;
import com.teamvitalis.vitalis.casts.pyro.PyroBlast;
import com.teamvitalis.vitalis.configuration.CastConfig;

public class CastInfo {
	
	private static CastLog log;
	
	private static HashMap<String, CastInfo> nameMap = new HashMap<>();
	private static HashMap<UUID, CastInfo> uuidMap = new HashMap<>();
	private static HashMap<Class<?>, CastInfo> classMap = new HashMap<>();
	
	private static HashMap<ClassType, List<CastInfo>> classes = new HashMap<>();
	private static HashMap<MagicType, List<CastInfo>> magics = new HashMap<>();
	
	private String name;
	private UUID uuid;
	private Class<?> clazz;
	private String description;
	private boolean enabled;
	private ClassType type;
	private MagicType magic;
	private int defaultUses;
	
	static {
		classes.put(ClassType.MANCER, new ArrayList<CastInfo>());
		classes.put(ClassType.MECHANIST, new ArrayList<CastInfo>());
		for (MagicType magic : MagicType.values()) {
			magics.put(magic, new ArrayList<CastInfo>());
		}
		log = new CastLog(Vitalis.plugin(), "Vitalis");
	}
	
	public CastInfo(String name, UUID uuid, Class<?> clazz, String description, ClassType type, MagicType magic) {
		this(name, uuid, clazz, description, type, magic, 0);
	}

	public CastInfo(String name, UUID uuid, Class<?> clazz, String description, ClassType type, MagicType magic, Integer uses) {
		String path = "Casts.<class>.<magic>.<cast>.Enabled";
		if (type == ClassType.MANCER) {
			path = path.replace("<class>", "Magic");
			path = path.replace("<magic>", magic.getName().replace("mancy", ""));
		} else {
			path = path.replace("<class>", "Mech");
			path = path.replace("<magic>.", "");
		}
		path = path.replace("<cast>", name);
		
		enabled = CastConfig.get().getBoolean(path);
		this.name = name;
		this.uuid = uuid;
		this.clazz = clazz;
		this.description = description;
		this.type = type;
		this.magic = magic;
		defaultUses = uses;
		nameMap.put(name.toLowerCase(), this);
		uuidMap.put(uuid, this);
		classMap.put(clazz, this);
		classes.get(type).add(this);
		magics.get(magic).add(this);
		
		String msg = name + " (" + uuid.toString() + ") was loaded. Status: ?";
		if (enabled) {
			msg = msg.replace("?", "ENABLED");
		} else {
			msg = msg.replace("?", "DISABLED");
		}
		log.modifyNewLine(msg);
	}
	
	public String getName() {
		return name;
	}
	
	public UUID getUniqueID() {
		return uuid;
	}
	
	public Class<?> getCastClass() {
		return clazz;
	}
	
	public String getDescription() {
		return description;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public ClassType getClassType() {
		return type;
	}
	
	public MagicType getMagicType() {
		return magic;
	}
	
	public int getDefaultUses() {
		return defaultUses;
	}
	
	public static CastInfo fromName(String name) {
		return nameMap.containsKey(name.toLowerCase()) ? nameMap.get(name.toLowerCase()) : null;
	}
	
	public static CastInfo fromUUID(UUID uuid) {
		return uuidMap.containsKey(uuid) ? uuidMap.get(uuid) : null;
	}
	
	public static CastInfo fromClass(Class<?> clazz) {
		return classMap.containsKey(clazz) ? classMap.get(clazz) : null;
	}
	
	public static List<CastInfo> getListByClassType(ClassType type) {
		return classes.get(type);
	}
	
	public static List<CastInfo> getListByMagicType(MagicType magic) {
		return magics.get(magic);
	}
	
	public static Collection<CastInfo> getAllCasts() {
		return nameMap.values();
	}
	
	public static void registerCasts() {
		new CastInfo("PyroBlast", UUID.fromString("6868ff61-7611-4f50-9c74-5d98c43dcdf9"), PyroBlast.class, "Click to throw a blast of fire in the clicked direction. As the blast goes, it will slowly increase in size.", ClassType.MANCER, MagicType.PYRO);
		new CastInfo("VoidTrap", UUID.fromString("56486fb9-70ab-47e1-9e69-b7e99375e154"), VoidTrap.class, "Create a black hole that sucks all living things into the void. All things sucked into the void must break out by smashing the dimensional barrier (hit rapidy lots). Hold Sneak to charge until a black hole forms.", ClassType.MANCER, MagicType.ETHER);
		log.close();
	}
}
