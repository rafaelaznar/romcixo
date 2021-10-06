package net.ausiasmarch.romcixo.connection;

import org.apache.commons.dbcp.BasicDataSource;
import java.sql.Connection;

public class DBCPConnectionImplementation {

    private Connection oConnection;
    private BasicDataSource oPool;

    public DBCPConnectionImplementation() {

        oPool = new BasicDataSource();
        

    }

}
