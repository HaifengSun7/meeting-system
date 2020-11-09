package ReadWrite;

import user.UserManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class UserManagerInitializer {
    public UserManager run(){
        UserManager usermanager = new UserManager();
        UserIterator useriterator = new UserIterator();
        String[] temp;
        while (!useriterator.hasNext()) {
            temp = useriterator.next(); //do something
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
        return usermanager;
    }
}
