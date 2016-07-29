package com.teamvitalis.vitalis.skilltree;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.inventory.ItemStack;

import com.teamvitalis.vitalis.api.BaseCast;
import com.teamvitalis.vitalis.object.ClassType;
import com.teamvitalis.vitalis.object.Lang;
import com.teamvitalis.vitalis.object.MagicType;
import com.teamvitalis.vitalis.object.Mancer;
import com.teamvitalis.vitalis.object.VitalisPlayer;

public abstract class SkillTreeObject implements ISkillTreeObject {

	private static final List<SkillTreeObject> INSTANCES = new ArrayList<>();

	protected Lang name;
	protected ItemStack icon;
	protected ClassType classType;
	protected MagicType magicType;
	protected int id;
	protected final List<ISkillTreeObject> parents = new ArrayList<>();
	protected final List<BaseCast> abilities = new ArrayList<>();
	
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
	public ClassType getClassType() {
		return classType;
	}
	
	public void setClassType(ClassType classType) {
		this.classType = classType;
	}
	
	@Override
	public MagicType getMagicType() {
		return magicType;
	}
	
	public void setMagicType(MagicType magicType) {
		this.magicType = magicType;
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
	
	public static List<SkillTreeObject> getRegisteredSkillTreeObjects() {
		return INSTANCES;
	}
	
	public static List<SkillTreeObject> getAccessibleSkillTreeObjects(VitalisPlayer vplayer) {
		List<SkillTreeObject> STOs = new ArrayList<>();
        for (SkillTreeObject sto : getRegisteredSkillTreeObjects()) {
        	//TODO: Upgrade this to a more efficient system. Something like:
        	//		vplayer.getClassType().getSkillTreeObjects();
        	if (!vplayer.getClassType().equals(sto.getClassType())) continue;
        	if (vplayer instanceof Mancer) {
        		if (((Mancer) vplayer).getMagicType().equals(sto.getMagicType())) continue;
        	}
        	STOs.add(sto);
        }
        return STOs;
	}
	
	public boolean hasRequirements(VitalisPlayer vplayer) {
		if (getParents() == null) return true;
		for (ISkillTreeObject isto : getParents()) {
			if (!ArrayUtils.contains(vplayer.getUnlockedSkillTreeObjects(), isto.getID())) return false;
		}
		return true;
	}
}