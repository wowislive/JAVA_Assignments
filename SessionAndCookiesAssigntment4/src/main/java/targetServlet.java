import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/targetServlet")
public class targetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection connection;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {	
		HttpSession session = req.getSession(false);
		String db = "jdbc:mariadb://mariadb.vamk.fi/e2101942_java_demo";
		String dbUser = (String) session.getAttribute("user");
		String dbPassword = (String) session.getAttribute("psw");
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			connection = DriverManager.getConnection(db, dbUser, dbPassword);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res.sendRedirect("loginForm.html");
		}
		PrintWriter out = res.getWriter();
		if(connection != null) {
			out.println("Hello " + dbUser);
			
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
