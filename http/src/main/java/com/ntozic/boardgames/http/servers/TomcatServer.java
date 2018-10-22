package com.ntozic.boardgames.http.servers;

import com.ntozic.boardgames.http.props.PropertyReader;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.annotation.WebServlet;
import java.io.File;
import java.util.List;

public class TomcatServer {
    private static final String PORT = "server.port";
    private static final String HOSTNAME = "server.hostname";

    private static Tomcat tomcat;

    public static void startTomcat(List<Class> servlets, List<Class> filters) throws LifecycleException {
        final PropertyReader reader = new PropertyReader();
        tomcat = new Tomcat();

        tomcat.setPort(reader.integer(PORT));
        tomcat.setHostname(reader.string(HOSTNAME));
//        String appBase = ".";
//        tomcat.getHost().setAppBase(appBase);

        File docBase = new File(System.getProperty("java.io.tmpdir"));
        Context context = tomcat.addContext("", docBase.getAbsolutePath());

        // add a addServlets
        addServlets(context, servlets);

        // add a addFilters and filterMapping
        addFilters(context, filters);

        tomcat.start();
        tomcat.getServer()
                .await();
    }

    private static void addServlets(Context context, List<Class> servlets) {
        System.out.println("Registering " + servlets.size() + " servlets.");
        servlets.forEach(clazz -> {
            final String url = ((WebServlet) clazz.getAnnotation(WebServlet.class)).urlPatterns()[0];
            final String classSimpleName = clazz.getSimpleName();
            System.out.println("Routing " + url + " to " + classSimpleName);
            Tomcat.addServlet(context, classSimpleName, clazz.getName());
            context.addServletMappingDecoded(
                    ((WebServlet) clazz.getAnnotation(WebServlet.class)).urlPatterns()[0],
                    classSimpleName
            );
        });
    }

    private static void addFilters(Context context, List<Class> filters) {
//        Class filterClass = MyFilter.class;
//        FilterDef myFilterDef = new FilterDef();
//        myFilterDef.setFilterClass(filterClass.getName());
//        myFilterDef.setFilterName(filterClass.getSimpleName());
//        context.addFilterDef(myFilterDef);
//
//        FilterMap myFilterMap = new FilterMap();
//        myFilterMap.setFilterName(filterClass.getSimpleName());
//        myFilterMap.addURLPattern("/my-addServlets/*");
//        context.addFilterMap(myFilterMap);
    }

    public void stopTomcat() throws LifecycleException {
        tomcat.stop();
        tomcat.destroy();
    }

}
