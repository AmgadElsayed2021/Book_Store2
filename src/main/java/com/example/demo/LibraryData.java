package com.example.demo;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static com.example.demo.DBConnection.initDB;


@WebServlet(name = "libraryData", value = "/library-data")
public class LibraryData extends HttpServlet {
    Connection conn;

    public void init() {
        try {
            conn= initDB();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String requestType = request.getParameter("view");


        Connection conn;
        Statement stmt;

        try {
            conn = initDB();
            stmt = conn.createStatement();

            PreparedStatement preppedStmt = null;
            String Header = "<div><h1>" + requestType.substring(0, 1).toUpperCase() + requestType.substring(1) + "</h1></div>";
            if (requestType.equals("books")){
                out.println("<html><head>\n<title>View Books</title>\n<link rel=\"stylesheet\"href=\"styles.css\">\n</head><body ><header>\n" +
                        "    <nav >\n" +
                        "        <div ><ul>\n" +
                        "            <li><a href=\"index.jsp\">Home</a></li>\n" +
                        "            <li><a href=\"library-data?view=books\">View Books</a></li>\n" +
                        "            <li><a href=\"library-data?view=authors\">View Authors</a></li>\n" +
                        "            <li><a href=\"addBook.jsp\">Add Books</a></li>\n" +
                        "            <li><a href=\"addAuthor.jsp\">Add Authors</a></li>\n" +
                        "        </ul>\n" +
                        "        </div>\n" +
                        "    </nav>\n" +
                        "\n" +
                        "</header>\n");
                out.println(Header);

                String querySQL = "select * from titles";
                List<Books> books = new ArrayList<>();
                try {

                    ResultSet set = stmt.executeQuery(querySQL);

                    while (set.next()) {
                        Books book = new Books();
                        book.setISBN(set.getString("isbn"));
                        book.setTitle(set.getString("title"));
                        book.setEdition(set.getInt("editionNumber"));
                        book.setCopyright(set.getString("copyright"));

                        String query = "select a.authorID, a.firstName, a.lastName from authors a join authorisbn ai on a.authorID = ai.authorID join titles t on ai.isbn = t.isbn where t.isbn = '"+book.getISBN()+"' ";
                        preppedStmt = conn.prepareStatement(query);
                        ResultSet set2 = preppedStmt.executeQuery();
                        List<Author> authList=new ArrayList<>();
                        while (set2.next()) {
                            Author author = new Author();
                            author.setID(set2.getInt("authorID"));
                            author.setAuthorFName(set2.getString("firstName"));
                            author.setAuthorLName(set2.getString("lastName"));
                            authList.add(author);
                        }
                        book.setAuthorList(authList);
                        books.add(book);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                out.println("<table ><tr><th>ISBN</th><th>Title</th><th>Edition</th><th>Copyright</th><th>Authors</th></tr>");
                for(Books book : books){
                    int count = 0;
                    String authorsNames = getString(book);
                    out.println("<tr><td>" + book.getISBN() + "</td><td>" + book.getTitle() + "</td><td>"
                            + book.getEdition() + "</td><td>" + book.getCopyright() + "</td><td>"
                            + authorsNames + "</td></tr>");
                }



            }  else if (requestType.equals("authors")) {
                out.println("<html><head>\n<title>View Authors</title>\n<link rel=\"stylesheet\"href=\"styles.css\">\n</head><body ><header>\n" +
                        "    <nav >\n" +
                        "        <div ><ul>\n" +
                        "            <li><a href=\"index.jsp\">Home</a></li>\n" +
                        "            <li><a href=\"library-data?view=books\">View Books</a></li>\n" +
                        "            <li><a href=\"library-data?view=authors\">View Authors</a></li>\n" +
                        "            <li><a href=\"addBook.jsp\">Add Books</a></li>\n" +
                        "            <li><a href=\"addAuthor.jsp\">Add Authors</a></li>\n" +
                        "        </ul>\n" +
                        "        </div>\n" +
                        "    </nav>\n" +
                        "\n" +
                        "</header>\n");
                out.println(Header);

                String query2;
                query2 = " select * from authors ";
                List<Author> authorList = new ArrayList<>();
                try {
                    ResultSet set = stmt.executeQuery(query2);

                    while (set.next()) {
                        Author author = new Author();
                        author.setID(set.getInt("authorID"));
                        author.setAuthorFName(set.getString("firstName"));
                        author.setAuthorLName(set.getString("lastName"));
                        String query = "select t.isbn, t.title, t.editionNumber, t.copyright from titles t join authorisbn ai on t.isbn = ai.isbn join authors a on ai.authorID = a.authorID where a.authorID = ? ";
                        preppedStmt = conn.prepareStatement(query);
                        preppedStmt.setInt(1,author.getID());
                        ResultSet set2 = preppedStmt.executeQuery();
                        List<Books> books =new ArrayList<>();
                        while (set2.next()) {
                            Books book;
                            book = new Books();
                            book.setISBN(set2.getString("isbn"));
                            book.setTitle(set2.getString("title"));
                            book.setEdition(set2.getInt("editionNumber"));
                            book.setCopyright(set2.getString("copyright"));
                            books.add(book);
                        }
                        author.setBooksList(books);
                        authorList.add(author);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                out.println("<table><tr><th>Author ID</th><th>First Name</th><th>Last Name</th><th>Books</th><th>ISBN</th><th>Edition no</th><th>CopyRight</th></tr>");
                for(Author author : authorList){
                    String BooksNames = getBooksString(author);
                    String ISBN = getISBNString(author);
                    String Edition = getEditionString(author);
                    String CopyRight = getCopyRightString(author);

                    out.println("<tr><td>" + author.getID() + "</td><td>" + author.getAuthorFName() + "</td><td>"
                                + author.getAuthorLName() + "</td><td>" + BooksNames + "</td><td>" + ISBN + "</td>" +
                            "<td>" + Edition + "</td><td>" + CopyRight + "</td></tr>");
                    }
            }
            conn.close();
            stmt.close();
            if (preppedStmt != null) {
                preppedStmt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.println("</table></body></html>");
    }

    private String getBooksString(Author author ) {
        String bookString="";
        int count=1;
        StringBuilder bookStringBuilder = new StringBuilder(bookString);
        for(Books book:author.getBooksList()){
            bookStringBuilder.append(count).append("- ").append(book.getTitle()).append("<br>");
            count++;
        }
        bookString = bookStringBuilder.toString();
        return bookString;
    }
    private String getISBNString(Author author) {
        String bookString="";

        StringBuilder bookStringBuilder = new StringBuilder(bookString);
        for(Books book:author.getBooksList()) bookStringBuilder.append(book.getISBN()).append("<br>");
        bookString = bookStringBuilder.toString();
        return bookString;
    }
    private String getEditionString(Author author) {
        String bookString="";
        StringBuilder bookStringBuilder = new StringBuilder(bookString);
        for(Books book:author.getBooksList()) bookStringBuilder.append(book.getEdition()).append("<br>");
        bookString = bookStringBuilder.toString();
        return bookString;
    }
    private String getCopyRightString(Author author) {
        String bookString="";

        StringBuilder bookStringBuilder = new StringBuilder(bookString);
        for(Books book:author.getBooksList()) bookStringBuilder.append(book.getCopyright()).append("<br>");
        bookString = bookStringBuilder.toString();
        return bookString;
    }

    private String getString(Books book) {
        String authorsNames="";
        int count=1;

        StringBuilder authorStringBuilder = new StringBuilder(authorsNames);
        for(Author author:book.getAuthorList()){
            authorStringBuilder.append(count).append("- ").append(author.getAuthorFName()).append(author.getAuthorLName()).append("<br>");
            count++;
        }
        authorsNames = authorStringBuilder.toString();
        return authorsNames;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Verify</title><link rel=\"stylesheet\"href=\"styles.css\">\n</head><body><header>\n" +
                "    <nav >\n" +
                "        <div ><ul>\n" +
                "            <li><a href=\"index.jsp\">Home</a></li>\n" +
                "            <li><a href=\"library-data?view=books\">View Books</a></li>\n" +
                "            <li><a href=\"library-data?view=authors\">View Authors</a></li>\n" +
                "            <li><a href=\"addBook.jsp\">Add Books</a></li>\n" +
                "            <li><a href=\"addAuthor.jsp\">Add Authors</a></li>\n" +
                "        </ul>\n" +
                "        </div>\n" +
                "    </nav>\n" +
                "\n" +
                "</header>\n");
        String type = request.getParameter("type");

        if (type.equals("author")){
            String firstName = request.getParameter("fName");
            String lastName = request.getParameter("lName");
            try {
                AddAuthor(firstName,lastName);
                out.println("<p>Author " + firstName + " " + lastName + " was successfully added to the database.</p><br><a href=\"index.jsp\">Home</a>");

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else if (type.equals("book")){
            String isbn = request.getParameter("isbn");
            String title = request.getParameter("title");
            int edition = Integer.parseInt(request.getParameter("edition"));
            String copyright = request.getParameter("copyright");
            String firstName = request.getParameter("fName");
            String lastName = request.getParameter("lName");

            try {
                AddABook(isbn, title, edition, copyright);
                AddAuthor(firstName, lastName);
                AddCommonDataIntoAuthorISBNTable(firstName,lastName, isbn);
                out.println("<p>" +"Book: "+ title + " written by: " + firstName + " " + lastName+" has been added.<br><a href=\"index.jsp\">Home</a>");

            } catch (ClassNotFoundException | SQLException  e) {
                e.printStackTrace();
            }
        }
        out.println("</body></html>");
    }

    public void AddAuthor(String firstName, String lastName) throws ClassNotFoundException, SQLException {
        String query = "insert into authors  values (default,'"+ firstName +"','"+ lastName +"')";
        Connection conn = initDB();
        PreparedStatement preppedStmt = conn.prepareStatement(query);
        preppedStmt.execute();
        conn.close();
    }

    public int searchAuthorID(String fName, String lName) throws ClassNotFoundException {
        int ID = 0;
        try {
            conn = DBConnection.initDB();
            Statement stmt = conn.createStatement();
            String query = ("select  authorID  from authors where firstName='" + fName + "' and lastName='" + lName + "'");
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                ID = rs.getInt(1);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ID;
    }

    public void AddCommonDataIntoAuthorISBNTable(String fName,String lName, String isbn) throws ClassNotFoundException, SQLException {
        int ID =searchAuthorID(fName, lName);
        try {
            conn = DBConnection.initDB();
            String query1 = ("INSERT INTO authorisbn VALUES(?,?)");
            PreparedStatement preparedStmt = conn.prepareStatement(query1);
            preparedStmt.setInt(1,ID);
            preparedStmt.setString(2,isbn);
            preparedStmt.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void AddABook(String isbn, String title, int edition, String copyright) throws ClassNotFoundException, SQLException {
        String query = "insert into titles (isbn, title, editionNumber, copyright) Values ('"+isbn+"','"+title+"','"+edition+"','"+copyright+"')";
        Connection conn = initDB();
        PreparedStatement preppedStmt = conn.prepareStatement(query);
        preppedStmt.execute();
        conn.close();

    }
}
