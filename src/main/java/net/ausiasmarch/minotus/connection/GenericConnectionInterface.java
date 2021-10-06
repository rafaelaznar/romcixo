
package net.ausiasmarch.minotus.connection;


import java.sql.Connection;
import java.sql.SQLException;


public interface GenericConnectionInterface {
    Connection crearConexion(String host, String port, String dbname, String username, String pass)throws SQLException,ClassNotFoundException;
}