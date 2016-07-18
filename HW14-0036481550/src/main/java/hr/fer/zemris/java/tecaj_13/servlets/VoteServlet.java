package hr.fer.zemris.java.tecaj_13.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.Poll;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

/**
 * {@code VoteServlet} is a {@link HttpServlet} class that loads poll and
 * redirects to {@code glasaj.jsp} page.
 * <p>
 * Poll(and its options) are chosen with {@code pollId} parameter.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see HttpServlet
 */
@WebServlet(name = "glasaj", urlPatterns = "/glasaj")
public class VoteServlet extends HttpServlet {

    /** Serial version UID. */
    private static final long serialVersionUID = 7541068072182429616L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long pollID = -1;
        try {
            pollID = Long.parseLong(req.getParameter("pollID"));
        } catch (NumberFormatException e) {
            resp.sendError(400, "Invalid pollID parameter.");
            return;
        }

        DAO dao = DAOProvider.getDao();
        Poll poll = dao.getPoll(pollID);
        
        if(poll == null) {
            resp.sendError(400, "Invalid pollID parameter.");
            return;
        }
        
        List<PollOption> pollOptions = dao.getPollOptionByPollID(pollID);
        pollOptions.sort((o1, o2) -> Long.compare(o2.getVotesCount(), o1.getVotesCount()));

        req.setAttribute("poll", poll);
        req.setAttribute("pollOptions", pollOptions);

        req.getRequestDispatcher("/WEB-INF/pages/glasaj.jsp").forward(req, resp);
    }

}
