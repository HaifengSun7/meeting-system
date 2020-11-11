package ReadWrite;

import message.MessageManager;
import user.UserManager;

public class MessageManagerInitializer {
    public MessageManager run() {
        int j;
        MessageManager messagemanager = new MessageManager();
        MessageIterator messageIterator = new MessageIterator();
        String[] temp;
        String temp_str;
        while (messageIterator.hasNext()) {
            temp = messageIterator.next(); //do something
            StringBuilder temp_strBuilder = new StringBuilder(temp[2]);
            for(j = 3; j < temp.length; j++){
                temp_strBuilder.append(',').append(temp[j]);
            }
            temp_str = temp_strBuilder.toString();
            try {
                messagemanager.sendMessage(temp[0], temp[1], temp_str);
            } catch (Exception e) {
                System.out.println("1");
            }
        }
        return messagemanager;
    }
}
