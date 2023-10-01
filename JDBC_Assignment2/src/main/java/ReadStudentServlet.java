

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
import java.sql.ResultSet;

/**
 * Servlet implementation class UpdateStudentServlet
 */
public class ReadStudentServlet extends HttpServlet {
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
			statement = connection.prepareStatement("SELECT * FROM student");
		} catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ResultSet rs = statement.executeQuery();
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.print("<html>");
			out.print("<body>");
			out.print("<h1> Users </h1>");
			out.print("<table>");
			out.print("<tr>");
			out.print("<th> Number </th>");
			out.print("<th> First Name </th>");
			out.print("<th> Last Name </th>");
			out.print("</tr>");
			while(rs.next()) {
				out.print("<tr>");
				out.print("<td>");
				out.print(rs.getInt(1));
				out.print("</td>");
				out.print("<td>");
				out.print(rs.getString(2));
				out.print("</td>");
				out.print("<td>");
				out.print(rs.getString(3));
				out.print("</td>");
				out.print("</tr>");
			}
			out.print("</table>");
			out.print("</body>");
			out.print("</html>");
		} catch(SQLException e) {
			e.printStackTrace();
		} 
	}
	
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
