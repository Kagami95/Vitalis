package com.teamvitalis.vitalis.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Database {

    private String driver = "sqlite";
    private String address = "localhost";
    private String port = "3306";
    private String database = "database";
    private String username = "user";
    private String password = "pass";
    private HikariDataSource dataSource;

    HikariConfig config = new HikariConfig();

    // Database Methods
    public Database(String driver, String address, String port,
                    String database, String username, String password) {
        this.driver = driver;
        this.address = address;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;

        this.dataSource = applyConfiguration();

    }

    public Database() {
        this.dataSource = applyConfiguration();
    }

    private HikariDataSource applyConfiguration() {
        switch (this.driver) {
            case "mysql": {
                this.config.setJdbcUrl("jdbc:mysql://" + this.address + ":" +
                        this.port + "/" + this.database);
                this.config.setUsername(this.username);
                this.config.setPassword(this.password);
                this.config.addDataSourceProperty("cachePrepStmts", "true");
                this.config.addDataSourceProperty("prepStmtCacheSize", "250");
                this.config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
                return new HikariDataSource(config);
            }
            case "sqlite": {
                this.config.setPoolName("AuthMeSQLitePool");
                this.config.setDriverClassName("org.sqlite.JDBC");
                this.config.setJdbcUrl("jdbc:sqlite:plugins/AuthMe/" + this.database + ".db");
                this.config.setConnectionTestQuery("SELECT 1");
                this.config.setMaxLifetime(60000);
                this.config.setIdleTimeout(45000);
                this.config.setMaximumPoolSize(50);
                return new HikariDataSource(config);
            }
            default:
                // TODO: Log to console that the database driver set in config is invalid.
                return null;
        }
    }

    // Configuration Methods
    public HikariConfig getConfig() {
        return this.config;
    }

    public String getDriver() {
        return this.driver;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPort() {
        return this.port;
    }

    public String getDatabase() {
        return this.database;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public HikariDataSource getDataSource() {
        return this.dataSource;
    }

}
