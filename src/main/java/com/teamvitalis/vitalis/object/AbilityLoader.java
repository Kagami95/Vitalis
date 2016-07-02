package com.teamvitalis.vitalis.object;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import com.teamvitalis.vitalis.Vitalis;
import com.teamvitalis.vitalis.api.AddonAbility;
import com.teamvitalis.vitalis.api.CoreAbility;

import sun.reflect.ReflectionFactory;

public class AbilityLoader {
	
	private JavaPlugin plugin;
	private AbilityLog logger;

	public AbilityLoader(JavaPlugin plugin) {
		this.plugin = plugin;
		this.logger = new AbilityLog(Vitalis.plugin(), plugin.getName());
		logger.modifyLine("Ability log for " + plugin.getName() + " plugin:");
		logger.modifyLine("Created: " + logger.getDateString());
		logger.skipLine();
	}
	
	public void loadAbilities(String packageName) {
		List<String> disabled = new ArrayList<String>();
		int found = 0;
		int failed = 0;
		if (!plugin.isEnabled()) {
			return;
		}
		
		Class<?> pluginClass = plugin.getClass();
		ClassLoader loader = pluginClass.getClassLoader();
		ReflectionFactory rf = ReflectionFactory.getReflectionFactory();
		
		try {
			for (final ClassInfo info : ClassPath.from(loader).getAllClasses()) {
				if (!info.getPackageName().startsWith(packageName)) {
					continue;
				}
				
				Class<?> clase = info.load();
				if (!CoreAbility.class.isAssignableFrom(clase) || clase.isInterface() || Modifier.isAbstract(clase.getModifiers())) {
					continue;
				}
				
				Constructor<?> objDef = CoreAbility.class.getDeclaredConstructor();
				Constructor<?> fake = rf.newConstructorForSerialization(clase, objDef);
				CoreAbility ability = (CoreAbility) clase.cast(fake.newInstance());
				found += 1;
				
				if (ability == null || ability.getName() == null) {
					failed += 1;
					continue;
				} else if (!ability.isEnabled() && !disabled.contains(ability.getName())) {
					logger.modifyLine(ability.getName() + " is disabled!");
					disabled.add(ability.getName());
					continue;
				}
				
				new AbilityInfo(ability);
				logger.modifyLine(ability.getName() + " has been successfully loaded!");
				
				if (ability instanceof AddonAbility) {
					AddonAbility addon = (AddonAbility) ability;
					addon.load();
				}
			}
		} catch (Exception e) {
			logger.modifyLine("ERROR ERROR ERROR");
			e.printStackTrace();
		}
		logger.modifyLine("\nResults: ");
		logger.modifyLine(found + " abilities found!");
		logger.modifyLine(disabled.size() + " abilities disabled!");
		logger.modifyLine(failed + " abilities failed loading!");
	}
}
