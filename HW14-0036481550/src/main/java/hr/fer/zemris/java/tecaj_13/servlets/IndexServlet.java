package hr.fer.zemris.java.tecaj_13.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.Poll;

/**
 * {@code IndexServlet} is a {@link HttpServlet} class that initializes database
 * with polls if they weren't previously created and offers the user polls n
 * which user can participate.
 * <p>
 * When user makes his/hers choice he/she will be redirected {@link VoteServlet}
 * .
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see HttpServlet
 */
@WebServlet(name = "index", urlPatterns = { "", "/index.html" })
public class IndexServlet extends HttpServlet {

    /** Serial version UID. */
    private static final long serialVersionUID = 287115456353595476L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Poll> polls = DAOProvider.getDao().getAllPolls();

        req.setAttribute("polls", polls);
        req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
    }

}
