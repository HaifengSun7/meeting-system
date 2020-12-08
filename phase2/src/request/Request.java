package request;

/**
 * A request made by user and received by organizers.
 */
public class Request {

    private final String username;
    private final String content;
    private final String title;
    private boolean status;

    /**
     * Create a new request. Status is false by default.
     *
     * @param username the user name of the attendee sending the request.
     * @param title    title of the request.
     * @param content  the request content.
     */
    public Request(String username, String title, String content) {
        this.username = username;
        this.title = title;
        this.content = content;
        this.status = false;
    }

    /**
     * Get the username of the sender.
     *
     * @return the username of the sender.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Get the content of the request.
     *
     * @return the content of the request.
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Get the title of the request.
     *
     * @return the title of the request.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Get the status of the request.
     *
     * @return the status of the request.
     */
    public boolean getStatus() {
        return this.status;
    }

    /**
     * Set the status of the request.
     *
     * @param status status of the request.
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

}
