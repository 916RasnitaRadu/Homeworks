<%@page import="domain.Pair" %>
<%@page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Popular Bookmarks</title>
    <link rel="stylesheet" href="popular.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script src="main.js"></script>
</head>
<body>
  <div class="wrapper">
    <table>
      <tr>
        <th>URL</th>
        <th>COUNTS</th>
      </tr>
      <%
        List<Pair<String,Integer>> urls = (List<Pair<String, Integer>>) request.getSession().getAttribute("urls");
        for (Pair<String,Integer> pair : urls) {
          out.write("<tr>");
          out.write("<td class=\"first_column\">");
          out.write("<a href= " + pair.first + ">");
          out.write(pair.first);
          out.write("</a> </td>");
          out.write("<td class=\"second_column\">" + pair.second + "</td> </tr>");
//          out.write(pair.second);
//          out.write("</td> </tr>");
        }
      %>
    </table>
  </div>
</body>
</html>
