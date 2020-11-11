package message;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * The message manager that organizes sending, receiving of messages.
 * @version 1.0.2
 * @author Shaohong Chen, Haoyang Wang
 */
public class MessageManager {

    private ArrayList<Message> messages =  new ArrayList<Message>();

    /**
     * Send a Message to receiver by sender. With the message text.
     * @param sender The Username of the sender.
     * @param receiver The Username of the receiver.
     * @param text The message.
     */
    public void sendMessage(String sender, String receiver, String text){
        Message message = new Message(sender, receiver, text);
        messages.add(message);
    }

    /**
     * Get the messages that the user has sent.
     * @param username the userName of the user.
     * @return The list of sent messages, in form of a list of Strings, each element is the string form of the Message.
     */
    public ArrayList<String> getSent(String username) {
        ArrayList<String> rtn_list = new ArrayList<String>();
        for(Message msg:this.messages){
            if(msg.getSender().equals(username)){
                rtn_list.add(msg.toString());
            }
        }
        return rtn_list;
    }

    /**
     * Get the inbox messages of the user.
     * @param username The username of the user that we are looking for.
     * @return The list of inbox messages, in form of a list of Strings, each element is the string form of the Message.
     */
    public ArrayList<String> getInbox(String username){
        ArrayList<String> rtn_list = new ArrayList<String>();
        for(Message msg:this.messages){
            if(msg.getReceiver().equals(username)){
                rtn_list.add(msg.toString());
            }
        }
        return rtn_list;
    }

    /**
     * Get the inbox messages of the user.
     * @param username The username of the user that we are looking for.
     * @return The list of inbox messages, in form of a list of Strings, each element is the string form of the Message.
     */
    public ArrayList<String> getInboxSender(String username){
        ArrayList<String> rtn_list = new ArrayList<String>();
        for(Message msg:this.messages){
            if(msg.getReceiver().equals(username)){
                rtn_list.add(msg.getReceiver());
            }
        }
        return rtn_list;
    }
    /**
     * Send a Message to a list of receivers by sender. With the same message text.
     * @param u The Username of the sender.
     * @param receivers The list of Usernames of the receivers.
     * @param text The message.
     */
    public void sendToList(String u, ArrayList<String> receivers, String text){
        for(String user:receivers){
            sendMessage(u, user, text);
        }
    }

    public ArrayList<ArrayList<String>> getAllMessage() {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();
        for(Message message: messages){
            temp.add(message.getSender());
            temp.add(message.getReceiver());
            temp.add(message.getText());
            result.add(temp);
            temp.clear();
        }
        return result;
    }
}
