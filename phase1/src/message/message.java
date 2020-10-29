package message;
import users.user;

public class message {
    private user sen;
    private user rec;
    private String text;
    public void message(user sender, user receiver, String text){
        this.sen = sender;
        this.rec = receiver;
        this.text = text;
    }

    public String getText(){
        return this.text;
    }
}
