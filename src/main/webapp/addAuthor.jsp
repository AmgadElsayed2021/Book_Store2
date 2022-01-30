<%--
  Created by IntelliJ IDEA.
  User: amgad
  Date: 2022-01-27
  Time: 10:07 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>add author</title>
    <link rel="stylesheet"href="styles.css">

</head>
<body>
<header>
    <ul>
        <li><a href="index.jsp">Home</a></li>
        <li><a href="library-data?view=books">View Books</a></li>
        <li><a href="library-data?view=authors">View Authors</a></li>
        <li><a href="addBook.jsp">Add Books</a></li>
        <li><a href="addAuthor.jsp">Add Authors</a></li>

    </ul>
</header>
<h1>Add an Author</h1>
<form action="library-data" method="post">
    <input type="hidden" name="type" value="author">
    <label>First Name:</label><label><input type="text" name="fName"></label>
    <label>Last Name :</label><label><input type="text" name="lName"></label>
    <input type="submit">

</form>
</body>
</html>
