package hr.fer.zemris.java.tecaj_13.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

/**
 * {@code GlasanjeXlsServlet} is a {@link HttpServlet} class that generates Excel
 * spreadsheet with list of all poll options and their vote count sorted by their vote
 * count.
 * <p>
 * Poll is selected with 'pollID' parameter.(e.g. "/glasanje-xls?pollID=2")
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see HttpServlet
 */
@WebServlet(name = "glasanje-xls", urlPatterns = { "/glasanje-xls" })
public class XLSServlet extends HttpServlet {


    /** Serial version UID. */
    private static final long serialVersionUID = 8464127761523933698L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HSSFWorkbook hwb = new HSSFWorkbook();

        long pollID = -1;
        try {
            pollID = Long.parseLong(req.getParameter("pollID"));
        } catch (NumberFormatException e) {
            resp.sendError(400, "Invalid pollID parameter.");
            hwb.close();
            return;
        }
        
        List<PollOption> pollOptions = DAOProvider.getDao().getPollOptionByPollID(pollID);
        
        createSheet(hwb, pollOptions);
        
        
        resp.setContentType("application/xls");
        resp.setHeader("Content-Disposition", "attachment; filename=results_poll" + pollID + ".xls");

        hwb.write(resp.getOutputStream());
    }

    /**
     * Creates a new sheet in provided {@code hwb} work book and writes
     * appropriate data to it.
     * 
     * @param pollOptions
     *            list of poll options
     * @param hwb
     *            excel spreadsheet
     */
    private static void createSheet(HSSFWorkbook hwb, List<PollOption> pollOptions) {
        pollOptions.sort((o1, o2) -> Long.compare(o2.getVotesCount(), o1.getVotesCount()));

        HSSFSheet sheet = hwb.createSheet("Results");

        for (int i = 0, length = pollOptions.size(); i < length; i++) {
            HSSFRow row = sheet.createRow(i);
            row.createCell(0).setCellValue(pollOptions.get(i).getOptionTitle());
            row.createCell(1).setCellValue(pollOptions.get(i).getVotesCount());
        }
    }

}
