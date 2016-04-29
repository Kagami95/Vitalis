package com.teamvitalis.vitalis.guis;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;

public abstract class AGui implements IGui {

	private static final ConcurrentHashMap<String, IGui> PAGES = new ConcurrentHashMap<>();
	private static final ConcurrentHashMap<String, List<UUID>> VIEWERS = new ConcurrentHashMap<>();
	private static final ConcurrentHashMap<Player, IGui> PREVIOUS = new ConcurrentHashMap<>();
	
	private String title;
	
	/**
	 * Creates a new registered Page.
	 * @param title Title of the page. (What is displayed in the Inventory).
	 */
	public AGui(String title) {
		this.title = title;
		if (isPage(title)) return;
		PAGES.put(title, this);
		VIEWERS.put(title, new ArrayList<UUID>());
	}
	
	@Override
	public String getName() {
		return title;
	}
	
	public void addViewer(Player player) {
		List<UUID> viewers = getViewers(getName());
		if (viewers.contains(player.getUniqueId())) {
			return;
		}
		getViewers(getName()).add(player.getUniqueId());
	}
	
	public void removeViewer(Player player) {
		List<UUID> viewers = getViewers(getName());
		if (!viewers.contains(player.getUniqueId())) {
			return;
		}
		getViewers(getName()).remove(player.getUniqueId());
	}
	
	@Override
	public void close(Player player) {
		removeViewer(player);
	}
	
	/**
	 * Returns all registered pages.
	 * @return ConcurrentHashMap of Pages.
	 */
	public static ConcurrentHashMap<String, IGui> getPages() {
		return PAGES;
	}
	
	/**
	 * Checks if an Inventory is a registered Page.
	 * @param title Title of the Inventory.
	 * @return true if the page is registered.
	 */
	public static boolean isPage(String title) {
		return getPages().containsKey(title);
	}
	
	/**
	 * Returns a page from its title.
	 * @param title Title of the page.
	 * @return Page from title.
	 */
	public static IGui getPage(String title) {
		return getPages().get(title);
	}
	
	/**
	 * Returns all current viewers.
	 * @return
	 */
	public static ConcurrentHashMap<String, List<UUID>> getViewers() {
		return VIEWERS;
	}
	
	/**
	 * Returns a List of UUIDs of viewers of an inventory.
	 * @param title
	 * @return
	 */
	public static List<UUID> getViewers(String title) {
		return getViewers().get(title);
	}
	
	/**
	 * Set the previously viewed inventory page.
	 * Sets the page to what is currently being viewed.
	 * @param player Player viewing the Inventory Page.
	 */
	public void setPreviousPage(Player player) {
		PREVIOUS.put(player, this);
	}
	
	/**
	 * Get the previous page the player was viewing.
	 * @param player Player to check for.
	 * @return Page the player was viewing.
	 */
	public static IGui getPreviousPage(Player player) {
		if (!PREVIOUS.containsKey(player)) {
			return null;
		}
		return PREVIOUS.get(player);
	}
	
	/**
	 * Remove the player's previous pages.
	 * @param player Player to remove.
	 */
	public static void clearPreviousPage(Player player) {
		PREVIOUS.remove(player);
	}
	
	public static ConcurrentHashMap<Player, IGui> getPreviousPages() {
		return PREVIOUS;
	}
}