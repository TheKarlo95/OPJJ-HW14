package hr.fer.zemris.java.tecaj_13.dao.sql;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.Properties;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;

/**
 * {@code SQLUtils} is a utility class that provides static method for creating
 * tables and loading Polls.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public class SQLUtils {

    /**
     * Creates table with table name 'Polls' in database 'votingDB' if it didn't
     * existed previous to this method call
     * 
     * @return number of affected rows; -1 if some exceptions occurred
     * @throws DAOException
     *             if some exception occurs while executing create statement
     * @see #createIfNotExist(String, String)
     */
    public static long createPollsIfNotExist() {
        String sql = "CREATE TABLE Polls"
                + "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                + "title VARCHAR(150) NOT NULL,"
                + "message CLOB(2048) NOT NULL)";

        return createIfNotExist("Polls", sql);
    }

    /**
     * Creates table with table name 'PollOptions' in database 'votingDB' if it
     * didn't existed previous to this method call
     * 
     * @return number of affected rows; -1 if some exceptions occurred
     * @throws DAOException
     *             if some exception occurs while executing create statement
     * @see #createIfNotExist(String, String)
     */
    public static long createPollOptionsIfNotExist() {
        String sql = "CREATE TABLE PollOptions"
                + "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                + " optionTitle VARCHAR(100) NOT NULL,"
                + " optionLink VARCHAR(150) NOT NULL,"
                + " pollID BIGINT, votesCount BIGINT,"
                + " FOREIGN KEY (pollID) REFERENCES Polls(id))";

        return createIfNotExist("PollOptions", sql);
    }

    /**
     * Loads a poll from a path specified by {@code path} parameter and inserts
     * it to database.
     * <p>
     * File at the specified path must be a .properties file with properties
     * title, message and at least one option. One option has two properties:
     * optX_title and optX_link(where X is a ordinal number of the poll option).
     * If option is to be properly added both properties must exists for all
     * options.
     * 
     * @param path
     *            path to file that contains information about poll
     */
    public static void loadPoll(String path) {
        Properties properties = new Properties();

        try {
            properties.load(Files.newInputStream(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        DAO dao = DAOProvider.getDao();

        String title = properties.getProperty("title");
        String message = properties.getProperty("message");

        long pollID = dao.addPollIfDoesntExist(title, message);

        String optionTitle = null;
        String optionLink = null;
        int i = 1;

        do {
            optionTitle = properties.getProperty("opt" + i + "_title");
            optionLink = properties.getProperty("opt" + i + "_link");

            if (optionTitle == null || optionLink == null)
                break;

            dao.addPollOptionIfDoesntExist(optionTitle, optionLink, pollID, 0L);
            i++;
        } while (true);
    }

    /**
     * Loads all .properties files as polls from the directory specified by
     * {@code dir} parameter.
     * 
     * @param dir
     *            the path to directory with .properties files containg
     *            information about polls
     * @see #loadPoll(String)
     */
    public static void loadPolls(String dir) {
        Objects.requireNonNull(dir, "You cannot load polls from a null reference as a directory.");

        try {
            Files.walk(Paths.get(dir))
                    .filter(p -> Files.isRegularFile(p))
                    .filter(p -> p.toString().endsWith(".properties"))
                    .forEach(path -> SQLUtils.loadPoll(path.toString()));
        } catch (IOException ignorable) {
        }
    }

    /**
     * Creates table with table name specified by parameter {@code table} in
     * database 'votingDB' if it didn't existed previous to this method call
     * 
     * @param table
     *            the table name
     * @param sql
     *            the SQL statement that creates this table
     * @return number of affected rows; -1 if some exceptions occurred
     * @throws DAOException
     *             if some exception occurs while executing create statement
     */
    private static long createIfNotExist(String table, String sql) {
        try {
            Connection con = SQLConnectionProvider.getConnection();
            DatabaseMetaData dbmd = con.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, table.toUpperCase(), null);

            if (!rs.next()) {
                int numberOfAffectedRows = -1;

                try (Statement sta = con.createStatement()) {
                    numberOfAffectedRows = sta.executeUpdate(sql);
                }

                return numberOfAffectedRows;
            }
        } catch (Exception e) {
            throw new DAOException("Exception while creating " + table + " table: " + e.getMessage(), e);
        }
        return -1;
    }
}
