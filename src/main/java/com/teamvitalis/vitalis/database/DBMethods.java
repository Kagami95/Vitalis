package com.teamvitalis.vitalis.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.scheduler.BukkitRunnable;

import com.teamvitalis.vitalis.Vitalis;

public class DBMethods {
	
	private static Connection connection;
	private Database database;
	
	public DBMethods(Database database) {
		this.database = database;
		connection = open();
		if (connection == null) {
			Vitalis.logger().severe("[SQL] Disabling plugin due to database error: Connection not established.");
			Vitalis.plugin().getServer().getPluginManager().disablePlugin(Vitalis.plugin());
			return;
		}
		Vitalis.logger().info("[SQL] Connection established!");
	}

	public void configureDatabase() {
		if (!tableExists("vitalis_players")) {
			Vitalis.logger().info("Creating new vitalis_players table");
			String query = "CREATE TABLE vitalis_players (uuid %uuid, name %name, class %class, slot1 %1, slot2 %2, slot3 %3, slot4 %4, slot5 %5, slot6 %6, slot7 %7, slot8 %8, slot9 %9);";
			if (database.getDriver().equalsIgnoreCase("mysql")) {
				query = query.replace("%uuid", "varchar(36)").replace("%name", "varchar(255)").replace("%class", "varchar(16)");
				for (int i = 1; i < 10; i++) {
					query = query.replace("%" + i, "varchar(36)");
				}
			} else if (database.getDriver().equalsIgnoreCase("sqlite")) {
				query = query.replace("%uuid", "TEXT(36)").replace("%name", "TEXT(255)").replace("%class", "TEXT(16)");
				for (int i = 1; i < 10; i++) {
					query = query.replace("%" + i, "TEXT(36)");
				}
			}
			modifyQuery(query);
		}
		/*
		if (!tableExists("ability_uuids")) {
			Vitalis.logger().info("Creating new ability_uuids table");
			String query = "CREATE TABLE ability_uuids (uuid %uuid, default %def, nickname %name)";
			if (database.getDriver().equalsIgnoreCase("mysql")) {
				query = query.replace("%uuid", "varchar(36)").replace("%def", "varchar(255)").replace("%name", "varchar(255)");
			} else if (database.getDriver().equalsIgnoreCase("sqlite")) {
				query = query.replace("%uuid", "TEXT(36)").replace("%def", "TEXT(255)").replace("%name", "TEXT(255)");
			}
			modifyQuery(query);
		}
		*/
	}
	
	public Connection open() {
		try {
			return database.getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
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
