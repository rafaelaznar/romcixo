package net.ausiasmarch.romcixo.control;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.ausiasmarch.romcixo.connection.GenericConnectionInterface;
import net.ausiasmarch.romcixo.connection.factory.PoolFactory;
import net.ausiasmarch.romcixo.connection.interfaces.PoolInterface;

public class control extends HttpServlet {

    Properties properties = new Properties();

    private void loadResourceProperties() throws FileNotFoundException, IOException {
        // https://stackoverflow.com/questions/44499306/how-to-read-application-properties-file-without-environment?noredirect=1&lq=1
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try ( InputStream resourceStream = loader.getResourceAsStream("application.properties")) {
            properties.load(resourceStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doCORS(HttpServletRequest oRequest, HttpServletResponse oResponse) {
        oResponse.setContentType("application/json;charset=UTF-8");
        if (!(oRequest.getMethod().equalsIgnoreCase("OPTIONS"))) {
            oResponse.setHeader("Cache-control", "no-cache, no-store");
            oResponse.setHeader("Pragma", "no-cache");
            oResponse.setHeader("Expires", "-1");
            oResponse.setHeader("Access-Control-Allow-Origin", oRequest.getHeader("origin"));
            oResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD,PATCH");
            oResponse.setHeader("Access-Control-Max-Age", "86400");
            oResponse.setHeader("Access-Control-Allow-Credentials", "true");
            oResponse.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, "
                    + "Origin, "
                    + "Accept, "
                    + "Authorization, "
                    + "ResponseType, "
                    + "Observe, "
                    + "X-Requested-With, "
                    + "Content-Type, "
                    + "Access-Control-Expose-Headers, "
                    + "Access-Control-Request-Method, "
                    + "Access-Control-Request-Headers");
        } else {
            // https://stackoverflow.com/questions/56479150/access-blocked-by-cors-policy-response-to-preflight-request-doesnt-pass-access
            System.out.println("Pre-flight");
            oResponse.setHeader("Access-Control-Allow-Origin", oRequest.getHeader("origin"));
            oResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD,PATCH");
            oResponse.setHeader("Access-Control-Max-Age", "3600");
            oResponse.setHeader("Access-Control-Allow-Credentials", "true");
            oResponse.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, "
                    + "Origin, "
                    + "Accept, "
                    + "Authorization, "
                    + "ResponseType, "
                    + "Observe, "
                    + "X-Requested-With, "
                    + "Content-Type, "
                    + "Access-Control-Expose-Headers, "
                    + "Access-Control-Request-Method, "
                    + "Access-Control-Request-Headers");
            oResponse.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doCORS(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        loadResourceProperties();
        doCORS(request, response);
        Gson oGson = new Gson();
        GenericConnectionInterface oConnectionObject = null;
        String dbversion;

        try ( PrintWriter out = response.getWriter()) {
            Class.forName("com.mysql.jdbc.Driver");
            PoolInterface oConnectionPool = null;
            oConnectionPool = PoolFactory.getPool(
                    properties.getProperty("connectionPool"),
                    properties.getProperty("database.host"),
                    properties.getProperty("database.port"),
                    properties.getProperty("database.dbname"),
                    properties.getProperty("database.username"),
                    properties.getProperty("database.password"),
                    Integer.parseInt(properties.getProperty("databaseMinPoolSize")),
                    Integer.parseInt(properties.getProperty("databaseMaxPoolSize"))
            );
            Connection oConnection = oConnectionPool.newConnection();
            Statement stmt = oConnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT version()");
            if (rs.next()) {
                dbversion = "Database Version : " + rs.getString(1);
            } else {
                throw new Exception("Error al obtener la versión de la base de datos");
            }
            oConnection.close();
            oConnectionPool.closePool();

            response.setStatus(HttpServletResponse.SC_OK);
            out.print(oGson.toJson(dbversion));
        } catch (Exception ex) {
            PrintWriter out = response.getWriter();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(oGson.toJson(ex.getMessage()));
        }
    }

}
