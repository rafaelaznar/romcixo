package net.ausiasmarch.romcixo.connection.implementation;

import java.sql.Connection;
import java.sql.SQLException;
import org.vibur.dbcp.ViburDBCPDataSource;
import net.ausiasmarch.romcixo.connection.interfaces.PoolInterface;

public class ViburConnectionImplementation implements PoolInterface {

    private ViburDBCPDataSource oPool;

    public ViburConnectionImplementation(String connectionChain, String login, String password, Integer databaseMinPoolSize, Integer databaseMaxPoolSize) {

        oPool = new ViburDBCPDataSource();
        oPool.setJdbcUrl(connectionChain);
        oPool.setUsername(login);
        oPool.setPassword(password);
        oPool.setPoolMaxSize(databaseMaxPoolSize);
        oPool.setPoolInitialSize(databaseMinPoolSize);

        oPool.setConnectionIdleLimitInSeconds(30);
        oPool.setTestConnectionQuery("isValid");

        oPool.setLogQueryExecutionLongerThanMs(500);
        oPool.setLogStackTraceForLongQueryExecution(true);

        oPool.setStatementCacheMaxSize(200);
        try {
            oPool.start();
        } catch (Exception e) {
            System.out.print("error: " + e.getMessage());
        }
    }

    @Override
    public Connection newConnection() throws SQLException {
        return (Connection) oPool.getConnection();
    }

    @Override
    public void closePool() throws SQLException {
        if (oPool != null) {
            oPool.close();
        }
    }
}
