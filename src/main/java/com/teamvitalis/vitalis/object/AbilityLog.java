package com.teamvitalis.vitalis.object;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.teamvitalis.vitalis.Vitalis;

public class AbilityLog {

	private File log;
	private File logFolder;
	
	public AbilityLog(Vitalis plugin, String name) {
		logFolder = new File(plugin.getDataFolder() + File.separator + "/logs/");
		if (!logFolder.exists()) {
			try {
				logFolder.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log = new File(logFolder + File.separator + name + "_ability-log.txt");
		try {
			if (log.exists()) {
				log.delete();
			}
			log.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public File getLog() {
		return log;
	}
	
	public File getLogFolder() {
		return logFolder;
	}
	
	public void modifyLine(String line) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(log, true));
			PrintWriter print = new PrintWriter(writer);
			print.println(line);
			print.flush();
			print.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
}
