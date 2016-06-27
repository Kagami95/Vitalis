package com.teamvitalis.vitalis.skilltree;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.teamvitalis.vitalis.Vitalis;
import com.teamvitalis.vitalis.api.CoreAbility;
import com.teamvitalis.vitalis.object.Lang;
import com.teamvitalis.vitalis.skilltree.SkillTreeBuilder.SkillTreeClickListener;

public abstract class SkillTreeObject implements ISkillTreeObject, Listener {

	private static final List<SkillTreeObject> INSTANCES = new ArrayList<>();

	protected Lang name;
	protected ItemStack icon;
	protected int id;
	protected final List<ISkillTreeObject> parents = new ArrayList<>();
	protected final List<CoreAbility> abilities = new ArrayList<>();
	protected SkillTreeClickListener stcl;
	
	/**
	 * <h1><b>DO NOT CALL THIS CONSTRUCTOR MANUALLY!!</b></h1>
	 */
	public SkillTreeObject(Vitalis plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@Override
	public String getDisplayName() {
		return name.toString();
	}

	@Override
	public ItemStack getDisplayIcon() {
		return icon;
	}

	@Override
	public ISkillTreeObject[] getParents() {
		return parents.toArray(new ISkillTreeObject[parents.size()]);
	}

	public void addParent(ISkillTreeObject isto) {
		parents.add(isto);
	}
	
	public void removeParent(ISkillTreeObject isto) {
		parents.remove(isto);
	}
	
	@Override
	public CoreAbility[] getAbilities() {
		return abilities.toArray(new CoreAbility[abilities.size()]);
	}

	@Override
	public int getID() {
		return id;
	}
	
	public static SkillTreeObject register(SkillTreeObject sto) {
		int id = sto.getID();
		if (INSTANCES.stream().filter(s -> s.getID() == id).count() != 0)
			return INSTANCES.get(id);
		INSTANCES.add(sto);
		return sto;
	}
	
	@EventHandler
	public abstract void clicked(InventoryClickEvent event);
	
}