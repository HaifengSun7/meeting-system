package presenter;

/**
 * Presenter for all prompts in loading.
 */
public class LoadingPresenter extends Presenter{

    public void printLoad(){
        System.out.println("Connection to SQLite has been established.");
    }

    public void badIndex(String input){
        System.out.println("[Error]: Bad database indexes in " + input +" database.");
    }
}
