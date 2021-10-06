
package net.ausiasmarch.minotus.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverManagerConnection implements GenericConnectionType {

    @Override
    public Connection crearConexion(String host, String port, String dbname, String username, String pass) throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + dbname, username, pass);
        return connection;
    }

}
