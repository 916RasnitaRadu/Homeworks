package controllers;

import domain.User;
import repository.DBManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginController extends HttpServlet {

    private static final String ERROR = "error";

    public LoginController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String username = request.getParameter("username");
        String password = request.getParameter("password");


        if (username.equals("")) {
            request.getSession().setAttribute(ERROR, "Username must not be empty!");
            request.getRequestDispatcher("login.jsp").include(request,response);
        }
        else if (password.isEmpty()) {
            request.getSession().setAttribute(ERROR, "Password must not be empty!");
            request.getRequestDispatcher("login.jsp").include(request, response);
        }
        else if (password.length() < 6) {
            request.getSession().setAttribute(ERROR, "Password is not strong enough! It must have more than 6 characters!");
            request.getRequestDispatcher("login.jsp").include(request, response);
        }
        else {
            DBManager dbManager = new DBManager();
            User user = dbManager.authenticate(username, password);

            if (user != null) {
                response.addCookie(new Cookie("user", user.getUsername()));
                response.sendRedirect("main");
            } else {
                request.getSession().setAttribute(ERROR, "Username or password invalid!");
                request.getRequestDispatcher("login.jsp").include(request,response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.getRequestDispatcher("login.jsp").include(request,response);
    }
}
