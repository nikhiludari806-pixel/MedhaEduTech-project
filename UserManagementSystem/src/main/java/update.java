

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

@WebServlet("/update")
public class update extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // ✅ GET supported
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        try (PrintWriter out = response.getWriter()) {
            out.println("Update servlet working");
        }
    }

    // ✅ POST supported
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException , IOException {

        response.setContentType("text/html");
        try (PrintWriter out = response.getWriter()) {

            String name = request.getParameter("uname");
            String password = request.getParameter("pwd");
            String email = request.getParameter("mail");
            String address = request.getParameter("address");

            if (name == null || name.isEmpty()) {
                out.println("Error: Name is required.");
                return;
            }

            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/javadb",
                    "root",
                    "root"
            )) {
                String sql = "UPDATE userregister SET password=?, email=?, address=? WHERE name=?";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, password);
                    ps.setString(2, email);
                    ps.setString(3, address);
                    ps.setString(4, name);

                    int rowsUpdated = ps.executeUpdate();
                    if (rowsUpdated > 0) {
                        out.println("Update successful");
                    } else {
                        out.println("No user found with the given name.");
                    }
                }
            }

        } catch (Exception e) {
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
