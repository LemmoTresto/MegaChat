package me.max.megachat.api.exceptions;

public class NoListenersFoundException extends RuntimeException {

    public NoListenersFoundException(Object obj) {
        super(obj.getClass().getName() + " tried to register its listeners but no methods annotated with @MegaChatEventHandler were found.");
    }

}
