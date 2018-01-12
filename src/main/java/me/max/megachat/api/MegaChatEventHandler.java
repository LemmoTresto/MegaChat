package me.max.megachat.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MegaChatEventHandler {

    MegaChatEventPriority priority() default MegaChatEventPriority.NORMAL;

}
