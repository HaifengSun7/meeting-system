package system;

import presenter.OrganizerPresenter;
import presenter.Presenter;
import request.InvalidTitleException;
import request.NoSuchRequestException;
import request.RequestManager;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The system that controls requests.
 */
public class RequestSystem {
    private final RequestManager requestmanager;
    private final Presenter presenter;
    private final OrganizerPresenter organizerPresenter;

    protected Scanner reader = new Scanner(System.in);
    private final String myName;

    /**
     * Constructs the request system.
     *
     * @param requestmanager that contains all information about the sent requests.
     * @param myName the username of the user logged in.
     */
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
            presenter.inputPrompt("anythingToGoBack");
            reader.nextLine();
            presenter.exitingToMainMenu();
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
     * This method can delete requests from one user.
     */
    protected void deleteRequests() {
        ArrayList<String[]> requestList = requestmanager.getRequestsFrom(myName);
        if (requestList.size() == 0) {
            presenter.inputPrompt("NoRequests");
            presenter.inputPrompt("anythingToGoBack");
            reader.nextLine();
            presenter.exitingToMainMenu();
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

    private void printRequests(ArrayList<String[]> requestList) {
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

    private void seeRequests(ArrayList<String[]> allRequests, String presenterString) { // Huge helper function
        if (allRequests.size() == 0) {
            organizerPresenter.submenusInOrganizer("NoRequestsInSystem");
            presenter.inputPrompt("anythingToGoBack");
            reader.nextLine();
            presenter.exitingToMainMenu();
        } else {
            organizerPresenter.submenusInOrganizer(presenterString);
            printRequests(allRequests); // Here are all requests printed on the screen!
            presenter.inputPrompt("readRequest");
            AtomicReference<String> command = new AtomicReference<>(reader.nextLine());
            AtomicBoolean validInput = new AtomicBoolean(false);
            AtomicBoolean validNumber = new AtomicBoolean(false);
            AtomicInteger input = new AtomicInteger();
            while (!validInput.get()) {
                try {
                    input.set(Integer.parseInt(command.get()));
                    if ((0 <= input.get()) && (input.get() < allRequests.size())) {
                        validInput.set(true);
                        validNumber.set(true);
                    } else {
                        presenter.invalid("");
                        presenter.inputPrompt("readRequest");
                        command.set(reader.nextLine());
                    }
                } catch (NumberFormatException e) {
                    if ("e".equals(command.get())) {
                        validInput.set(true);
                    } else {
                        presenter.invalid("");
                        presenter.inputPrompt("readRequest");
                        command.set(reader.nextLine());
                    }
                }
            }
            if (!validNumber.get()) {
                organizerPresenter.exitingToMainMenu();
            } else {
                organizerPresenter.defaultPrint(allRequests.get(input.get())[1]);
                changeRequestStatus(allRequests.get(input.get())[0]); // Include confirm of status change
            }
        }
    }

    /**
     * Show all requests in the system to organizer.
     */
    protected void seeAllRequest() {
        ArrayList<String[]> allRequests = requestmanager.getAllRequests();
        seeRequests(allRequests, "SeeAllRequestsInSystemIntroduction");
    }

    /**
     * Show all unsolved/pending requests in the system to organizer.
     */
    protected void seeUnsolvedRequest() {
        ArrayList<String[]> allRequests = requestmanager.getAllUnsolvedRequests();
        seeRequests(allRequests, "SeeAllPendingRequestsInSystemIntroduction");
    }

    /**
     * Show all solved/addressed requests in the system to organizer.
     */
    protected void seeSolvedRequest() {
        ArrayList<String[]> allRequests = requestmanager.getAllSolvedRequests();
        seeRequests(allRequests, "SeeAllAddressedRequestsInSystemIntroduction");
    }

    /**
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
