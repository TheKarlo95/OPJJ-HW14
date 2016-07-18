package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.tecaj_13.model.Poll;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

/**
 * {@code DAO}(Direct Access Object) is an interface of a persistent data
 * subsystem.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public interface DAO {

    /**
     * Returns the list of all polls.
     * 
     * @return the list of all polls
     * @throws DAOException
     *             if some exception occurs
     */
    public List<Poll> getAllPolls() throws DAOException;

    /**
     * Returns the poll with the ID specified by parameter {@code id} or
     * {@code null} if that entry doesn't exist.
     * 
     * @param id
     *            the ID of the poll
     * @return the poll with the specified ID
     * @throws DAOException
     *             if some exception occurs
     */
    public Poll getPoll(long id) throws DAOException;

    /**
     * Returns the poll with the title specified by parameter {@code title} or
     * {@code null} if that entry doesn't exist.
     * 
     * @param title
     *            the title of the poll
     * @return the poll with the specified title
     * @throws DAOException
     *             if some exception occurs
     */
    public Poll getPoll(String title) throws DAOException;

    /**
     * Adds a poll to table 'Polls' with specified {@code title} and
     * {@code message} parameters.
     * 
     * @param title
     *            the title of the poll
     * @param message
     *            the message of the poll
     * @return the id of a new row
     * @throws DAOException
     *             if some exception occurs
     * @throws NullPointerException
     *             if {@code title} or {@code message} parameter is a
     *             {@code null} reference
     */
    public long addPoll(String title, String message) throws DAOException;

    /**
     * Adds a poll to table 'Polls'.
     * 
     * @param poll
     *            the poll to be added to table 'Polls'
     * @return the ID of a poll
     * @throws DAOException
     *             if some exception occurs
     * @throws NullPointerException
     *             if {@code poll} parameter is a {@code null} reference
     */
    public default long addPoll(Poll poll) throws DAOException {
        Objects.requireNonNull(poll, "You cannot add a null reference as poll.");
        return addPoll(poll.getTitle(), poll.getMessage());
    }

    /**
     * Adds a poll to table 'Polls' with specified {@code title} and
     * {@code message} parameters if row with same {@code title} doesn't exist.
     * 
     * @param title
     *            the title of the poll
     * @param message
     *            the message of the poll
     * @return the id of a new row or {@code -1} if row with same {@code title}
     *         already exists
     * @throws DAOException
     *             if some exception occurs
     * @throws NullPointerException
     *             if {@code title} or {@code message} parameter is a
     *             {@code null} reference
     */
    public long addPollIfDoesntExist(String title, String message) throws DAOException;

    /**
     * Returns the list of all poll options.
     * 
     * @return the list of all poll options
     * @throws DAOException
     *             if some exception occurs
     */
    public List<PollOption> getAllPollOptions() throws DAOException;

    /**
     * Returns the poll option with the ID specified by parameter {@code id} or
     * {@code null} if that entry doesn't exist.
     * 
     * @param id
     *            the ID of the poll option
     * @return the poll option with the specified ID
     * @throws DAOException
     *             if some exception occurs
     */
    public PollOption getPollOption(long id) throws DAOException;

    /**
     * Returns the poll option with the title specified by parameter
     * {@code optionTitle} or {@code null} if that entry doesn't exist.
     * 
     * @param optionTitle
     *            the title of the poll option
     * @return the poll option with the specified ID
     * @throws DAOException
     *             if some exception occurs
     */
    public PollOption getPollOption(String optionTitle) throws DAOException;

    /**
     * Returns the list of all poll options with the poll ID specified by
     * parameter {@code pollID} or {@code null} if that entry doesn't exist.
     * 
     * @param pollID
     *            the ID of the poll
     * @return the poll option with the specified ID
     * @throws DAOException
     *             if some exception occurs
     */
    public List<PollOption> getPollOptionByPollID(long pollID) throws DAOException;

    /**
     * Adds a poll option to table 'PollOptions' with specified {@code id},
     * {@code optionTitle}, {@code optionLink}, {@code pollID} and
     * {@code votesCount}.
     * 
     * @param optionTitle
     *            the title of the poll option
     * @param optionLink
     *            the link of the poll option
     * @param pollID
     *            the ID of the poll that this option is available
     * @param votesCount
     *            the votes count of this poll option
     * @return the ID of a poll option
     * @throws DAOException
     *             if some exception occurs
     * @throws NullPointerException
     *             if {@code optionTitle} or {@code optionLink} parameter is a
     *             {@code null} reference
     * @throws IllegalArgumentException
     *             if {@code id}, {@code pollID} or {@code votesCount} parameter
     *             is a negative number
     */
    public long addPollOption(String optionTitle, String optionLink, long pollID, long votesCount) throws DAOException;

    /**
     * Adds a poll option to table 'PollOptions'.
     * 
     * @param pollOption
     *            the poll option
     * @return the ID of a poll option
     * @throws DAOException
     *             if some exception occurs
     * @throws NullPointerException
     *             if {@code poll} parameter is a {@code null} reference
     */
    public default long addPollOption(PollOption pollOption) throws DAOException {
        Objects.requireNonNull(pollOption, "You cannot add a null reference as poll option.");
        return addPollOption(
                pollOption.getOptionTitle(),
                pollOption.getOptionLink(),
                pollOption.getPollID(),
                pollOption.getVotesCount());
    }

    /**
     * Adds a poll option to table 'PollOptions' with specified {@code id},
     * {@code optionTitle}, {@code optionLink}, {@code pollID} and
     * {@code votesCount} if row with same {@code optionTitle} doesn't exist.
     * 
     * @param optionTitle
     *            the title of the poll option
     * @param optionLink
     *            the link of the poll option
     * @param pollID
     *            the ID of the poll that this option is available
     * @param votesCount
     *            the votes count of this poll option
     * @return the ID of a poll option or {@code -1} if row with same
     *         {@code optionTitle} already exists
     * @throws DAOException
     *             if some exception occurs
     * @throws NullPointerException
     *             if {@code optionTitle} or {@code optionLink} parameter is a
     *             {@code null} reference
     * @throws IllegalArgumentException
     *             if {@code id}, {@code pollID} or {@code votesCount} parameter
     *             is a negative number
     */
    public long addPollOptionIfDoesntExist(String optionTitle, String optionLink, long pollID, long votesCount)
            throws DAOException;

    /**
     * Updates the poll option with specified {@code id} votes count to votes
     * count plus {@code votes}.
     * 
     * @param id
     *            the ID of poll option to be updated
     * @param votes
     *            votes to be added
     * @return the number of affected rows; if everything went well 1 should be
     *         returned
     * @throws DAOException
     *             if some exception occurs
     * @throws IllegalArgumentException
     *             if {@code id} or {@code votes} parameter is a negative number
     */
    public int updatePollOptionsVotesCount(long id, long votes) throws DAOException;

    /**
     * Updates the poll option with specified {@code id} votes count to votes
     * count plus {@code 1}.
     * 
     * @param id
     *            the ID of poll option to be updated
     * @return the number of affected rows; if everything went well 1 should be
     *         returned
     * @throws DAOException
     *             if some exception occurs
     * @throws IllegalArgumentException
     *             if {@code id} parameter is a negative number
     */
    public default int updatePollOptionsVotesCount(long id) throws DAOException {
        return updatePollOptionsVotesCount(id, 1);
    }
}