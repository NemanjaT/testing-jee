package com.ntozic.boardgames.http;

import com.ntozic.boardgames.http.annotations.Filters;
import com.ntozic.boardgames.http.annotations.Servlets;
import com.ntozic.boardgames.http.servers.TomcatServer;
import org.apache.catalina.LifecycleException;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;

public class ApplicationRunner {

    public static void run(Class configurationClass) {
        try {
            TomcatServer.startTomcat(
                    Arrays.asList(
                            servlets(getClassesFromAnnotation(Servlets.class, configurationClass))),
                    Arrays.asList(
                            filters(getClassesFromAnnotation(Filters.class, configurationClass)))
            );
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }

    private static Annotation getClassesFromAnnotation(Class<? extends Annotation> annotation, Class<?> configurationClass) {
        return Optional.ofNullable(configurationClass)
                .flatMap(confClass -> Optional.ofNullable(confClass.getAnnotation(annotation)))
                .orElse(null);
    }

    private static Class<?>[] servlets(Annotation annotation) {
        if (annotation instanceof Servlets) {
            return ((Servlets) annotation).value();
        }
        return new Class<?>[0];
    }

    private static Class<?>[] filters(Annotation annotation) {
        if (annotation instanceof Filters) {
            return ((Filters) annotation).value();
        }
        return new Class<?>[0];
    }

}
