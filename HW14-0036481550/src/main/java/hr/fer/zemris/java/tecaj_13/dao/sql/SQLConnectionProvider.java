package hr.fer.zemris.java.tecaj_13.dao.sql;

import java.sql.Connection;

/**
 * {@code SQLConnectionProvider} provides thread-local connections to database.
 * These connections differ from their normal counterparts in that each thread
 * that accesses one (via its {@code get} or {@code set} method) has its own,
 * independently initialized copy of the connection.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public class SQLConnectionProvider {

    /** Thread-local connections. */
    private static ThreadLocal<Connection> CONNECTIONS = new ThreadLocal<>();

    /**
     * Sets the current thread's copy of this thread-local variable to the
     * specified connection(or removes the current thread's value for this
     * thread-local variable if parameter {@code con} is a {@code null}
     * reference).
     * 
     * @param con
     *            the connection to database
     */
    public static void setConnection(Connection con) {
        if (con == null) {
            CONNECTIONS.remove();
        } else {
            CONNECTIONS.set(con);
        }
    }

    /**
     * Returns the connection to database in the current thread's copy of this
     * thread-local connection.
     * 
     * @return the current thread's connection to database
     */
    public static Connection getConnection() {
        return CONNECTIONS.get();
    }
}
