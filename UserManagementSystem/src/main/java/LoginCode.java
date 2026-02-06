import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/LoginCode")
public class LoginCode extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // ✅ Handles GET request (prevents 405 error)
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		try  
		{
			String name=request.getParameter("uname");
			String password=request.getParameter("password");
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/javadb","root","root");
			PreparedStatement ps=con.prepareStatement("select * from  userregister where name=? and password=?");
			 
			ps.setString(1, name);
			ps.setString(2, password);
			 
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()) {
			
				response.sendRedirect("userlogin.html");
			
			}else {
				out.print("please enter the valide username && password");
				
				
				
			}
		}catch(Exception ex) {
			
			out.print(ex);
		}
    }
}
