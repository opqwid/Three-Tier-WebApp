/*  Name: Vincent Qiu
    Course: CNT 4714 – Summer 2022 – Project Three 
    Assignment title:  A Three-Tier Distributed Web-Based Application 
    Date:  August 4, 2022 
*/ 

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.sql.*;

public class ClientUserApp extends HttpServlet {
    private Connection connection;
    private Statement statement;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            Class.forName(config.getInitParameter("databaseDriver"));
            connection = DriverManager.getConnection(config.getInitParameter("databaseName"),
                    config.getInitParameter("username"), config.getInitParameter("password"));
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnavailableException(e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String textBox = request.getParameter("textBox");
        String message = null;

        if (textBox != null) {
            if (textBox.contains("select")) {
                try {
                    message = doSelectQuery(textBox);
                } catch (SQLException error) {
                    message = "<p><span class='font-bold'>[ERROR]:</span> " + error.getMessage() + "</p>";
                    error.printStackTrace();
                }
            } else {
                try {
                    message = doUpdateQuery(textBox);
                } catch (SQLException error) {
                    message = "<h4><span class='font-bold'>[ERROR]:</span> " + error.getMessage() + "</h4>";
                    error.printStackTrace();
                }
            }
        }

        HttpSession session = request.getSession();
        session.setAttribute("message", message);
        session.setAttribute("textBox", textBox);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/clientHome.jsp");
        dispatcher.forward(request, response);
    }

    private String doSelectQuery(String textBox) throws SQLException {
        String result;

        ResultSet table = statement.executeQuery(textBox);
        ResultSetMetaData metaData = table.getMetaData();

        int numOfColumns = metaData.getColumnCount();
        String tableOpeningHTML = "<table class='w-full text-left'><font color=#ffffff>";
        String tableColumnsHTML = "<thead><tr><font color=#ffffff>";
        for (int i = 1; i <= numOfColumns; i++) {
            tableColumnsHTML += "<th class='text-lg'><font color=#ffffff>" + metaData.getColumnName(i) + "</th></font>";
        }
        tableColumnsHTML += "</tr></thead>";

        String tableBodyHTML = "<tbody><font color=#ffffff>";
        int counter = 1;
        while (table.next()) {
            if (counter % 2 == 0) {
                tableBodyHTML += "<tr class='bg-gray-200'>";
            } else {
                tableBodyHTML += "<tr>";
            }
            for (int i = 1; i <= numOfColumns; i++) {
                tableBodyHTML += "<td class='p-1'>" + table.getString(i) + "</td>";
            }
            tableBodyHTML += "</tr>";
            counter += 1;
        }

        tableBodyHTML += "</tbody></table>";
        result = tableOpeningHTML + tableColumnsHTML + tableBodyHTML;
        return result;
    }

    private String doUpdateQuery(String textBox) throws SQLException {
        String result = null;
        statement.executeUpdate(textBox);
        return result;
    }
}