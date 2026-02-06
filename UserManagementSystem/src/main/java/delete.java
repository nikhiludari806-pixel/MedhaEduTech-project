import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/delete")
public class delete extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // ✅ Prevents 405 error
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        response.getWriter().println("<h3>Please use delete form</h3>");
    }

    // ✅ Actual delete logic
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("uname");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/javadb",
                    "root",
                    "root"
            );

            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM userregister WHERE name=?"
            );

            ps.setString(1, name);

            int i = ps.executeUpdate();

          
            con.close();

            if (i > 0) {
                out.println("<h3>Record Deleted Successfully</h3>");
            } else {
                out.println("<h3>No Record Found</h3>");
            }

        } catch (Exception e) {
            out.println("<h3>Error: " + e + "</h3>");
        }
    }
}
