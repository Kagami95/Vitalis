package com.teamvitalis.vitalis.skilltree;

import org.bukkit.inventory.ItemStack;

import com.teamvitalis.vitalis.api.BaseCast;
import com.teamvitalis.vitalis.object.Lang;

public interface ISkillTreeObject {
	
	public Lang getDisplayName();
	
	public ItemStack getDisplayIcon();
	
	public ISkillTreeObject[] getParents();
	
	public BaseCast[] getAbilities();
	
	public int getID();
	
}