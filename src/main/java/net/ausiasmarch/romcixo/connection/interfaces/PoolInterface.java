package net.ausiasmarch.romcixo.connection.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

public interface PoolInterface {       
    
    public Connection newConnection() throws SQLException ;
    
    public void closePool() throws SQLException;
    
}
