package com.teamvitalis.vitalis.skilltree;

import org.bukkit.inventory.ItemStack;

import com.teamvitalis.vitalis.api.CoreAbility;

public interface ISkillTreeObject {
	
	public String getDisplayName();
	
	public ItemStack getDisplayIcon();
	
	public ISkillTreeObject[] getParents();
	
	public CoreAbility[] getAbilities();
	
	public int getID();
	
}