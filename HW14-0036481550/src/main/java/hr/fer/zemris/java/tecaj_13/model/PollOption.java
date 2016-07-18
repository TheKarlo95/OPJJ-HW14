package hr.fer.zemris.java.tecaj_13.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * {@code PollOption} class is a {@code JavaBean} class encapsulating
 * information about a poll option.
 * <p>
 * This class contains ID, option title, option link, poll ID and votes count.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see Serializable
 */
public class PollOption implements Serializable {

    /** Serial version UID. */
    private static final long serialVersionUID = -8684896380465627789L;

    /** ID of the poll option. */
    private long id;
    /** Title of the poll option. */
    private String optionTitle;
    /** Link of the poll option. */
    private String optionLink;
    /** ID of the poll that this option is available. */
    private long pollID;
    /** Votes count of this poll option. */
    private long votesCount;

    /**
     * Constructs a empty {@code PollOption} with specified {@code id},
     * {@code optionTitle}, {@code optionLink}, {@code pollID} and
     * {@code votesCount} parameters.
     * 
     * @param id
     *            the ID of the poll option
     * @param optionTitle
     *            the title of the poll option
     * @param optionLink
     *            the link of the poll option
     * @param pollID
     *            the ID of the poll that this option is available
     * @param votesCount
     *            the votes count of this poll option
     * @throws NullPointerException
     *             if {@code optionTitle} or {@code optionLink} parameter is a
     *             {@code null} reference
     * @throws IllegalArgumentException
     *             if {@code id}, {@code pollID} or {@code votesCount} parameter
     *             is a negative number
     */
    public PollOption(long id, String optionTitle, String optionLink, long pollID, long votesCount) {
        this(optionTitle, optionLink, pollID, votesCount);
        if (id < 0)
            throw new IllegalArgumentException("ID cannot be a negative number!");

        this.id = id;
    }

    /**
     * Constructs a empty {@code PollOption} with specified {@code optionTitle},
     * {@code optionLink}, {@code pollID} and {@code votesCount} parameters.
     * 
     * @param optionTitle
     *            the title of the poll option
     * @param optionLink
     *            the link of the poll option
     * @param pollID
     *            the ID of the poll that this option is available
     * @param votesCount
     *            the votes count of this poll option
     * @throws NullPointerException
     *             if {@code optionTitle} or {@code optionLink} parameter is a
     *             {@code null} reference
     * @throws IllegalArgumentException
     *             if {@code pollID} or {@code votesCount} parameter is a
     *             negative number
     */
    public PollOption(String optionTitle, String optionLink, long pollID, long votesCount) {
        if (pollID < 0)
            throw new IllegalArgumentException("Poll ID cannot be a negative number.");
        if (votesCount < 0)
            throw new IllegalArgumentException("Votes count of poll option cannot be a negative number.");

        this.pollID = pollID;
        this.votesCount = votesCount;
        this.optionTitle = Objects.requireNonNull(optionTitle, "Option title of poll option cannot be null reference.");
        this.optionLink = Objects.requireNonNull(optionLink, "Option link of poll option cannot be null reference.");
    }

    /**
     * Constructs a new empty {@code PollOption}.
     */
    public PollOption() {
    }

    /**
     * Returns the ID of the poll option.
     * 
     * @return the ID of the poll option
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the title of the poll option.
     * 
     * @return the title of the poll option
     */
    public String getOptionTitle() {
        return optionTitle;
    }
    
    /**
     * Returns the link used to vote for this option.
     * 
     * @return the link used to vote for this option
     */
    public String getVoteLink() {
        return "/dodajGlas?pollID=" + pollID + "&id=" + id;
    }

    /**
     * Returns the link of the poll option.
     * 
     * @return the link of the poll option
     */
    public String getOptionLink() {
        return optionLink;
    }

    /**
     * Returns the ID of the poll that this option is available.
     * 
     * @return the ID of the poll that this option is available
     */
    public long getPollID() {
        return pollID;
    }

    /**
     * Returns the votes count of this poll option.
     * 
     * @return the votes count of this poll option
     */
    public long getVotesCount() {
        return votesCount;
    }

    /**
     * Sets the ID of the poll option.
     * 
     * @param id
     *            the new ID of the poll option
     */
    public void setId(long id) {
        if (id < 0)
            throw new IllegalArgumentException("ID of poll option cannot be a negative number.");

        this.id = id;
    }

    /**
     * Sets the title of the poll option.
     * 
     * @param optionTitle
     *            the new title of the poll option
     */
    public void setOptionTitle(String optionTitle) {
        this.optionTitle = Objects.requireNonNull(optionTitle, "Option title of poll option cannot be null reference.");
    }

    /**
     * Sets the link of the poll option.
     * 
     * @param optionLink
     *            the new link of the poll option
     */
    public void setOptionLink(String optionLink) {
        this.optionLink = Objects.requireNonNull(optionLink, "Option link of poll optioncannot be null reference.");
    }

    /**
     * Sets the ID of the poll that this option is available.
     * 
     * @param pollID
     *            the new ID of the poll that this option is available
     */
    public void setPollID(long pollID) {
        if (pollID < 0)
            throw new IllegalArgumentException("Poll ID cannot be a negative number.");

        this.pollID = pollID;
    }

    /**
     * Sets the votes count of this poll option.
     * 
     * @param votesCount
     *            the new votes count of this poll option
     */
    public void setVotesCount(long votesCount) {
        if (votesCount < 0)
            throw new IllegalArgumentException("Votes count of poll option cannot be a negative number.");

        this.votesCount = votesCount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((optionLink == null) ? 0 : optionLink.hashCode());
        result = prime * result + ((optionTitle == null) ? 0 : optionTitle.hashCode());
        result = prime * result + (int) (pollID ^ (pollID >>> 32));
        result = prime * result + (int) (votesCount ^ (votesCount >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PollOption other = (PollOption) obj;
        if (id != other.id)
            return false;
        if (optionLink == null) {
            if (other.optionLink != null)
                return false;
        } else if (!optionLink.equals(other.optionLink))
            return false;
        if (optionTitle == null) {
            if (other.optionTitle != null)
                return false;
        } else if (!optionTitle.equals(other.optionTitle))
            return false;
        if (pollID != other.pollID)
            return false;
        if (votesCount != other.votesCount)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "id=" + id
                + ", optionTitle=" + optionTitle
                + ", optionLink=" + optionLink
                + ", pollID=" + pollID
                + ", votesCount=" + votesCount;
    }
}