package controllers;

import repository.DBManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteController extends HttpServlet {
    private DBManager dbManager;

    public DeleteController() {
        super();
        this.dbManager = new DBManager();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        this.dbManager.deleteUrl(Integer.parseInt(request.getParameter("id")));
    }
}
