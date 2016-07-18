package hr.fer.zemris.java.tecaj_13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;

/**
 * {@code AddVoteServlet} is a {@link HttpServlet} class that handles
 * user votes and redirects to {@link ResultsServlet}.
 * <p>
 * In order to use this servlet you need to provided parameter {@code pollId}
 * which indicates the id of the poll user participated in and parameter
 * {@code id} which indicates the id of the poll option user voted for.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see HttpServlet
 */
@WebServlet(name = "dodajGlas", urlPatterns = "/dodajGlas")
public class AddVoteServlet extends HttpServlet {

    /** Serial version UID. */
    private static final long serialVersionUID = -518033556118862648L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long pollID = -1;
        try {
            pollID = Long.parseLong(req.getParameter("pollID"));
        } catch (NumberFormatException e) {
            resp.sendError(400, "Invalid pollID parameter.");
            return;
        }
        long id = -1;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            resp.sendError(400, "Invalid id parameter.");
            return;
        }

        int num = DAOProvider.getDao().updatePollOptionsVotesCount(id);
        
        if(num != 1) {
            resp.sendError(400, "Invalid id parameter.");
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati?pollID=" + pollID);
    }

}
