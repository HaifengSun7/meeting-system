package request;

import java.util.ArrayList;
import java.util.Map;

public class Requestmanager {
    private Map<String, ArrayList<Request>> userRequestMapping; // key: username; value: a list of Requests;
    private Map<String, Request> titleRequestMapping; // key: title; value: Request;

    public void createNewRequest(String username, String title, String content) throws InvalidTitleException {
        if (titleRequestMapping.containsKey(title)) {
            throw new InvalidTitleException("Already exist such request title.");
        }
        Request request = new Request(username, title, content);
        addRequest(username, title, request);
    }

    public void changeStatus(String title){
        titleRequestMapping.get(title).setStatus(true);
    }

    public ArrayList<String[]> getMineRequests(String username){
        ArrayList<String[]> allMineRequestList = new ArrayList<>();
        for (Request request: userRequestMapping.get(username)){
            String[] singleRequest = new String[] {request.getTitle(), request.getContent()};
            allMineRequestList.add(singleRequest);
        }
        return allMineRequestList;
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
            ArrayList<Request> requestList = userRequestMapping.get(username);
            for (Request request:requestList){
                if (!titleRequestMapping.containsKey(request.getTitle())) {
                    throw new NoSuchRequestException("Cannot find such request in title-request mapping");
                } else {
                    recallRequest(username, request.getTitle(), request);
                }
            }
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
        recallRequest(username, title, request);
    }

    private void addRequest(String username, String title, Request request){
        if (!userRequestMapping.containsKey(username)) {
            userRequestMapping.put(username, new ArrayList<>());
        }
        userRequestMapping.get(username).add(request);
        titleRequestMapping.put(title, request);
    }

    private void recallRequest(String username, String title, Request request){
            titleRequestMapping.remove(title);
            userRequestMapping.get(username).remove(request);
    }
}
