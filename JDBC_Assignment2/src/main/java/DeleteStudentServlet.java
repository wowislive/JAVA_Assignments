

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class AddStudentServlet
 */

public class DeleteStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private Connection connection;
	PreparedStatement statement;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	
	@Override
	public void init() {
		try { 
			Class.forName("org.mariadb.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mariadb://mariadb.vamk.fi/e2101942_java_demo", "e2101942", "ea6VrXAFkS3");
			statement = connection.prepareStatement("DELETE FROM student WHERE number=?");
		} catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String numberStr = req.getParameter("number");
		
		try {
			int number = Integer.parseInt(numberStr);
			
			statement.setInt(1, number);

			int result = statement.executeUpdate();
			res.setContentType("text/html");
			PrintWriter out = res.getWriter();
			out.print("<b> " + result + " student deleted");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @see Servlet#destroy()
	 */
	@Override
	public void destroy() {
		try {
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
