package com.teamvitalis.vitalis.skilltree;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.teamvitalis.vitalis.Vitalis;
import com.teamvitalis.vitalis.api.CoreAbility;
import com.teamvitalis.vitalis.object.Lang;
import com.teamvitalis.vitalis.object.VitalisPlayer;

public class SkillTreeBuilder {

	private Vitalis plugin;

	private Lang name;
	private ItemStack icon;
	private int id;
	private final List<ISkillTreeObject> parents = new ArrayList<>();
	private final List<CoreAbility> abilities = new ArrayList<>();
	private SkillTreeClickListener stcl;

	public SkillTreeBuilder(Vitalis plugin) {
		this.plugin = plugin;
	}

	public void clear() {
		name = null;
		icon = null;
		id = -1;
		parents.clear();
		abilities.clear();
		stcl = null;
	}

	public SkillTreeObject buildAndClear() {
		SkillTreeObject skill = build();
		clear();
		return skill;
	}

	public SkillTreeObject build() {
		SkillTreeBuilder builder = this;
		SkillTreeObject skill = new SkillTreeObject(plugin) {
			public SkillTreeObject init() {
				this.name = builder.name;
				this.icon = builder.icon;
				this.id = builder.id;
				this.parents.clear();
				this.parents.addAll(builder.parents);
				this.abilities.clear();
				this.abilities.addAll(builder.abilities);
				this.stcl = builder.stcl;
				plugin.getServer().getPluginManager().registerEvents(this, plugin);
				return this;
			}

			@EventHandler
			public void clicked(InventoryClickEvent event) {
				ItemStack item = event.getCurrentItem();
				if (!item.isSimilar(icon))
					return;
				HumanEntity whoClicked = event.getWhoClicked();
				if (!(whoClicked instanceof Player))
					return;
				VitalisPlayer vp = VitalisPlayer.fromPlayer((Player) whoClicked);
				if (vp == null)
					return;
				Inventory inv = event.getInventory();
				stcl.click(vp, inv);
			}

		}.init();
		return skill;
	}

	public void setSkillTreeClickListener(SkillTreeClickListener stcl) {
		this.stcl = stcl;
	}

	public Lang getName() {
		return name;
	}

	public void setName(Lang name) {
		this.name = name;
	}

	public ItemStack getIcon() {
		return icon;
	}

	public void setIcon(ItemStack icon) {
		this.icon = icon;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ISkillTreeObject> getParents() {
		return parents;
	}

	public List<CoreAbility> getAbilities() {
		return abilities;
	}

}