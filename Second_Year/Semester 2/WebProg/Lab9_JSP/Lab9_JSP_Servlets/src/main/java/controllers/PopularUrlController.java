package controllers;

import domain.Pair;
import repository.DBManager;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PopularUrlController extends HttpServlet {
    private DBManager dbManager;

    public PopularUrlController() {
        super();
        this.dbManager = new DBManager();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        int number = 10;
        for (Cookie c : request.getCookies()) {
            if (c.getName().equals("number")) {
                number = Integer.parseInt(c.getValue());
            }
        }
        List<Pair<String,Integer>> urls = this.dbManager.getPopularUrls(number);
        request.getSession().setAttribute("urls", urls);
        request.getRequestDispatcher("popular.jsp").include(request,response);
    }
}
