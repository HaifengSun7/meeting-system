package request;

public class Request {
    private final String username;
    private final String content;
    private final String title;
    private boolean status;

    public Request(String username, String title, String content){
        this.username = username;
        this.title = title;
        this.content = content;
        this.status = false;
    }
    public String getUsername() {
        return this.username;
    }

    public String getContent() {
        return this.content;
    }

    public String getTitle() {
        return this.title;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return this.status;
    }

}
