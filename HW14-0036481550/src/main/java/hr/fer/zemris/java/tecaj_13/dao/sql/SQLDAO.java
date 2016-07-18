package hr.fer.zemris.java.tecaj_13.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.Poll;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

/**
 * {@code SQLDAO} is a class that offers a some methods to work with a SQL
 * database.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see DAO
 */
public class SQLDAO implements DAO {

    /**
     * SQL select statement that selects all columns from table 'Polls' and
     * orders rows by 'id'.
     */
    private static final String SQL_POLLS_SELECT_ALL = "SELECT id, title, message"
            + " FROM Polls"
            + " ORDER BY id";
    /**
     * SQL select statement that selects column from table 'Polls' with
     * specified 'id'.
     */
    private static final String SQL_POLLS_SELECT_BY_ID = "SELECT id, title, message"
            + " FROM Polls"
            + " WHERE id=?";
    /**
     * SQL select statement that selects column from table 'Polls' with
     * specified 'title'.
     */
    private static final String SQL_POLLS_SELECT_BY_TITLE = "SELECT id, title, message"
            + " FROM Polls"
            + " WHERE title=?";
    /**
     * SQL insert statement that inserts row with specified 'title' and
     * 'message' columns in table 'Polls'.
     */
    private static final String SQL_POLLS_INSERT = "INSERT INTO Polls"
            + " (title, message) values (?,?)";

    /**
     * SQL select statement that selects all columns from table 'PollOptions'
     * and orders rows by 'id'.
     */
    private static final String SQL_POLLOPTIONS_SELECT_ALL = "SELECT id, optionTitle, optionLink, pollID, votesCount"
            + " FROM PollOptions"
            + " ORDER BY id";
    /**
     * SQL select statement that selects column from table 'PollOptions' with
     * specified 'id'.
     */
    private static final String SQL_POLLOPTIONS_SELECT_BY_ID = "SELECT id, optionTitle, optionLink, pollID, votesCount"
            + " FROM PollOptions"
            + " WHERE id=?";
    /**
     * SQL select statement that selects column from table 'PollOptions' with
     * specified 'optionTitle'.
     */
    private static final String SQL_POLLOPTIONS_SELECT_BY_OPTIONTITLE = "SELECT id, optionTitle, optionLink, pollID,"
            + " votesCount"
            + " FROM PollOptions"
            + " WHERE optionTitle=?";
    /**
     * SQL select statement that selects column from table 'PollOptions' with
     * specified 'optionTitle'.
     */
    private static final String SQL_POLLOPTIONS_SELECT_BY_POLLID = "SELECT id, optionTitle, optionLink, pollID,"
            + " votesCount"
            + " FROM PollOptions"
            + " WHERE pollID=?";
    /**
     * SQL insert statement that inserts row with specified 'optionTitle',
     * 'optionLink', 'pollID' and 'votesCount' columns in table 'PollOptions'.
     */
    private static final String SQL_POLLOPTIONS_INSERT = "INSERT INTO PollOptions"
            + " (optionTitle, optionLink, pollID, votesCount) values (?,?,?,?)";
    /**
     * SQL update statement that updates row with specified 'id' with new
     * 'votesCount' value in table 'PollOptions'.
     */
    private static final String SQL_POLLOPTIONS_UPDATE_VOTESCOUNT = "UPDATE pollOptions"
            + " SET votesCount=votesCount+?"
            + " WHERE id=?";

    @Override
    public List<Poll> getAllPolls() throws DAOException {
        List<Poll> polls = new ArrayList<>();
        Connection con = SQLConnectionProvider.getConnection();

        try (PreparedStatement pst = con.prepareStatement(SQL_POLLS_SELECT_ALL)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs != null && rs.next()) {
                    polls.add(new Poll(rs.getLong(1), rs.getString(2), rs.getString(3)));
                }
            }

            
        } catch (Exception e) {
            throw new DAOException("Exception occured while getting list of all pols.", e);
        }
        
        return polls;
    }

    @Override
    public Poll getPoll(long id) throws DAOException {
        if (id < 0)
            return null;

        Poll poll = null;
        Connection con = SQLConnectionProvider.getConnection();

        try (PreparedStatement pst = con.prepareStatement(SQL_POLLS_SELECT_BY_ID)) {
            pst.setLong(1, Long.valueOf(id));

            try (ResultSet rs = pst.executeQuery()) {
                if (rs != null && rs.next()) {
                    poll = new Poll(rs.getLong(1), rs.getString(2), rs.getString(3));
                }
            }
        } catch (Exception e) {
            throw new DAOException("Exception occured while getting a poll with id=" + id + ".", e);
        }

        return poll;
    }

    public Poll getPoll(String title) throws DAOException {
        if (title == null)
            return null;

        Poll poll = null;
        Connection con = SQLConnectionProvider.getConnection();

        try (PreparedStatement pst = con.prepareStatement(SQL_POLLS_SELECT_BY_TITLE)) {
            pst.setString(1, title);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs != null && rs.next()) {
                    poll = new Poll(rs.getLong(1), rs.getString(2), rs.getString(3));
                }
            }
        } catch (Exception e) {
            throw new DAOException("Exception occured while getting a poll with title=" + title + ".", e);
        }

        return poll;
    }

    @Override
    public long addPoll(String title, String message) throws DAOException {
        Objects.requireNonNull(title, "You cannot add a poll with a null reference as a title.");
        Objects.requireNonNull(message, "You cannot add a poll with a null reference as a message.");

        Connection con = SQLConnectionProvider.getConnection();
        long id = -1;

        try (PreparedStatement pst = con.prepareStatement(SQL_POLLS_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, title);
            pst.setString(2, message);

            pst.executeUpdate();

            try (ResultSet rset = pst.getGeneratedKeys()) {
                if (rset != null && rset.next()) {
                    id = rset.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Exception occured while inserting a new poll.", e);
        }

        return id;
    }

    @Override
    public long addPollIfDoesntExist(String title, String message) throws DAOException {
        Objects.requireNonNull(title, "You cannot add a poll with a null reference as a title.");
        Objects.requireNonNull(message, "You cannot add a poll with a null reference as a message.");

        Poll poll = getPoll(title);

        if (poll == null) {
            return addPoll(title, message);
        }

        return poll.getId();
    }

    @Override
    public List<PollOption> getAllPollOptions() throws DAOException {
        List<PollOption> pollOptions = new ArrayList<>();
        Connection con = SQLConnectionProvider.getConnection();

        try (PreparedStatement pst = con.prepareStatement(SQL_POLLOPTIONS_SELECT_ALL)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs != null && rs.next()) {
                    pollOptions.add(new PollOption(
                            rs.getLong(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getLong(4),
                            rs.getLong(5)));
                }
            }
        } catch (Exception e) {
            throw new DAOException("Exception occured while getting list of all pols.", e);
        }

        return pollOptions;
    }

    @Override
    public PollOption getPollOption(long id) throws DAOException {
        if (id < 0)
            return null;
        
        PollOption pollOption = null;
        Connection con = SQLConnectionProvider.getConnection();

        try (PreparedStatement pst = con.prepareStatement(SQL_POLLOPTIONS_SELECT_BY_ID)) {
            pst.setLong(1, Long.valueOf(id));

            try (ResultSet rs = pst.executeQuery()) {
                if (rs != null && rs.next()) {
                    pollOption = new PollOption(
                            rs.getLong(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getLong(4),
                            rs.getLong(5));
                }
            }
        } catch (Exception e) {
            throw new DAOException("Exception occured while getting a poll with id=" + id + ".", e);
        }

        return pollOption;
    }

    @Override
    public PollOption getPollOption(String optionTitle) throws DAOException {
        if(optionTitle == null)
            return null;
        
        PollOption pollOption = null;
        Connection con = SQLConnectionProvider.getConnection();

        try (PreparedStatement pst = con.prepareStatement(SQL_POLLOPTIONS_SELECT_BY_OPTIONTITLE)) {
            pst.setString(1, optionTitle);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs != null && rs.next()) {
                    pollOption = new PollOption(
                            rs.getLong(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getLong(4),
                            rs.getLong(5));
                }
            }
        } catch (Exception e) {
            throw new DAOException("Exception occured while getting a poll with optionTitle=" + optionTitle + ".", e);
        }

        return pollOption;
    }

    @Override
    public List<PollOption> getPollOptionByPollID(long pollID) throws DAOException {
        if(pollID < 0)
            return null;
        
        List<PollOption> pollOptions = null;
        Connection con = SQLConnectionProvider.getConnection();

        try (PreparedStatement pst = con.prepareStatement(SQL_POLLOPTIONS_SELECT_BY_POLLID)) {
            pst.setLong(1, pollID);

            try (ResultSet rs = pst.executeQuery()) {

                if (rs != null) {
                    pollOptions = new ArrayList<>();

                    while (rs.next()) {
                        pollOptions.add(new PollOption(
                                rs.getLong(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getLong(4),
                                rs.getLong(5)));
                    }
                }
            }
        } catch (Exception e) {
            throw new DAOException("Exception occured while getting a poll with pollID=" + pollID + ".", e);
        }

        return pollOptions;
    }

    @Override
    public long addPollOption(String optionTitle, String optionLink, long pollID, long votesCount) throws DAOException {
        if (pollID < 0)
            throw new IllegalArgumentException("You cannot add a poll option with negative poll ID.");
        if (votesCount < 0)
            throw new IllegalArgumentException("You cannot add a poll option with negative votes count.");
        Objects.requireNonNull(optionTitle, "You cannot add a poll option with a null reference as a option title.");
        Objects.requireNonNull(optionLink, "You cannot add a poll option with a null reference as a option link.");

        Connection con = SQLConnectionProvider.getConnection();
        long id = -1;

        try (PreparedStatement pst = con.prepareStatement(SQL_POLLOPTIONS_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, optionTitle);
            pst.setString(2, optionLink);
            pst.setLong(3, pollID);
            pst.setLong(4, votesCount);

            pst.executeUpdate();

            try (ResultSet rset = pst.getGeneratedKeys()) {
                if (rset != null && rset.next()) {
                    id = rset.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Exception occured while inserting a new poll option.", e);
        }

        return id;
    }

    @Override
    public long addPollOptionIfDoesntExist(String optionTitle, String optionLink, long pollID, long votesCount)
            throws DAOException {
        if (pollID < 0)
            throw new IllegalArgumentException("You cannot add a poll option with negative poll ID.");
        if (votesCount < 0)
            throw new IllegalArgumentException("You cannot add a poll option with negative votes count.");
        Objects.requireNonNull(optionTitle, "You cannot add a poll option with a null reference as a option title.");
        Objects.requireNonNull(optionLink, "You cannot add a poll option with a null reference as a option link.");

        PollOption pollOption = getPollOption(optionTitle);

        if (pollOption == null) {
            return addPollOption(optionTitle, optionLink, pollID, votesCount);
        }

        return pollOption.getId();
    }

    @Override
    public int updatePollOptionsVotesCount(long id, long votes) throws DAOException {
        if (votes < 0)
            throw new IllegalArgumentException("You cannot update a poll option with negative votes count.");

        Connection con = SQLConnectionProvider.getConnection();
        int numberOfAffectedRows = -1;

        try (PreparedStatement pst = con.prepareStatement(SQL_POLLOPTIONS_UPDATE_VOTESCOUNT)) {
            pst.setLong(1, votes);
            pst.setLong(2, Long.valueOf(id));

            numberOfAffectedRows = pst.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Exception occured while updating a poll option.", e);
        }

        return numberOfAffectedRows;
    }

}