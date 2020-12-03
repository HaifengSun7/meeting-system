package system;

import java.util.ArrayList;

public class VIPSystem extends AttendeeSystem{

    public VIPSystem(String myName) {
        super(myName);
    }

    @Override
    protected void SignUpForEvent() {
        ArrayList<String> example_list = eventmanager.canSignUp(myName, true);
        super.SignUp(example_list);
    }
}
