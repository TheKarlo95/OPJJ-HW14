package hr.fer.zemris.java.tecaj_13;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.tecaj_13.dao.sql.SQLConnectionProvider;
import hr.fer.zemris.java.tecaj_13.dao.sql.SQLUtils;

/**
 * {@code Initialization} is a servlet context listener that on every context
 * initialization makes a connection pool and sets it as a servlet context
 * attribute.
 * <p>
 * If file "src/main/webapp/WEB-INF/dbsettings.properties" is missing this
 * application will end with status code 1.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see ServletContextListener
 */
@WebListener
public class Initialization implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String connectionURL = null;

        connectionURL = getConnectionURL(sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties"));

        ComboPooledDataSource cpds = new ComboPooledDataSource();

        try {
            cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
        } catch (PropertyVetoException e1) {
            throw new RuntimeException("Error occured during initialization of a connection pool.", e1);
        }

        cpds.setJdbcUrl(connectionURL);

        sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);

        initializeDatabase(cpds, sce.getServletContext().getRealPath("WEB-INF/polls"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
                .getAttribute("hr.fer.zemris.dbpool");
        if (cpds != null) {
            try {
                DataSources.destroy(cpds);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns the connection URL of the database.
     * 
     * @param configPath
     *            path to a {@code .properties} file that contains resources
     *            needed to make connection URL
     * @return the connection URL of the database
     * @throws NullPointerException
     *             if {@code configPath} parameter is a {@code null} reference
     *             or {@code host}, {@code port}, {@code name}, {@code user} or
     *             {@code password} properties are missing from a file specified
     *             by {@code configPath} parameter.
     */
    private static String getConnectionURL(String configPath) {
        Objects.requireNonNull(
                configPath,
                "You cannot get connection URL with null reference as a path to config file!");

        Properties config = new Properties();

        try {
            config.load(Files.newInputStream(Paths.get(configPath)));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        String host = Objects.requireNonNull(
                config.getProperty("host"),
                "You cannot get connection URL with null reference as a host!");
        String port = Objects.requireNonNull(
                config.getProperty("port"),
                "You cannot get connection URL with null reference as a port!");
        String name = Objects.requireNonNull(
                config.getProperty("name"),
                "You cannot get connection URL with null reference as a name!");
        String user = Objects.requireNonNull(
                config.getProperty("user"),
                "You cannot get connection URL with null reference as a user!");
        String password = Objects.requireNonNull(
                config.getProperty("password"),
                "You cannot get connection URL with null reference as a password!");

        return "jdbc:derby://" + host + ":" + port + "/" + name + ";user=" + user + ";password=" + password;
    }

    /**
     * Creates the polls if they previously didn't existed in the database and
     * loads them with data from .properties files in location specified by
     * {@code dir} parameter.
     * 
     * @param ds
     *            data source
     * @param dir
     *            the path to directory with .properties files containg
     *            information about polls
     * @throws RuntimeException
     *             if a database access error occurs
     */
    private static void initializeDatabase(DataSource ds, String dir) {
        Connection con = null;

        try {
            con = ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        SQLConnectionProvider.setConnection(con);

        SQLUtils.createPollsIfNotExist();
        SQLUtils.createPollOptionsIfNotExist();
        SQLUtils.loadPolls(dir);
    }

}