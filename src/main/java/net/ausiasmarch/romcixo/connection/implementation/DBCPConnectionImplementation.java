package net.ausiasmarch.romcixo.connection.implementation;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;
import net.ausiasmarch.romcixo.connection.interfaces.PoolInterface;

public class DBCPConnectionImplementation implements PoolInterface {

    private Connection oConnection;
    private BasicDataSource oPool;

    public DBCPConnectionImplementation(String connectionChain, String login, String password, Integer databaseMinPoolSize, Integer databaseMaxPoolSize) {
        oPool = new BasicDataSource();
        oPool.setUrl(connectionChain);
        oPool.setUsername(login);
        oPool.setPassword(password);
        oPool.setMaxIdle(databaseMaxPoolSize);
        oPool.setMinIdle(databaseMinPoolSize);

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
