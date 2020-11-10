package ReadWrite;

import message.MessageManager;
import user.UserManager;

public class MessageManagerInitializer {
    public MessageManager run() {
        int j;
        MessageManager messagemanager = new MessageManager();
        MessageIterator messageIterator = new MessageIterator();
        String[] temp;
        while (messageIterator.hasNext()) {
            temp = messageIterator.next(); //do something
            try {
                messagemanager.sendMessage(temp[0], temp[1], temp[2]);
            } catch (Exception e) {
                System.out.println("This should not be happening.");
            }
        }
        return messagemanager;
    }
}
