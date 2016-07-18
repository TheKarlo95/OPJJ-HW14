package hr.fer.zemris.java.tecaj_13.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * {@code Poll} class is a {@code JavaBean} class encapsulating information
 * about a poll.
 * <p>
 * This class contains ID, title and message of a poll.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see Serializable
 */
public class Poll implements Serializable {

    /** Serial version UID. */
    private static final long serialVersionUID = -1317906628770849233L;

    /** ID of the poll. */
    private long id;
    /** Title of the poll. */
    private String title;
    /** Message of the poll. */
    private String message;

    /**
     * Constructs a empty {@code Poll} with specified {@code id}, {@code title}
     * and {@code message} parameters.
     * 
     * @param id
     *            the ID of the poll
     * @param title
     *            the title of the poll
     * @param message
     *            the message of the poll
     * @throws NullPointerException
     *             if {@code title} or {@code message} parameter is a
     *             {@code null} reference
     * @throws IllegalArgumentException
     *             if {@code id} parameter is a negative number
     */
    public Poll(long id, String title, String message) {
        this(title, message);
        if (id < 0)
            throw new IllegalArgumentException("ID cannot be a negative number.");

        this.id = id;
    }

    /**
     * Constructs a empty {@code Poll} with specified {@code title} and
     * {@code message} parameters.
     * 
     * @param title
     *            the title of the poll
     * @param message
     *            the message of the poll
     * @throws NullPointerException
     *             if {@code title} or {@code message} parameter is a
     *             {@code null} reference
     */
    public Poll(String title, String message) {
        this.title = Objects.requireNonNull(title, "Title of poll cannot be null reference.");
        this.message = Objects.requireNonNull(message, "Message of poll cannot be null reference.");
    }

    /**
     * Constructs a new empty {@code Poll}.
     */
    public Poll() {
    }

    /**
     * Returns the ID of the poll.
     * 
     * @return the ID of the poll
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the title of the poll.
     * 
     * @return the title of the poll
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the message of the poll.
     * 
     * @return the message of the poll
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the ID of the poll.
     * 
     * @param id
     *            the new ID of the poll
     * @throws IllegalArgumentException
     *             if {@code id} parameter is a negative number
     */
    public void setId(long id) {
        if (id < 0)
            throw new IllegalArgumentException("ID cannot be a negative number.");

        this.id = id;
    }

    /**
     * Sets the title of the poll.
     * 
     * @param title
     *            the new title of the poll
     * @throws NullPointerException
     *             if {@code title} parameter is a {@code null} reference
     */
    public void setTitle(String title) {
        this.title = Objects.requireNonNull(title, "Title of poll cannot be null reference.");
    }

    /**
     * Sets the message of the poll.
     * 
     * @param message
     *            the new message of the poll
     * @throws NullPointerException
     *             if {@code message} parameter is a {@code null} reference
     */
    public void setMessage(String message) {
        this.message = Objects.requireNonNull(message, "Message of poll cannot be null reference.");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
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
        Poll other = (Poll) obj;
        if (id != other.id)
            return false;
        if (message == null) {
            if (other.message != null)
                return false;
        } else if (!message.equals(other.message))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "id=" + id + ", title=" + title + ", message=" + message;
    }

}
