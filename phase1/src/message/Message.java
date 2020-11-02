package message;
import users.user;

public class Message {
    private User sen;
    private User rec;
    private String text;
    public void message(User sender, User receiver, String text){
        this.sen = sender;
        this.rec = receiver;
        this.text = text;
    }

    public String getText(){
        return this.text;
    }
}
