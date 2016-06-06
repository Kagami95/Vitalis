package com.teamvitalis.vitalis.object;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import com.teamvitalis.vitalis.api.AddonAbility;
import com.teamvitalis.vitalis.api.CoreAbility;
import com.teamvitalis.vitalis.utils.ReflectionUtils;

public class AbilityLoader {
	
	private JavaPlugin plugin;

	public AbilityLoader(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	
	public void loadAbilities(String packageName) {
		List<String> disabled = new ArrayList<String>();
		if (!plugin.isEnabled()) {
			return;
		}
		
		Class<?> pluginClass = plugin.getClass();
		ClassLoader loader = pluginClass.getClassLoader();
		
		try {
			for (final ClassInfo info : ClassPath.from(loader).getAllClasses()) {
				if (!info.getPackageName().startsWith(packageName)) {
					continue;
				}
				
				Class<?> clase = info.getClass();
				if (!CoreAbility.class.isAssignableFrom(clase) || clase.isInterface() || Modifier.isAbstract(clase.getModifiers())) {
					continue;
				}
				
				Constructor<?> fake = ReflectionUtils.getConstructor(CoreAbility.class, (Class<?>[])null);
				CoreAbility ability = (CoreAbility) clase.cast(fake.newInstance());
				
				if (ability == null || ability.getName() == null) {
					continue;
				} else if (!ability.isEnabled() && !disabled.contains(ability.getName())) {
					disabled.add(ability.getName());
					continue;
				}
				
				new AbilityInfo(ability);
				
				if (ability instanceof AddonAbility) {
					AddonAbility addon = (AddonAbility) ability;
					addon.load();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
