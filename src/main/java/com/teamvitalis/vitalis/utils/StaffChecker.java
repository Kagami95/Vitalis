package com.teamvitalis.vitalis.utils;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import com.teamvitalis.vitalis.Vitalis;

public class StaffChecker {
	
	private static HashMap<UUID, ChatColor> staff = new HashMap<>();

	public StaffChecker() {
		staff.clear();
		fetch();
	}
	
	private static void fetch() {
		new BukkitRunnable() {

			@Override
			public void run() {
				try {
					URL url = new URL("http://pastebin.com/raw/6YebJgCt");
					InputStream is = url.openStream();
					StringWriter sw = new StringWriter();
					IOUtils.copy(is, sw);
					Vitalis.logger().info("Reached remote file");
					String[] split = sw.toString().split("\n");
					for (String s : split) {
						if (s.startsWith("#")) {
							continue;
						}
						String[] entry = s.split("//");
						if (entry[0].matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}")) {
							if (!staff.containsKey(UUID.fromString(entry[0]))) {
								ChatColor color = ChatColor.valueOf(entry[1]);
								if (color == null) {
									color = ChatColor.WHITE;
								}
								staff.put(UUID.fromString(entry[0]), color);
								Vitalis.logger().info("Added " + entry[0] + " to staff map.");
							}
						}
					}
					Vitalis.logger().info("Populated staff map");
				} catch (Exception e) {
					Vitalis.logger().warning("Unable to fetch remote file!");
					e.printStackTrace();
				}
			}
			
		}.runTaskAsynchronously(Vitalis.plugin());
	}
	
	public static HashMap<UUID, ChatColor> getMap() {
		return staff;
	}
}
