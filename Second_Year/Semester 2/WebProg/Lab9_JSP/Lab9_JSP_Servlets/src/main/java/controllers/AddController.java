package controllers;

import repository.DBManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddController extends HttpServlet {
    private DBManager dbManager;

    public AddController() {
        super();
        this.dbManager = new DBManager();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        this.dbManager.addUrl(Integer.parseInt(req.getParameter("userId")), req.getParameter("url"));
    }
}
