package hr.fer.zemris.java.tecaj_13;

import hr.fer.zemris.java.tecaj_13.dao.sql.SQLConnectionProvider;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.sql.DataSource;

/**
 * {@code ConnectionSetterFilter} is filter that sets the connection for every
 * servlet.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see Filter
 */
@WebFilter(filterName = "f1", urlPatterns = { "/*" })
public class ConnectionSetterFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        DataSource ds = (DataSource) filterConfig.getServletContext().getAttribute("hr.fer.zemris.dbpool");
        Connection con = null;

        try {
            con = ds.getConnection();
        } catch (SQLException e) {
            throw new ServletException("Database is currently unavailable.", e);
        }

        SQLConnectionProvider.setConnection(con);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        DataSource ds = (DataSource) request.getServletContext().getAttribute("hr.fer.zemris.dbpool");
        Connection con = null;

        try {
            con = ds.getConnection();
        } catch (SQLException e) {
            throw new IOException("Database is currently unavailable.", e);
        }

        SQLConnectionProvider.setConnection(con);

        try {
            chain.doFilter(request, response);
        } finally {
            SQLConnectionProvider.setConnection(null);
            try {
                con.close();
            } catch (SQLException ignorable) {
            }
        }
    }

}