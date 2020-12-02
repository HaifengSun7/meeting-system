package request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Requestmanager {
    private Map<String, ArrayList<Request>> userRequestMapping; // key: username; value: a list of Requests;
    private Map<String, Request> titleRequestMapping; // key: title; value: Request;

    public Requestmanager(){
        this.userRequestMapping = new HashMap<>();
        this.titleRequestMapping = new HashMap<>();
    }

    public void createNewRequest(String username, String title, String content) throws InvalidTitleException {
        if (titleRequestMapping.containsKey(title)) {
            throw new InvalidTitleException("Already exist such request title.");
        } else {
            Request request = new Request(username, title, content);
            addRequest(username, title, request);
        }
    }

    public void changeStatus(String title){
        titleRequestMapping.get(title).setStatus(true);
    }
    public boolean getRequestStatus(String title) {
        return titleRequestMapping.get(title).getStatus();
    }

    public ArrayList<String[]> getRequestsFrom(String username){
        ArrayList<String[]> allMyRequestList = new ArrayList<>();
        if (userRequestMapping.containsKey(username)){
            for (Request request: userRequestMapping.get(username)){
                String[] singleRequest = new String[] {request.getTitle(), request.getContent()};
                allMyRequestList.add(singleRequest);
            }
        }
        return allMyRequestList;
    }

    public ArrayList<String[]> getAllRequests(){ //Need to be String in the future!!!!!!!
        ArrayList<String[]> allRequestList = new ArrayList<>();
        for (Request request: titleRequestMapping.values()){
            String[] singleRequest = new String[] {request.getTitle(), request.getContent()};
            allRequestList.add(singleRequest);
        }
        return allRequestList;
    }

    public ArrayList<String[]> getAllUnsolvedRequests(){ //Need to be String in the future!!!!!!!
        ArrayList<String[]> unsolvedRequestList = new ArrayList<>();
        for (Request request: titleRequestMapping.values()){
            if (!request.getStatus()) {
                String[] singleRequest = new String[]{request.getTitle(), request.getContent()};
                unsolvedRequestList.add(singleRequest);
            }
        }
        return unsolvedRequestList;
    }

    public ArrayList<String[]> getAllSolvedRequests(){ //Need to be String in the future!!!!!!!
        ArrayList<String[]> solvedRequestList = new ArrayList<>();
        for (Request request: titleRequestMapping.values()){
            if (request.getStatus()) {
                String[] singleRequest = new String[]{request.getTitle(), request.getContent()};
                solvedRequestList.add(singleRequest);
            }
        }
        return solvedRequestList;
    }

    public void recallAllRequestsFrom(String username) throws NoSuchRequestException {
        if (userRequestMapping.containsKey(username)){
            for (Request request:userRequestMapping.get(username)){
                if (titleRequestMapping.containsKey(request.getTitle())) {
                    titleRequestMapping.remove(request.getTitle());
                } else {
                    throw new NoSuchRequestException("Cannot find such request in title-request mapping");
                }
            }
            userRequestMapping.remove(username);
        }
    }

    public void recallSingleRequest(String title) throws NoSuchRequestException {
        if (!titleRequestMapping.containsKey(title)) {
            throw new NoSuchRequestException("Cannot find such request in title-request mapping");
        }
        Request request = titleRequestMapping.get(title);
        String username = request.getUsername();
        if (!userRequestMapping.get(username).contains(request)){
            throw new NoSuchRequestException("Cannot find such request in user-request mapping");
        }
        titleRequestMapping.remove(title);
        userRequestMapping.get(username).remove(request);
    }

    private void addRequest(String username, String title, Request request){
        if (!userRequestMapping.containsKey(username)) {
            userRequestMapping.put(username, new ArrayList<>());
        }
        userRequestMapping.get(username).add(request);
        titleRequestMapping.put(title, request);
    }
//    private void recallRequest(String username, String title, Request request){
//            titleRequestMapping.remove(title);
//            userRequestMapping.get(username).remove(request);
//    }
}
