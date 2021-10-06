package net.ausiasmarch.romcixo.connection.factory;

import java.sql.SQLException;
import net.ausiasmarch.romcixo.connection.ConnectionData;
import net.ausiasmarch.romcixo.connection.implementation.BoneCPConnectionImplementation;
import net.ausiasmarch.romcixo.connection.implementation.C3P0ConnectionImplementation;
import net.ausiasmarch.romcixo.connection.implementation.DBCPConnectionImplementation;
import net.ausiasmarch.romcixo.connection.implementation.HikariConnectionImplementation;
import net.ausiasmarch.romcixo.connection.implementation.ViburConnectionImplementation;
import net.ausiasmarch.romcixo.connection.interfaces.PoolInterface;

public class PoolFactory {
    private static String getConnectionChain(String databaseHost, String databasePort, String databaseName) {
        return "jdbc:mysql://" + databaseHost + ":" + databasePort + "/"
                + databaseName + "?autoReconnect=true&useSSL=false";
    }

    public static PoolInterface getPool(String poolName, String host, String port,String dbname,String login,String password,Integer databaseMinPoolSize, Integer databaseMaxPoolSize) throws SQLException {
        PoolInterface oConnectionInterface;
        String connectionChain= getConnectionChain(host, password, dbname);
        switch (poolName) {
            case "Hikari":
                oConnectionInterface = new HikariConnectionImplementation(connectionChain, login, password, databaseMinPoolSize,  databaseMaxPoolSize);
                break;
            case "DBCP":
                oConnectionInterface = new DBCPConnectionImplementation(connectionChain, login, password, databaseMinPoolSize,  databaseMaxPoolSize);
                break;
            case "BoneCP":
                oConnectionInterface = new BoneCPConnectionImplementation(connectionChain, login, password, databaseMinPoolSize,  databaseMaxPoolSize);
                break;
            case "C3P0":
                oConnectionInterface = new C3P0ConnectionImplementation(connectionChain, login, password, databaseMinPoolSize,  databaseMaxPoolSize);
                break;
            case "Vibur":
                oConnectionInterface = new ViburConnectionImplementation(connectionChain, login, password, databaseMinPoolSize,  databaseMaxPoolSize);
                break;
            default:
                oConnectionInterface = new HikariConnectionImplementation(connectionChain, login, password, databaseMinPoolSize,  databaseMaxPoolSize);
                break;
        }
        return oConnectionInterface;
    }

}
