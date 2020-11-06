package message;

import user.User;

import java.util.ArrayList;

/**
 * @version 1.0.2
 * @author Shaohong Chen, Haoyang Wang
 * The message manager that organizes sending, receiving of messages.
 */

public class MessageManager {

    private ArrayList<Message> Messages =  new ArrayList<Message>();

    public MessageManager(){}

    public void sendMessage(String sender, String receiver, String text){
        Message message = new Message(sender, receiver, text);
        Messages.add(message);
    }

    public ArrayList<String> getSent(String username) {
        ArrayList<String> rtn_list = new ArrayList<String>();
        for(Message msg:this.Messages){
            if(msg.getSender().equals(username)){
                rtn_list.add(msg.toString());
            }
        }
        return rtn_list;
    }

    public ArrayList<String> getInbox(String username){
        ArrayList<String> rtn_list = new ArrayList<String>();
        for(Message msg:this.Messages){
            if(msg.getSender().equals(username)){
                rtn_list.add(msg.toString());
            }
        }
        return rtn_list;
    }

    public void sendToList(String u, ArrayList<String> receivers, String text){
        for(String user:receivers){
            sendMessage(u, user, text);
        }
    }

    public void reply(Message msg, String text){
        sendMessage(msg.getReceiver(), msg.getSender(), text);
    }

}
