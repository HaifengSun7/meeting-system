package presenter;

/**
 * Presenter for all prompts in loading.
 */
public class LoadingPresenter extends Presenter{

    public void badIndex(String input){
        System.out.println("[Error]: Bad database indexes in " + input +" database.");
    }
}
