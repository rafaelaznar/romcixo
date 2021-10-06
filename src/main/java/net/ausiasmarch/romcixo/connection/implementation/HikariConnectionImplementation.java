package net.ausiasmarch.romcixo.connection.implementation;

import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.ausiasmarch.romcixo.connection.interfaces.PoolInterface;

public class HikariConnectionImplementation implements PoolInterface {

    private HikariDataSource oPool;

    public HikariConnectionImplementation(String connectionChain, String login, String password, Integer databaseMinPoolSize, Integer databaseMaxPoolSize) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(connectionChain);
        config.setUsername(login);
        config.setPassword(password);
        config.setMaximumPoolSize(databaseMaxPoolSize);
        config.setMinimumIdle(databaseMinPoolSize);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setLeakDetectionThreshold(15000);
        config.setConnectionTestQuery("SELECT 1");
        config.setConnectionTimeout(2000);

        oPool = new HikariDataSource(config);
    }

    @Override
    public Connection newConnection() throws SQLException {
        return oPool.getConnection();
    }

    @Override
    public void closePool() throws SQLException {
        if (oPool != null) {
            oPool.close();
        }
    }

}
