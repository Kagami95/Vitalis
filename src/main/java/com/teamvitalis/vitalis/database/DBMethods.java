package com.teamvitalis.vitalis.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.scheduler.BukkitRunnable;

import com.teamvitalis.vitalis.Vitalis;

public class DBMethods {
	
	private static Connection connection = null;
	private static Database database = null;
	
	public DBMethods() {
		database = Vitalis.database();
		try {
			connection = database.getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void configureDatabase() {
		if (!tableExists("vitalis_players")) {
			String query = "CREATE TABLE vitalis_players (uuid %uuid, name %name, class %class, slot1 %1, slot2 %2, slot3 %3, slot4 %4, slot5 %5, slot6 %6, slot7 %7, slot8 %8, slot9 %9)";
			if (database.getDriver().equalsIgnoreCase("mysql")) {
				query = query.replace("%uuid", "varchar(36)").replace("%name", "varchar(255)").replace("class", "varchar(16)").replace("%1", "varchar(16)")
						.replace("%2", "varchar(16)").replace("%3", "varchar(16)").replace("%4", "varchar(16)").replace("%5", "varchar(16)")
						.replace("%6", "varchar(16)").replace("%7", "varchar(16)").replace("%8", "varchar(16)").replace("%9", "varchar(16)");
			} else if (database.getDriver().equalsIgnoreCase("sqlite")) {
				query = query.replace("%uuid", "TEXT(36)").replace("%name", "TEXT(255)").replace("class", "TEXT(16)").replace("%1", "TEXT(16)")
						.replace("%2", "TEXT(16)").replace("%3", "TEXT(16)").replace("%4", "TEXT(16)").replace("%5", "TEXT(16)")
						.replace("%6", "TEXT(16)").replace("%7", "TEXT(16)").replace("%8", "TEXT(16)").replace("%9", "TEXT(16)");
			}
			modifyQuery(query);
		}
	}
	/**
     * Close connection to Database.
     */
    public static void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Vitalis.logger().severe("No connection is open currently");
        }
    }

    /**
     * Queries the Database, for queries which modify data.
     *
     * @param query Query to run
     */
    public static void modifyQuery(final String query) {
    	new BukkitRunnable() {
    		@Override
    		public void run() {
    			try {
    				PreparedStatement stmt = connection.prepareStatement(query);
    				stmt.execute();
    				stmt.close();
    			} catch (SQLException e) {
    				e.printStackTrace();
    			}
    		}
    	}.runTaskAsynchronously(Vitalis.plugin());
    }

    /**
     * Queries the Database, for queries which return results.
     *
     * @param query Query to run
     * @return Result set of ran query
     */
    public static ResultSet readQuery(String query) {
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            return rs;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Check database to see if a table exists.
     * 
     * @param table Table name to check
     * @return true if table exists, else false
     */
    public static boolean tableExists(String table) {
        try {
            DatabaseMetaData dmd = connection.getMetaData();
            ResultSet rs = dmd.getTables(null, null, table, null);
            
            return rs.next();
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
}
}
