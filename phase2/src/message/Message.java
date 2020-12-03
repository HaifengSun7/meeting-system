package message;

/**
 * Each Message entity stores the sender of the message, receiver of the message and the message text itself.
 */
public class Message {

    private final String sender;
    private final String receiver;
    private final String text;
    private boolean unReadStatus;

    /**
     * The constructor for Message entity. Stores sender, receiver, and message text as Strings.
     *
     * @param sender   The Username of the user who sends the message.
     * @param receiver The Username of the receiver who gets the message.
     * @param text     The text of the message
     */
    public Message(String sender, String receiver, String text) {
        this.sender = sender;
        this.receiver = receiver;
        if (text.isEmpty()) {
            this.text = "(empty message)";
        } else {
            this.text = text;
        }
        this.unReadStatus = true; //TODO: we need to write this into csv
    }

    /**
     * Get's the sender of the message.
     *
     * @return the sender user with the username.
     */
    public String getSender() {
        return this.sender;
    }

    /**
     * Get's the receiver of the message.
     *
     * @return the receiving user with the username.
     */
    public String getReceiver() {
        return this.receiver;
    }

    /**
     * Get's the text of the message.
     *
     * @return the message text.
     */
    public String getText() {
        return this.text;
    }
    /**
     * Get's whether the text is unread or not
     * @return unReadStatus.
     */
    public boolean getUnReadStatus() {
        return this.unReadStatus;
    }

    /**
     * @param status the unReadStatus to set
     */
    public void setUnreadStatus(boolean status) {
        this.unReadStatus = status;
    }

    /**
     * Get's everything of the message.
     *
     * @return the message with all its info in String.
     */
    @Override
    public String toString() {
        return "Message To: " + this.receiver + ". From: " + this.sender + ". Text: " + this.text;
    }

    public String getStatus() {
        //TODO:
        return "yes.";
    }
}
