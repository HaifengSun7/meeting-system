package message;

import user.User;

import java.util.ArrayList;


public class MessageManager {

    private ArrayList<Message> Messages =  new ArrayList<Message>();

    public MessageManager(){}

    public void sendMessage(String sender, String receiver, String text){
        Message message = new Message(sender, receiver, text);
        Messages.add(message);
    }

    public ArrayList<Message> getSent(String username) {
        ArrayList<Message> rtn_list = new ArrayList<Message>();
        for(Message msg:this.Messages){
            if(msg.getSender().equals(username)){
                rtn_list.add(msg);
            }
        }
        return rtn_list;//TODO: Please return a list of String, or write another method that returns a String. Haifeng
    }

    public ArrayList<Message> getInbox(String username){
        ArrayList<Message> rtn_list = new ArrayList<Message>();
        for(Message msg:this.Messages){
            if(msg.getReceiver().equals(username)){
                rtn_list.add(msg);
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
