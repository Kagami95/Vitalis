package com.teamvitalis.vitalis.guis;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.teamvitalis.vitalis.object.Lang;
import com.teamvitalis.vitalis.object.VitalisPlayer;
import com.teamvitalis.vitalis.skilltree.SkillTreeObject;

public class SkillTreeInventory extends AGui {

	private VitalisPlayer vplayer;
	private int pageid;

	public SkillTreeInventory(VitalisPlayer vplayer, int pageid) {
		super (Lang.GUI_SKILL_TREE.toString());
		this.vplayer = vplayer;
		this.pageid = pageid;
	}

	@Override
	public int getSize() {
		return 36;
	}

	@Override
	public ItemStack[] getItems() {
		List<ItemStack> items = new ArrayList<ItemStack>();
        for (SkillTreeObject sto : SkillTreeObject.getAccessibleSkillTreeObjects(vplayer)) {
            ItemStack item = sto.getDisplayIcon();
            if (ArrayUtils.contains(vplayer.getUnlockedSkillTreeObjects(), sto.getID())) {
                //TODO: Make item enchanted or have so appearance;
            	//Temporary Solution:
            	ItemMeta meta = item.getItemMeta();
            	List<String> lore = new ArrayList<>();
            	lore.add("Already Unlocked");
            	meta.setLore(lore);
            	item.setItemMeta(meta);
            }
            items.add(item);
        }
		if (items.size() > 27) {
			List<ItemStack> newitems = new ArrayList<ItemStack>();
			for (int i = (pageid*27); i < (27 + (pageid*27)); i++) {
				if (i < items.size()) {
					newitems.add(items.get(i));
				}
			}
			return newitems.toArray(new ItemStack[newitems.size()]);
		} else {
			return items.toArray(new ItemStack[items.size()]);
		}
	}

	@Override
	public void execute(Player player, ItemStack item, int position) {
		AGui inventory = new SkillTreeInventory(vplayer, 0);
		if (position == 34 || position == 35) {
			if (position == (inventory.getSize() - 1)) {
				pageid++;
				if (pageid > (Math.round((SkillTreeObject.getAccessibleSkillTreeObjects(vplayer).size()/27) + 0.5) - 1)) {
					pageid--;
				}
			}
			if (position == (inventory.getSize() - 2)) {
				pageid--;
				if (pageid < 0) {
					pageid = 0;
				}
			}
			open(player, vplayer, pageid);
		} else {
			SkillTreeObject sto = SkillTreeObject.getAccessibleSkillTreeObjects(vplayer).get((pageid * 27) + position);
            if (ArrayUtils.contains(vplayer.getUnlockedSkillTreeObjects(), sto.getID())) {
            	player.sendMessage(Lang.GUI_SKILL_TREE_ALREADY_UNLOCKED.toString());
            } else if (sto.hasRequirements(vplayer)) {
            	Integer[] unlockedArray = vplayer.getUnlockedSkillTreeObjects();
            	ArrayUtils.add(unlockedArray, sto.getID());
            	vplayer.setUnlockedSkillTreeObjects(unlockedArray);
            	player.sendMessage(Lang.GUI_SKILL_TREE_NEW_UNLOCK.toString(true, sto.getDisplayName().toString()));
            } else {
            	player.sendMessage(Lang.GUI_SKILL_TREE_LOCKED.toString());
            }
		}
	}

	@Override
	public void open(Player player, Object... object) {
		if (object.length == 2) {
			this.vplayer = (VitalisPlayer) object[0];
			this.pageid = (int) object[1];
		}
		GuiBuilder gui = new GuiBuilder(getName(), getSize());
		int pos = 0;
		for (ItemStack i : getItems()) {
			gui.setOption(pos++, i, i.getItemMeta().getDisplayName(), i.getItemMeta().getLore().toArray(new String[i.getItemMeta().getLore().size()]));
		}
		gui.setOption(getSize()-2, new ItemStack(Material.PAPER, 1), ChatColor.WHITE + "[<] Previous Page", ChatColor.GRAY + "Jump to the previous page.");
		gui.setOption(getSize()-1, new ItemStack(Material.PAPER, 1), ChatColor.WHITE + "[>] Next Page", ChatColor.GRAY + "Jump to the next page.");
		gui.open(player);
		addViewer(player);
	}

	@Override
	public void close(Player player) {
		super.close(player);
		if (getViewers(this.getName()).size() == 0) {
			getViewers().remove(this.getName());
			getPages().remove(this.getName());
		}
	}
}