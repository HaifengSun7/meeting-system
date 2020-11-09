package ReadWrite;

import user.UserManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class UserManagerInitializer {
    public static UserManager run(){
        int j;
        UserManager usermanager = new UserManager();
        UserIterator userIterator = new UserIterator();
        EventIterator eventIterator = new EventIterator();
        String[] temp;
        while (userIterator.hasNext()) {
            temp = userIterator.next(); //do something
            try {
                usermanager.createUserAccount(temp[2], temp[0], temp[1]);
            } catch (Exception e) {
                System.out.println("This should not be happening.");
            }
        }
        UserIterator useriterator2 = new UserIterator();
        while (!useriterator2.hasNext()) {
            temp = useriterator2.next(); //do something
            for(int i = 3; i < temp.length; i++){
                usermanager.addContactList(temp[i], temp[0]);
            }
        }
        String[] temp2;
        int k = 0;
        while (eventIterator.hasNext()) {
            temp2 = eventIterator.next(); //do something
            for(j = 2; j < temp2.length; j++){
                try {
                    usermanager.addSignedEvent(String.valueOf(k), temp2[j]);
                } catch (Exception e) {
                    System.out.println("This should not be happening.");
                }
            }
            k += 1;
        }
        return usermanager;
    }
}
