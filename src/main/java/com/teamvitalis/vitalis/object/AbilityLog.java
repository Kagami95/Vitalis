package com.teamvitalis.vitalis.object;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.teamvitalis.vitalis.Vitalis;

public class AbilityLog {

	private File log;
	private File logFolder;
	private Date date;
	
	public AbilityLog(Vitalis plugin, String name) {
		date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
		logFolder = new File(plugin.getDataFolder() + File.separator + "/logs/");
		if (!logFolder.exists()) {
			try {
				logFolder.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log = new File(logFolder, name + "_AbilityLog_" + format.format(date) + ".txt");
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
	
	public void skipLine() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(log, true));
			PrintWriter print = new PrintWriter(writer);
			print.println("");
			print.flush();
			print.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getDateString() {
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		return format.format(date);
	}
}
