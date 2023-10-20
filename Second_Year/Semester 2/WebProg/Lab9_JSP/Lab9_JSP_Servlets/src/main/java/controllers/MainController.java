package controllers;

import domain.URL;
import domain.User;
import repository.DBManager;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.io.IOException;

public class MainController extends HttpServlet {

    private DBManager dbManager;

    public MainController() {
        super();
        this.dbManager = new DBManager();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("user")) {
                List<URL> urls = this.dbManager.getUrlsOfUser(cookie.getValue());
                User user = this.dbManager.getUserFromName(cookie.getValue());

                request.getSession().setAttribute("urls", urls);
                request.getSession().setAttribute("user", user);
                request.getRequestDispatcher("main.jsp").include(request,response);

                return;
            }
        }
        response.getWriter().println("Invalid request bo$$!");
    }
}
