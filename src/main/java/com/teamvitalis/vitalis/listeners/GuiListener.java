package com.teamvitalis.vitalis.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.teamvitalis.vitalis.Vitalis;
import com.teamvitalis.vitalis.guis.AGui;
import com.teamvitalis.vitalis.guis.IGui;

public class GuiListener implements Listener {

	Vitalis plugin;

	public GuiListener(Vitalis plugin) {
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onInventoryClick(InventoryClickEvent event) {
		String title = event.getInventory().getName();
		if (AGui.isPage(title)) {
			event.setCancelled(true);
			Player player = (Player) event.getWhoClicked();
			ItemStack item = event.getCurrentItem();
			int position = event.getRawSlot();
			if (item != null && item.getType() != Material.AIR) {
				AGui.getPage(title).execute(player, item, position);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onInventoryClose(InventoryCloseEvent event) {
		String title = event.getInventory().getName();
		if (AGui.isPage(title)) {
			final Player player = (Player) event.getPlayer();
			final IGui inventory = AGui.getPage(title);
			new BukkitRunnable() {
				public void run() {
					if (!player.isOnline() || !AGui.isPage(player.getOpenInventory().getTitle())) {
						AGui.clearPreviousPage(player);
					}
					if (!player.isOnline() || player.getOpenInventory().getTitle() != inventory.getName()) {
						inventory.close(player);
					}
				}
			}.runTaskLaterAsynchronously(plugin, 5);
		}
	}
}