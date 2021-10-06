package net.ausiasmarch.minotus.connection;

import net.ausiasmarch.minotus.connection.ConnectionTypeEnums.TipoDeConexion;

public class ConnectionTypeFactory {

    public static GenericConnectionType getConnectionType(TipoDeConexion eConnectionType) throws Exception {
        if (eConnectionType == TipoDeConexion.DriverManager) {
            return new DriverManagerConnection();
        } else if (eConnectionType == TipoDeConexion.DataSource) {
            return new DataSourceConnection();
        } else {
            throw new Exception("operation not allowed");
        }
    }
}
