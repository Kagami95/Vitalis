package com.teamvitalis.vitalis.object;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.teamvitalis.vitalis.Vitalis;

public class CastLog {

	private File log;
	private File logFolder;
	private Date date;
	private PrintWriter print;
	
	public CastLog(Vitalis plugin, String name) {
		date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
		logFolder = new File(plugin.getDataFolder(), "logs");
		if (!logFolder.exists()) {
			try {
				logFolder.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log = new File(logFolder, name + "_CastLog_" + format.format(date) + ".txt");
		try {
			if (log.exists()) {
				log.delete();
			}
			log.createNewFile();
			print = new PrintWriter(new BufferedWriter(new FileWriter(log, true)));
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
	
	/**
	 * Modifies the current line. Use {@link #endLine(String)} to end the line.
	 * @param line Text to add to the line.
	 */
	public void modifyLine(String line) {
		print.print(line);
	}
	
	/**
	 * Modifies a new line. Should be called after {@link #endLine(String)}
	 * @param line Text to put on new line
	 */
	public void modifyNewLine(String line) {
		print.println(line);
	}
	
	/**
	 * Ends the current line, further modifying will happen on the next line.
	 * @param line Text to end the line
	 */
	public void endLine(String line) {
		print.println(line);
	}
	
	/**
	 * Skips the current line. Should be called after {@link #endLine(String)}
	 */
	public void skipLine() {
		print.println("");
	}
	
	/**
	 * Closes the PrintWriter. Must be called.
	 */
	public void close() {
		print.flush();
		print.close();
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getDateString() {
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		return format.format(date);
	}
}
