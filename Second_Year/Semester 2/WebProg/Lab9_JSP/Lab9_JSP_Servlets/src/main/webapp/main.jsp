
<%@ page import="java.util.List" %>
<%@page import="domain.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Main page</title>
    <link rel="stylesheet" href="main.css">
    <script src="main.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
</head>
<body>
    <div class="wrapper">
        <div class="topWrapper">
            <%
                out.write("<h3>Hi, " + ((User) request.getSession().getAttribute("user")).getUsername() + "!</h3>");
                out.write("<br>");
            %>
        </div>
        <label>
            <input type="input" id="cntText" placeholder="Count:">
            <%
                out.write("<button onclick=\"nav()\">URLs</button>");
            %>
        </label>
        <label>
            <input type="input" id="urlText" placeholder="Add bookmark">
            <%
                out.write("<button onclick=\"add(" + ((User) request.getSession().getAttribute("user")).getId() + ")\">Add</button>");
            %>
        </label>

        <ul>
            <%
                // noinspection unchecked
                List<URL> urls = (List<URL>) request.getSession().getAttribute("urls");
                if (urls.size() == 0) {
                    out.write("<h5> Oops! It looks you don't have any saved urls yet.. </h5>");
                }
                else {
                    for (URL url : urls) {
                        out.write("<li> <a href=\"" + url.getUrl() + "\">\"" + url.getUrl() + "\"</a> <button onclick=\"del(" + url.getId() + ")\"> Delete </button> </li>");
                    }
                }
            %>
        </ul>

        <label>
            <button onclick="logout()"> Log out</button>
        </label>

        <div class="notification" id="errorMsg">
            <p>Invalid url! Try another one</p>
            <span class="notification__progress"></span>
        </div>
    </div>
</body>
</html>
