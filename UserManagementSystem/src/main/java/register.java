import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class register extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect("register.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        try {
            String name = request.getParameter("uname");
            String password = request.getParameter("psw");
            String email = request.getParameter("mail");
            String gender = request.getParameter("gender");
            String address = request.getParameter("address");
            String country = request.getParameter("country");

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/javadb",
                    "root",
                    "root"
            );

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO userregister  VALUES (?, ?, ?, ?, ?, ?)"
            );

            // ✅ SET PARAMETERS (THIS WAS MISSING)
            ps.setString(1, name);
            ps.setString(2, password);
            ps.setString(3, email);
            ps.setString(4, gender);
            ps.setString(5, address);
            ps.setString(6, country);

            int i = ps.executeUpdate();

            con.close();

            if (i > 0) {
                response.sendRedirect("success.html");
            } else {
                response.getWriter().println("Registration Failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
