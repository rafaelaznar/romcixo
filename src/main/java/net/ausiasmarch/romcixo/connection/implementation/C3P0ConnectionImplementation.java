package net.ausiasmarch.romcixo.connection.implementation;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import net.ausiasmarch.romcixo.connection.interfaces.PoolInterface;

public class C3P0ConnectionImplementation implements PoolInterface {

    private ComboPooledDataSource oPool;

    public C3P0ConnectionImplementation(String connectionChain, String login, 
            String password, Integer databaseMinPoolSize, Integer databaseMaxPoolSize) {
        oPool = new ComboPooledDataSource();
        oPool.setJdbcUrl(connectionChain);
        oPool.setUser(login);
        oPool.setPassword(password);
        oPool.setMaxPoolSize(databaseMinPoolSize);
        oPool.setMinPoolSize(databaseMaxPoolSize);
        oPool.setInitialPoolSize(5);
        oPool.setAcquireIncrement(5);
        oPool.setMaxStatements(100);
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
