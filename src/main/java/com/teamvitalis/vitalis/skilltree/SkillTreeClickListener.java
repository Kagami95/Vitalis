package com.teamvitalis.vitalis.skilltree;

import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.teamvitalis.vitalis.object.VitalisPlayer;

public interface SkillTreeClickListener extends Listener {
	public void click(VitalisPlayer player, InventoryClickEvent inv, SkillTreeObject skillTreeObject);
}