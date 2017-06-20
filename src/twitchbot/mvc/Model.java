package twitchbot.mvc;

import java.util.ArrayList;
import java.util.List;

public abstract class Model {

    private List<Listener> listeners;
    
    public Model() {
        listeners = new ArrayList<>();
    }
    
    public void addListener(Listener listener) {
        listeners.add(listener);
    }
    
    public void notifyListeners() {
        for (Listener l : listeners) {
            l.onChange();
        } 
    }
}
