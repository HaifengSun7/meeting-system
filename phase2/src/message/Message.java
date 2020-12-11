package message;

import java.util.ArrayList;

/**
 * Each Message entity stores the sender of the message, receiver of the message and the message text itself.
 */
public class Message {

    private static int number = 0;
    private final String sender;
    private final String receiver;
    private final String text;
    private final int ID;
    private boolean unReadStatus = true;
    private boolean ReceiverDeleteStatus = false;
    private boolean ReceiverArchiveStatus = false;
    private boolean SenderDeleteStatus = false;
    private boolean SenderArchiveStatus = false;

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
        this.ID = number;
        number += 1;
    }

    public static void resetID() {
        number = 0;
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
     * Gets the Id of message
     *
     * @return the message id.
     */
    public int getID() {
        return this.ID;
    }

    /**
     * Get's whether the text is unread or not
     *
     * @return unReadStatus.
     */
    public boolean getUnReadStatus() {
        return this.unReadStatus;
    }

    /**
     * Get's whether the receiver has deleted the message.
     *
     * @return ReceiverDeleteStatus.
     */
    public boolean getReceiverDeleteStatus() {
        return this.ReceiverDeleteStatus;
    }

    /**
     * @param status the ReceiverDeleteStatus to set
     */
    public void setReceiverDeleteStatus(boolean status) {
        this.ReceiverDeleteStatus = status;
    }

    /**
     * Get's whether the text is archived by receiver.
     *
     * @return ReceiverArchiveStatus.
     */
    public boolean getReceiverArchiveStatus() {
        return this.ReceiverArchiveStatus;
    }

    /**
     * @param status the ReceiverArchive to set
     */
    public void setReceiverArchiveStatus(boolean status) {
        this.ReceiverArchiveStatus = status;
    }

    /**
     * Get's whether the receiver has deleted the message.
     *
     * @return ReceiverDeleteStatus.
     */
    public boolean getSenderDeleteStatus() {
        return this.SenderDeleteStatus;
    }

    /**
     * @param status the SenderDelete to set
     */
    public void setSenderDeleteStatus(boolean status) {
        this.SenderDeleteStatus = status;
    }

    /**
     * Get's whether the text is archived by receiver.
     *
     * @return ReceiverArchiveStatus.
     */
    public boolean getSenderArchiveStatus() {
        return this.SenderArchiveStatus;
    }

    /**
     * @param status the SenderArchiveStatus to set
     */
    public void setSenderArchiveStatus(boolean status) {
        this.SenderArchiveStatus = status;
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

    /**
     * Return all status of the message.
     *
     * @return An arraylist containing unread, sender and receiver delete/archive status.
     */
    public ArrayList<String> getAllStatus() {
        ArrayList<String> allStatus = new ArrayList<>();
        allStatus.add(String.valueOf(getUnReadStatus()));
        allStatus.add(String.valueOf(getReceiverDeleteStatus()));
        allStatus.add(String.valueOf(getReceiverArchiveStatus()));
        allStatus.add(String.valueOf(getSenderDeleteStatus()));
        allStatus.add(String.valueOf(getSenderArchiveStatus()));
        return allStatus;
    }
}
