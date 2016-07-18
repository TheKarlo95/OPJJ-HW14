package hr.fer.zemris.java.tecaj_13.servlets;

import java.io.IOException;
import java.util.ArrayList;
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
 * {@code ResultsServlet} is a {@link HttpServlet} class that loads poll results
 * in table, pie chart and .xls file.
 * <p>
 * In order to use this servlet you need to provided parameter {@code pollId}
 * which indicates the id of the poll you want to display results of.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see HttpServlet
 */
@WebServlet(name = "glasanje-rezultati", urlPatterns = "/glasanje-rezultati")
public class ResultsServlet extends HttpServlet {

    /** Serial version UID. */
    private static final long serialVersionUID = -3403909592511794624L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DAO dao = DAOProvider.getDao();
        long pollID = -1;
        try {
            pollID = Long.parseLong(req.getParameter("pollID"));
        } catch (NumberFormatException e) {
            resp.sendError(400, "Invalid pollID parameter.");
            return;
        }
        Poll poll = dao.getPoll(pollID);
        List<PollOption> pollOptions = dao.getPollOptionByPollID(pollID);

        if (pollOptions == null) {
            resp.sendError(400, "Invalid pollID parameter.");
            return;
        }

        req.setAttribute("poll", poll);
        req.setAttribute("pollOptions", pollOptions);
        req.setAttribute("winners", getWinners(pollOptions));

        req.getRequestDispatcher("/WEB-INF/pages/glasanje-rezultati.jsp").forward(req, resp);
    }

    /**
     * Returns the winner of the votes.
     * <p>
     * <b>Note:</b> more than one winner can exist if they have the same number
     * of votes and therefore the list is returned
     * 
     * @param pollOptions
     *            list of all poll options
     * @return list of winners
     */
    private static List<PollOption> getWinners(List<PollOption> pollOptions) {
        pollOptions.sort((o1, o2) -> Long.compare(o2.getVotesCount(), o1.getVotesCount()));

        List<PollOption> winners = new ArrayList<>();

        long maxVotes = pollOptions.get(0).getVotesCount();

        for (int i = 0, length = pollOptions.size(); i < length; i++) {
            if (maxVotes != pollOptions.get(i).getVotesCount())
                break;

            winners.add(pollOptions.get(i));
        }

        return winners;
    }
}
