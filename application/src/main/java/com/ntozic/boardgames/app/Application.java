package com.ntozic.boardgames.app;

import com.ntozic.boardgames.app.servlets.HomeServlet;
import com.ntozic.boardgames.http.ApplicationRunner;
import com.ntozic.boardgames.http.annotations.Servlets;

@Servlets({
        HomeServlet.class
})
public class Application {

    public static void main(String... args) {
        ApplicationRunner.run(Application.class);
    }

}
