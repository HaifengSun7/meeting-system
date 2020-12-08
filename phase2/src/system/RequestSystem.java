package system;

import event.EventManager;
import presenter.OrganizerPresenter;
import presenter.Presenter;
import request.InvalidTitleException;
import request.NoSuchRequestException;
import request.RequestManager;
import user.UserManager;

import java.util.ArrayList;
import java.util.Scanner;

public class RequestSystem {
    private final RequestManager requestmanager;
    private final Presenter presenter;
    private final OrganizerPresenter organizerPresenter;

    protected Scanner reader = new Scanner(System.in);
    private final String myName;

    public RequestSystem(RequestManager requestmanager, String myName) {
        this.requestmanager = requestmanager;
        this.presenter = new Presenter();
        this.organizerPresenter = new OrganizerPresenter();
        this.myName = myName;
    }


    /**
     * Create a new request.
     * Input from reader: String title
     * Input from reader: String content
     */
    protected void makeNewRequest() {
        presenter.inputPrompt("makeRequestTitle");
        String title = reader.nextLine();
        presenter.inputPrompt("makeRequestContext");
        String content = reader.nextLine();
        try {
            requestmanager.createNewRequest(myName, title, content);
            presenter.inputPrompt("addSuccess");
        } catch (InvalidTitleException e) {
            presenter.invalid("invalidRequestTitle");
        }
    }

    /**
     * Print a request list include all requests from one particular person.
     */
    protected void seeMyRequests() {
        ArrayList<String[]> requestList = requestmanager.getRequestsFrom(myName);
        if (requestList.size() == 0) {
            presenter.inputPrompt("NoRequests");
        } else {
            presenter.inputPrompt("requestIntroduction");
            printRequests(requestList);
            presenter.inputPrompt("readRequest");
            String command = reader.nextLine();
            try {
                int input = Integer.parseInt(command);
                if ((0 <= input) && (input < requestList.size())) {
                    presenter.defaultPrint(requestList.get(input)[1]);
                } else {
                    presenter.invalid("");
                    presenter.exitingToMainMenu();
                }
            } catch (NumberFormatException e) {
                if (!"e".equals(command)) {
                    presenter.invalid("");
                }
                presenter.exitingToMainMenu();
            }
        }
    }

    /**
     * This is a protected helper function.
     * Help to print out a list of requests regardless what type of requests list contained.
     * Print a request list include all requests from one particular person.
     */
    protected void printRequests(ArrayList<String[]> requestList) { //Helper function
        for (int i = 0; i < requestList.size(); i++) {
            String requestTitle = requestList.get(i)[0];
            String status;
            if (requestmanager.getRequestStatus(requestTitle)) {
                status = "[Status: Addressed] ";
            } else {
                status = "[Status: Pending]   ";
            }
            presenter.defaultPrint("[" + i + "] " + status + requestList.get(i)[0]);
        }
        presenter.exitToMainMenuPrompt();
    }

    /**
     * This method can delete requests from one user.
     */
    protected void deleteRequests() {
        ArrayList<String[]> requestList = requestmanager.getRequestsFrom(myName);
        if (requestList.size() == 0) {
            presenter.inputPrompt("NoRequests");
        } else {
            presenter.inputPrompt("requestIntroduction");
            printRequests(requestList);
            String command = reader.nextLine();
            try {
                int input = Integer.parseInt(command);
                if ((0 <= input) && (input < requestList.size())) {
                    try {
                        requestmanager.recallSingleRequest(requestList.get(Integer.parseInt(command))[0]);
                        presenter.inputPrompt("deleteSuccess");
                    } catch (NoSuchRequestException e) {
                        presenter.invalid("noSuchRequest");
                    }
                } else {
                    presenter.invalid("");
                    presenter.exitingToMainMenu();
                }
            } catch (NumberFormatException e) {
                if ("R".equals(command)) {
                    presenter.inputPrompt("recallRequestConfirm");
                    String confirm = reader.nextLine();
                    if (confirm.equals("Yes") || confirm.equals("yes") || confirm.equals("Y")) {
                        try {
                            requestmanager.recallAllRequestsFrom(myName);
                            presenter.inputPrompt("deleteSuccess");
                        } catch (NoSuchRequestException f) {
                            presenter.invalid("noSuchRequest");
                        }
                    }
                } else if (!"e".equals(command)) {
                    presenter.invalid("");
                }
                presenter.exitingToMainMenu();
            }
        }
    }

    /*
     * This is a huge helper private method in order to print requests to user on the screen and let them choose.
     *
     * @param allRequests     this is a arraylist of list of strings which include title, content of requests.
     * @param presenterString cause this method may print our different requests depends on different input,
     *                        so we need to input the message we are going to tell presenter.
     */
    private void seeRequests(ArrayList<String[]> allRequests, String presenterString) { // Huge helper function
        if (allRequests.size() == 0) {
            presenter.inputPrompt("NoRequests");
            presenter.inputPrompt("anythingToGoBack");
            reader.nextLine();
            presenter.exitingToMainMenu();
        } else {
            organizerPresenter.submenusInOrganizer(presenterString);
            printRequests(allRequests); // Here are all requests printed on the screen!
            presenter.inputPrompt("readRequest");
            String command = reader.nextLine();
            boolean validInput = false;
            boolean validNumber = false;
            int input = 0;
            while (!validInput) {
                try {
                    input = Integer.parseInt(command);
                    if ((0 <= input) && (input < allRequests.size())) {
                        validInput = true;
                        validNumber = true;
                    } else {
                        presenter.invalid("");
                        presenter.inputPrompt("readRequest");
                        command = reader.nextLine();
                    }
                } catch (NumberFormatException e) {
                    if ("e".equals(command)) {
                        validInput = true;
                    } else {
                        presenter.invalid("");
                        presenter.inputPrompt("readRequest");
                        command = reader.nextLine();
                    }
                }
            }
            if (!validNumber) {
                presenter.exitingToMainMenu();
            } else {
                presenter.defaultPrint(allRequests.get(input)[1]);
                changeRequestStatus(allRequests.get(input)[0]); // Include confirm of status change
            }
//                presenter.inputPrompt("anythingToGoBack");
//                reader.nextLine();
        }
    }

    /*
     * Show all requests in the system to organizer.
     */
    protected void seeAllRequest() {
        ArrayList<String[]> allRequests = requestmanager.getAllRequests();
        seeRequests(allRequests, "SeeAllRequestsInSystemIntroduction");
    }

    /*
     * Show all unsolved/pending requests in the system to organizer.
     */
    protected void seeUnsolvedRequest() {
        ArrayList<String[]> allRequests = requestmanager.getAllUnsolvedRequests();
        seeRequests(allRequests, "SeeAllPendingRequestsInSystemIntroduction");
    }

    /*
     * Show all solved/addressed requests in the system to organizer.
     */
    protected void seeSolvedRequest() {
        ArrayList<String[]> allRequests = requestmanager.getAllSolvedRequests();
        seeRequests(allRequests, "SeeAllAddressedRequestsInSystemIntroduction");
    }

    /*
     * change the status of a request.
     *
     * @param title the title of a specific request.
     */
    protected void changeRequestStatus(String title) {
        boolean requestSolved = requestmanager.getRequestStatus(title);
        if (requestSolved) {
            organizerPresenter.submenusInOrganizer("ChangeStatusAtoP");
        } else {
            organizerPresenter.submenusInOrganizer("ChangeStatusPtoA");
        }
        String confirm = reader.nextLine();
        if (confirm.equals("Yes") || confirm.equals("yes") || confirm.equals("Y")) {
            requestmanager.changeStatus(title);
            organizerPresenter.submenusInOrganizer("ChangeStatusSuccess");
            presenter.inputPrompt("anythingToGoBack");
            reader.nextLine();
            presenter.exitingToMainMenu();
        }
    }

}
