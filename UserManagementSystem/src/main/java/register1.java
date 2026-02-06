import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class register1
 */
@WebServlet("/register1")
public class register1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public register1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out=response.getWriter();

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
           out.println(e);
        }
   
	}

}
