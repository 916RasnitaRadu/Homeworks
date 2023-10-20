
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
    <link rel="stylesheet" href="login.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script src="login.js"></script>
</head>
<body>
    <div class="wrapper">
        <h1> Welcome to Bookmarks!!</h1>
        <h5> Please log in before you browse bookmarks </h5>

        <form action="login" method="post">
            <label>
                <input type="text" name="username" placeholder="Username:" autocomplete="off">
            </label>
            <label>
                <input type="password" name="password" placeholder="Password" autocomplete="off">
            </label>
            <%
                String err = (String) session.getAttribute("error");
                if (err != null) {
                    out.write("<p>" + err + "</p>");
                }
            %>
            <div id="buttons">
                <button type="submit">Login</button>
                <button onclick="nav()" type="button">Urls</button>
            </div>
        </form>
    </div>
</body>
</html>
