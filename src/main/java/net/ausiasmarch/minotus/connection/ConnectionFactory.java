package net.ausiasmarch.minotus.connection;

import net.ausiasmarch.minotus.connection.ConnectionTypeEnums.TipoDeConexion;

public class ConnectionFactory {

    public static GenericConnectionInterface getConnectionType(TipoDeConexion eConnectionType) throws Exception {
        if (eConnectionType == TipoDeConexion.DriverManager) {
            return new DriverManagerConnectionImplementation();
        } else if (eConnectionType == TipoDeConexion.DataSource) {
            return new DataSourceConnectionImplementation();
        } else {
            throw new Exception("operation not allowed");
        }
    }
}
