package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.sql.SQLDAO;

/**
 * {@code DAOProvider} is a singleton that knows what needs to be returned as a
 * service provider that enables you to access persistent data.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see DAO
 */
public class DAOProvider {

    /** Direct access object used to access persistent data. */
    private static DAO DIRECT_ACCESS_OBJECT = new SQLDAO();

    /**
     * Returns the instance of the direct access object used to access
     * persistent data.
     * 
     * @return the instance of the direct access object
     */
    public static DAO getDao() {
        return DIRECT_ACCESS_OBJECT;
    }

}
