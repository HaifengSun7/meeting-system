package request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Manager for requests.
 */
public class RequestManager {
    private final Map<String, ArrayList<Request>> userRequestMapping; // key: username; value: a list of Requests;
    private final Map<String, Request> titleRequestMapping; // key: title; value: Request;

    /**
     * Create a new request manager.
     */
    public RequestManager() {
        this.userRequestMapping = new HashMap<>();
        this.titleRequestMapping = new HashMap<>();
    }

    /**
     * Create new request.
     *
     * @param username: a User's username in string.
     * @param title:    a request's title in string.
     * @param content:  a request's content in string.
     * @throws InvalidTitleException if the title already exists.
     */
    public void createNewRequest(String username, String title, String content) throws InvalidTitleException {
        if (titleRequestMapping.containsKey(title)) {
            throw new InvalidTitleException("Already exist such request title.");
        } else {
            Request request = new Request(username, title, content);
            addRequest(username, title, request);
        }
    }

    /**
     * change the status of a request to its opposite.
     *
     * @param title: a request's title in string.
     */
    public void changeStatus(String title) {
        boolean status = titleRequestMapping.get(title).getStatus();
        titleRequestMapping.get(title).setStatus(!status);
    }

    /**
     * change the status of a request to its opposite.
     *
     * @param title: a request's title in string.
     * @return a boolean represent the status of request, e.g ture - addressed or false - pending
     */
    public boolean getRequestStatus(String title) {
        return titleRequestMapping.get(title).getStatus();
    }

    /**
     * return all requests from single user.
     *
     * @param username: a user's username in string.
     * @return an array list of list of Strings which include title and content of each request from this user.
     */
    public ArrayList<String[]> getRequestsFrom(String username) {
        ArrayList<String[]> allMyRequestList = new ArrayList<>();
        if (userRequestMapping.containsKey(username)) {
            for (Request request : userRequestMapping.get(username)) {
                String[] singleRequest = new String[]{request.getTitle(), request.getContent()};
                allMyRequestList.add(singleRequest);
            }
        }
        return allMyRequestList;
    }

    /**
     * return all requests in the system.
     *
     * @return an array list of list of Strings which include title and content of each requests in this system.
     */
    public ArrayList<String[]> getAllRequests() {
        ArrayList<String[]> allRequestList = new ArrayList<>();
        for (Request request : titleRequestMapping.values()) {
            String[] singleRequest = new String[]{request.getTitle(), request.getContent()};
            allRequestList.add(singleRequest);
        }
        return allRequestList;
    }

    /**
     * return all requests in the system.
     *
     * @return an array list of list of Strings which include title and content of each pending requests in this system.
     */
    public ArrayList<String[]> getAllUnsolvedRequests() {
        ArrayList<String[]> unsolvedRequestList = new ArrayList<>();
        for (Request request : titleRequestMapping.values()) {
            if (!request.getStatus()) {
                String[] singleRequest = new String[]{request.getTitle(), request.getContent()};
                unsolvedRequestList.add(singleRequest);
            }
        }
        return unsolvedRequestList;
    }

    /**
     * return all requests in the system.
     *
     * @return an array list of list of Strings which include title and content of each addressed requests in this system.
     */
    public ArrayList<String[]> getAllSolvedRequests() { //Need to be String in the future!!!!!!!
        ArrayList<String[]> solvedRequestList = new ArrayList<>();
        for (Request request : titleRequestMapping.values()) {
            if (request.getStatus()) {
                String[] singleRequest = new String[]{request.getTitle(), request.getContent()};
                solvedRequestList.add(singleRequest);
            }
        }
        return solvedRequestList;
    }

    /**
     * recall all request from one single user.
     *
     * @param username: a user's username in string.
     * @throws NoSuchRequestException when the request does not exist.
     */
    public void recallAllRequestsFrom(String username) throws NoSuchRequestException {
        if (userRequestMapping.containsKey(username)) {
            for (Request request : userRequestMapping.get(username)) {
                if (titleRequestMapping.containsKey(request.getTitle())) {
                    titleRequestMapping.remove(request.getTitle());
                } else {
                    throw new NoSuchRequestException("Cannot find such request in title-request mapping");
                }
            }
            userRequestMapping.remove(username);
        }
    }

    /**
     * recall one specific request from one user.
     *
     * @param title: the title of a request in string.
     * @throws NoSuchRequestException if the request does not exist.
     */
    public void recallSingleRequest(String title) throws NoSuchRequestException {
        if (!titleRequestMapping.containsKey(title)) {
            throw new NoSuchRequestException("Cannot find such request in title-request mapping");
        }
        Request request = titleRequestMapping.get(title);
        String username = request.getUsername();
        if (!userRequestMapping.get(username).contains(request)) {
            throw new NoSuchRequestException("Cannot find such request in user-request mapping");
        }
        titleRequestMapping.remove(title);
        userRequestMapping.get(username).remove(request);
    }

    /**
     * Return all requests, with format as follows:
     *
     * @return An Arraylist, each element with format: [Sender, Status, Title, Content]
     */
    public ArrayList<ArrayList<String>> getAllRequest() {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (Request request : titleRequestMapping.values()) {
            ArrayList<String> temp = new ArrayList<>();
            temp.add(request.getUsername());
            if (request.getStatus()) {
                temp.add("Addressed");
            } else {
                temp.add("Pending");
            }
            temp.add(request.getContent());
            temp.add(request.getTitle());
            result.add(temp);
        }
        return result;
    }

    private void addRequest(String username, String title, Request request) {
        if (!userRequestMapping.containsKey(username)) {
            userRequestMapping.put(username, new ArrayList<>());
        }
        userRequestMapping.get(username).add(request);
        titleRequestMapping.put(title, request);
    }
}
