package com.teamvitalis.vitalis.skilltree;

import org.bukkit.inventory.ItemStack;

import com.teamvitalis.vitalis.api.BaseCast;
import com.teamvitalis.vitalis.object.ClassType;
import com.teamvitalis.vitalis.object.Lang;
import com.teamvitalis.vitalis.object.MagicType;

public interface ISkillTreeObject {
	
	public Lang getDisplayName();
	
	public ItemStack getDisplayIcon();
	
	public ClassType getClassType();
	
	public MagicType getMagicType();
	
	public ISkillTreeObject[] getParents();
	
	public BaseCast[] getAbilities();
	
	public int getID();
	
}