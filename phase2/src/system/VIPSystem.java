package system;

import java.util.ArrayList;

/**
 * The VIP's text user interface. Extends from attendee.
 */
public class VIPSystem extends AttendeeSystem {

    /**
     * Initializes VIP system.
     *
     * @param myName The username of the VIP.
     */
    public VIPSystem(String myName) {
        super(myName);
    }

    /**
     * Signs up for event as a VIP.
     */
    @Override
    protected void SignUpForEvent() {
        ArrayList<String> example_list = eventmanager.canSignUp(myName, true, conference);
        super.SignUp(example_list);
    }
}
