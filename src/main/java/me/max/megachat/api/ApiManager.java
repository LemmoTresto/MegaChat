package me.max.megachat.api;

import me.max.megachat.api.events.MegaChatEvent;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ApiManager {

    public List<Object> listeners = new ArrayList<>();

    public MegaChatEvent callEvent(MegaChatEvent event) {
        for (MegaChatEventPriority priority : MegaChatEventPriority.values()) {
            for (Object listener : listeners) {
                for (Method method : listener.getClass().getMethods()) {
                    // checks
                    if (method.getParameters().length != 1) continue; // only 1 param
                    if (!method.getParameters()[0].getType().isAssignableFrom(event.getClass()))
                        continue; //does event want this?
                    if (!method.isAnnotationPresent(MegaChatEventHandler.class)) continue; //look for our annotation

                    //loop over all annotations.
                    for (Annotation annotation : method.getAnnotations()) {
                        if (!(annotation instanceof MegaChatEventHandler)) continue; // get our own annotation

                        MegaChatEventHandler eventHandlerAnnotation = (MegaChatEventHandler) annotation;
                        if (eventHandlerAnnotation.priority() != priority)
                            continue; // not the priority we are at right now in the for loop.

                        // method has to be accessible.
                        if (!method.isAccessible()) method.setAccessible(true);

                        try {
                            method.invoke(listener, event);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return event;
    }
}
