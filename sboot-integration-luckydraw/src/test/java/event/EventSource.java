package event;

import java.util.Enumeration;
import java.util.Vector;

public class EventSource {

    private Vector<EventListener> repository = new Vector<>();//监听自己的监听器队列

    public EventSource(){}

    public void addListener(EventListener dl) {
        repository.addElement(dl);
    }

    public void notifyEvent() {//通知所有的监听器
        Enumeration<EventListener> enums = repository.elements();
        while(enums.hasMoreElements()) {
            EventListener dl = enums.nextElement();
            dl.handleEvent(new EventObject(this));
        }
    }

}
