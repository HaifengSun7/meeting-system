package message;

public class Message {

    private String sender;
    private String receiver;
    private String text;

    public Message(String sender, String receiver, String text){
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
    }

    public String getSender(){
        return this.sender;
    }

    public String getReceiver(){
        return this.receiver;
    }

    public String getText(){
        return this.text;
    }

    @Override
    public String toString(){
        return "Message TO:" + this.receiver + ". From" + this.sender + ".\n" + this.text;
    }
}
