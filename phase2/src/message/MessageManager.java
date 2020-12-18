package message;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The message manager that organizes sending, receiving of messages.
 */
public class MessageManager {

    private final HashMap<Integer, Message> map = new HashMap<>();

    /**
     * Runs when message manager is created. Resets message ID.
     */
    public MessageManager() {
        Message.resetID();
    }

    /**
     * Send a Message to receiver by sender. With the message text.
     *
     * @param sender   The Username of the sender.
     * @param receiver The Username of the receiver.
     * @param text     The message.
     */
    public void sendMessage(String sender, String receiver, String text) {
        Message message = new Message(sender, receiver, text);
        map.put(message.getID(), message);
    }

    /**
     * Get the messages that the user has sent.
     *
     * @param username the userName of the user.
     * @return The list of sent messages, in form of a list of Strings, each element is the string form of the Message.
     */
    public ArrayList<String> getSent(String username) {
        ArrayList<String> rtn_list = new ArrayList<>();
        for (Message msg : this.map.values()) {
            if (msg.getSender().equals(username)) {
                rtn_list.add(msg.toString());
            }
        }
        return rtn_list;
    }

    /**
     * Get the inbox messages of the user.
     *
     * @param username The username of the user that we are looking for.
     * @return The list of inbox messages, the list would include unread messages, in form of a list of Strings, each element is the string form of the Message.
     */
    public ArrayList<String> getInbox(String username) {
        ArrayList<String> rtn_list = new ArrayList<>();
        for (Message msg : this.map.values()) {
            if (msg.getReceiver().equals(username) && !msg.getReceiverDeleteStatus()) {
                rtn_list.add(msg.toString());
            }
        }
        return rtn_list;
    }

    /**
     * Get the inbox messages of the user.
     *
     * @param username The username of the user that we are looking for.
     * @return The list of unread messages, in form of a list of Strings, each element is the string form of the Message.
     */
    public ArrayList<String> getUnread(String username) {
        ArrayList<String> rtn_list = new ArrayList<>();
        for (Message msg : this.map.values()) {
            if (msg.getReceiver().equals(username) && msg.getUnReadStatus() && !msg.getReceiverDeleteStatus()) {
                rtn_list.add(msg.toString());
            }
        }
        return rtn_list;
    }

    /**
     * Read message.
     *
     * @param username the username we need to change its message status
     * @param k        the index of the message we need to change its status
     */
    public void markKthAsRead(String username, Integer k) {
        this.getUnreadMessages(username).get(k).setUnreadStatus(false);
    }

    /**
     * Read messages.
     *
     * @param username the username we need to change its message status
     * @throws NoSuchMessageException when the message does not exist.
     */
    public void markAllAsRead(String username) throws NoSuchMessageException {
        try {
            for (Message msg : map.values()) {
                if (msg.getReceiver().equals(username) && msg.getUnReadStatus()) {
                    msg.setUnreadStatus(false);
                }
            }
        } catch (Exception e) {
            throw new NoSuchMessageException("Failed to mark all as read.");
        }
    }

    /**
     * Get all messages to string sent or received by the user.
     *
     * @param username The username of the user that we are looking for.
     * @return The list of deleted messages, in form of a list of Strings, each element is the string form of the Message.
     */
    public ArrayList<String> getAll(String username) {
        ArrayList<String> rtn_list = new ArrayList<>();
        for (Message msg : this.map.values()) {
            if (msg.getReceiver().equals(username)) {
                if (!msg.getReceiverDeleteStatus()) {
                    rtn_list.add(msg.toString());
                }
            } else if (msg.getSender().equals(username)) {
                if (!msg.getSenderDeleteStatus()) {
                    rtn_list.add(msg.toString());
                }
            }
        }
        return rtn_list;
    }

    /**
     * Delete message.
     *
     * @param username the username we need to change its message status
     * @param k        the index of the message we need to change its status
     */
    public void deleteKth(String username, Integer k) {
        if (this.getAllMessages(username).get(k).getReceiver().equals(username)) {
            this.getAllMessages(username).get(k).setReceiverDeleteStatus(true);
        } else if (this.getAllMessages(username).get(k).getSender().equals(username)) {
            this.getAllMessages(username).get(k).setSenderDeleteStatus(true);
        }
    }


    /**
     * Get the all messages to string archived by the user.
     *
     * @param username The username of the user that we are looking for.
     * @return The list of archived messages, in form of a list of Strings, each element is the string form of the Message.
     */
    public ArrayList<String> getArchived(String username) {
        ArrayList<String> rtn_list = new ArrayList<>();
        for (Message msg : this.map.values()) {
            if (msg.getReceiver().equals(username)) {
                if (!msg.getReceiverDeleteStatus() && msg.getReceiverArchiveStatus()) {
                    rtn_list.add(msg.toString());
                }
            } else if (msg.getSender().equals(username)) {
                if (!msg.getSenderDeleteStatus() && msg.getSenderArchiveStatus()) {
                    rtn_list.add(msg.toString());
                }
            }
        }
        return rtn_list;
    }

    /**
     * Archive message
     *
     * @param username the username we need to change its message status
     * @param k        the index of the message we need to change its status
     */
    public void archiveKth(String username, Integer k) {
        if (this.getAllMessages(username).get(k).getReceiver().equals(username)) {
            this.getAllMessages(username).get(k).setReceiverArchiveStatus(true);
        } else if (this.getAllMessages(username).get(k).getSender().equals(username)) {
            this.getAllMessages(username).get(k).setSenderArchiveStatus(true);
        }
    }

    /**
     * Unarchive message
     *
     * @param username the username we need to change its message status
     * @param k        the index of the message we need to change its status
     */
    public void unArchiveKth(String username, Integer k) {
        if (this.getArchivedMessages(username).get(k).getReceiver().equals(username)) {
            this.getArchivedMessages(username).get(k).setReceiverArchiveStatus(false);
        } else if (this.getArchivedMessages(username).get(k).getSender().equals(username)) {
            this.getArchivedMessages(username).get(k).setSenderArchiveStatus(false);
        }
    }

    /**
     * Get the inbox messages' senders of the user.
     *
     * @param username The username of the user that we are looking for.
     * @return The list of inbox messages, in form of a list of Strings, each element is the string form of the
     * messages' senders.
     */
    public ArrayList<String> getInboxSender(String username) {
        ArrayList<String> rtn_list = new ArrayList<>();
        for (Message msg : this.map.values()) {
            if (msg.getReceiver().equals(username)) {
                rtn_list.add(msg.getSender());
            }
        }
        return rtn_list;
    }

    /**
     * Send a Message to a list of receivers by sender. With the same message text.
     *
     * @param u         The Username of the sender.
     * @param receivers The list of Usernames of the receivers.
     * @param text      The message.
     */
    public void sendToList(String u, ArrayList<String> receivers, String text) {
        for (String user : receivers) {
            if (!user.equals(u)) {
                sendMessage(u, user, text);
            }
        }
    }

    /**
     * The method used to save all sent Messages in the system. Stores each message in the form of
     * [sender's username, receiver's username, status, message] and put them into a list.
     *
     * @return A list of lists. Each element are in form: [sender's username, receiver's username, status, message]
     */
    public ArrayList<ArrayList<String>> getAllMessage() {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (Message message : map.values()) {
            ArrayList<String> temp = new ArrayList<>();
            temp.add(message.getSender());
            temp.add(message.getReceiver());
            temp.add(message.getText());
            temp.addAll(message.getAllStatus());
            result.add(temp);
        }
        return result;
    }

    /**
     * Initialize the message's status.
     *
     * @param ID                    message ID
     * @param Unread                unread status, true for unread.
     * @param ReceiverDeleteStatus  whether the receiver has deleted the message.
     * @param ReceiverArchiveStatus whether the receiver has archived the message.
     * @param SenderDeleteStatus    whether the sender has delete the message.
     * @param SenderArchiveStatus   whether the sender has archived the message.
     */
    public void initializeStatus(int ID, boolean Unread, boolean ReceiverDeleteStatus,
                                 boolean ReceiverArchiveStatus, boolean SenderDeleteStatus,
                                 boolean SenderArchiveStatus) {
        Message msg = map.get(ID);
        msg.setUnreadStatus(Unread);
        msg.setReceiverArchiveStatus(ReceiverArchiveStatus);
        msg.setSenderArchiveStatus(SenderArchiveStatus);
        msg.setReceiverDeleteStatus(ReceiverDeleteStatus);
        msg.setSenderDeleteStatus(SenderDeleteStatus);
    }

    /**
     * Create a Message to receiver by sender. With the message text.
     *
     * @param sender   The Username of the sender.
     * @param receiver The Username of the receiver.
     * @param text     The message.
     * @return id of the message.
     */
    public int createMessage(String sender, String receiver, String text) {
        Message message = new Message(sender, receiver, text);
        map.put(message.getID(), message);
        return message.getID();
    }

    private ArrayList<Message> getUnreadMessages(String username) {
        ArrayList<Message> rtn_list = new ArrayList<>();
        for (Message msg : this.map.values()) {
            if (msg.getReceiver().equals(username) && msg.getUnReadStatus() && !msg.getReceiverDeleteStatus()) {
                rtn_list.add(msg);
            }
        }
        return rtn_list;
    }

    private ArrayList<Message> getArchivedMessages(String username) {
        ArrayList<Message> rtn_list = new ArrayList<>();
        for (Message msg : this.map.values()) {
            if (msg.getReceiver().equals(username)) {
                if (!msg.getReceiverDeleteStatus() && msg.getReceiverArchiveStatus()) {
                    rtn_list.add(msg);
                }
            } else if (msg.getSender().equals(username)) {
                if (!msg.getSenderDeleteStatus() && msg.getReceiverArchiveStatus()) {
                    rtn_list.add(msg);
                }
            }
        }
        return rtn_list;
    }

    private ArrayList<Message> getAllMessages(String username) {
        ArrayList<Message> rtn_list = new ArrayList<>();
        for (Message msg : this.map.values()) {
            if (msg.getReceiver().equals(username)) {
                if (!msg.getReceiverDeleteStatus()) {
                    rtn_list.add(msg);
                }
            } else if (msg.getSender().equals(username)) {
                if (!msg.getSenderDeleteStatus()) {
                    rtn_list.add(msg);
                }
            }
        }
        return rtn_list;
    }
}
