package net.ausiasmarch.romcixo.connection.implementation;

import java.sql.Connection;
import java.sql.SQLException;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import net.ausiasmarch.romcixo.connection.interfaces.PoolInterface;

public class BoneCPConnectionImplementation implements PoolInterface {

    private BoneCP oPool;

    public BoneCPConnectionImplementation(String connectionChain, String login, String password, Integer databaseMinPoolSize, Integer databaseMaxPoolSize) throws SQLException {
        BoneCPConfig config = new BoneCPConfig();
        config.setJdbcUrl(connectionChain); // jdbc url specific to your database, eg jdbc:mysql://127.0.0.1/yourdb
        config.setUsername(login);
        config.setPassword(password);
        config.setMinConnectionsPerPartition(databaseMinPoolSize);
        config.setMaxConnectionsPerPartition(databaseMaxPoolSize);
        config.setPartitionCount(1);
        oPool = new BoneCP(config);
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
