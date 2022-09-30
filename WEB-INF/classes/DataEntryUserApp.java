/*  Name: Vincent Qiu
    Course: CNT 4714 – Summer 2022 – Project Three 
    Assignment title:  A Three-Tier Distributed Web-Based Application 
    Date:  August 4, 2022 
*/ 

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.sql.*;

public class DataEntryUserApp extends HttpServlet {
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
        String snum = request.getParameter("snum");
        String pnum = request.getParameter("pnum");
        String jnum = request.getParameter("jnum");
        String quantity = request.getParameter("quantity");
        String textBoxresult = null;
        String message = null;

        if (snum != null) {
                try {
                    message = doUpdateQuery(textBox, snum, pnum, jnum, quantity);
                } catch (SQLException error) {
                    message = "<p><span class='font-bold'>[ERROR]:</span> " + error.getMessage() + "</p>";
                    error.printStackTrace();
                }
        }

        HttpSession session = request.getSession();
        session.setAttribute("message", message);
        session.setAttribute("textBox", textBox);
        session.setAttribute("snum", snum);
        session.setAttribute("pnum", pnum);
        session.setAttribute("jnum", jnum);
        session.setAttribute("quantity", quantity);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dataentryHome.jsp");
        dispatcher.forward(request, response);
    }

    private String doSelectQuery(String textBox) throws SQLException {
        String result;

        ResultSet table = statement.executeQuery(textBox);
        ResultSetMetaData metaData = table.getMetaData();

        int numOfColumns = metaData.getColumnCount();
        String tableOpeningHTML = "<table class='w-full text-left'>";
        String tableColumnsHTML = "<thead><tr>";
        for (int i = 1; i <= numOfColumns; i++) {
            tableColumnsHTML += "<th class='text-lg'>" + metaData.getColumnName(i) + "</th>";
        }
        tableColumnsHTML += "</tr></thead>";

        String tableBodyHTML = "<tbody>";
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

    private String doUpdateQuery(String textBox, String snum, String pnum, String jnum, String quantity) throws SQLException {
        String result = null;
        int numOfRowsAffected = 0;

        statement.executeUpdate("drop table if exists beforeShipments;");
        statement.executeUpdate("create table beforeShipments like shipments");
        statement.executeUpdate("insert into beforeShipments select * from shipments");

        String dataentrystatement = "INSERT INTO project3.shipments" + " (snum, pnum, jnum, quantity) VALUES " + " ('" + snum + "', '" + pnum + "', '" + jnum + "', '" + quantity + "');";

                numOfRowsAffected = statement.executeUpdate(dataentrystatement);
                result = "<p>The statement executed succesfully.</p>";
                result += "<p><span class='font-bold'>" + numOfRowsAffected + "</span> row(s) affected</p>";
            
                if (dataentrystatement.contains("shipments")) {
                    result += "<p class='mt-2'>Business Logic Detected! - Updating Supplier Status</p>";
                    result += "<p>Business Logic updated <span class='font-bold'> </span> supplier status marks.</p>";
                }

        return result;
    }
}