package com.teamvitalis.vitalis.guis;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * GuiBuilder modified version of:
 * https://bukkit.org/threads/icon-menu.108342/
 */
public class GuiBuilder {

	private String name;
    private int size;
   
    private String[] optionNames;
    private ItemStack[] optionIcons;
   
    /**
     * Create a new Inventory Builder.
     * @param name
     * @param size
     */
    public GuiBuilder(String name, int size) {
        this.name = name;
        this.size = size;
        this.optionNames = new String[size];
        this.optionIcons = new ItemStack[size];
    }
   
    /**
     * Set options (ItemStack icons) that appear in the GUI.
     * @param position Slot to place the ItemStack.
     * @param icon ItemStack to be displayed.
     * @param name Name of the Icon.
     * @param info Lore of the Icon.
     * @return
     */
    public GuiBuilder setOption(int position, ItemStack icon, String name, String... info) {
        optionNames[position] = name;
        optionIcons[position] = setItemNameAndLore(icon, name, info);
        return this;
    }
   
    /**
     * Opens the GUI for the player.
     * @param player Player to open the GUI for.
     */
    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(player, size, name);
        for (int i = 0; i < optionIcons.length; i++) {
            if (optionIcons[i] != null) {
                inventory.setItem(i, optionIcons[i]);
            }
        }
        player.openInventory(inventory);
    }
    
    private ItemStack setItemNameAndLore(ItemStack item, String name, String[] lore) {
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        im.setLore(Arrays.asList(lore));
        item.setItemMeta(im);
        return item;
    }
}