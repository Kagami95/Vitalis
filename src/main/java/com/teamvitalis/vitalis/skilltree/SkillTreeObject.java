package com.teamvitalis.vitalis.skilltree;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.teamvitalis.vitalis.Vitalis;
import com.teamvitalis.vitalis.api.BaseCast;
import com.teamvitalis.vitalis.object.Lang;
import com.teamvitalis.vitalis.skilltree.SkillTreeBuilder.SkillTreeClickListener;

public abstract class SkillTreeObject implements ISkillTreeObject, Listener {

	private static final List<SkillTreeObject> INSTANCES = new ArrayList<>();

	protected Lang name;
	protected ItemStack icon;
	protected int id;
	protected final List<ISkillTreeObject> parents = new ArrayList<>();
	protected final List<BaseCast> abilities = new ArrayList<>();
	protected SkillTreeClickListener stcl;
	
	/**
	 * <h1><b>DO NOT CALL THIS CONSTRUCTOR MANUALLY!!</b></h1>
	 */
	public SkillTreeObject(Vitalis plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@Override
	public Lang getDisplayName() {
		return name;
	}

	public void setDisplayName(Lang l) {
		name = l;
	}
	
	@Override
	public ItemStack getDisplayIcon() {
		return icon;
	}

	public void setDisplayIcon(ItemStack is) {
		icon = is;
	}
	
	@Override
	public ISkillTreeObject[] getParents() {
		return parents.toArray(new ISkillTreeObject[parents.size()]);
	}

	public void setParents(ISkillTreeObject... objects) {
		parents.clear();
		for (ISkillTreeObject isto : objects)
			parents.add(isto);
	}
	
	public void addParent(ISkillTreeObject isto) {
		parents.add(isto);
	}
	
	public void removeParent(ISkillTreeObject isto) {
		parents.remove(isto);
	}
	
	@Override
	public BaseCast[] getAbilities() {
		return abilities.toArray(new BaseCast[abilities.size()]);
	}
	
	public void setAbilities(BaseCast... cas) {
		abilities.clear();
		for (BaseCast ca : cas)
			abilities.add(ca);
	}
	
	@Override
	public int getID() {
		return id;
	}
	
	public void setID(int i) {
		id = i;
	}
	
	public static SkillTreeObject register(SkillTreeObject sto) {
		int id = sto.getID();
		SkillTreeObject sto2 = INSTANCES.get(id);
		if (sto2 != null)
			return sto2;
		INSTANCES.add(sto);
		return sto;
	}
	
	public void setClickAction(SkillTreeClickListener stcl) {
		this.stcl = stcl;
	}
}