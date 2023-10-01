import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletConfig;
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
import java.sql.SQLException;

@WebServlet(urlPatterns="/AddUserServlet")
public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private Connection connection;
	PreparedStatement statement;

	@Override
	public void init(ServletConfig config) {
		ServletContext context = config.getServletContext();
		String driverClass = context.getInitParameter("driverClass");
		String db = "jdbc:mariadb://mariadb.vamk.fi/e2101942_java_demo";
		String dbUser = context.getInitParameter("db_user");
		String dbPassword = context.getInitParameter("db_password");
		try { 
			Class.forName(driverClass);
			connection = DriverManager.getConnection(db, dbUser, dbPassword);
			statement = connection.prepareStatement("INSERT INTO account (email, password) VALUES (?, ?)");
		} catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		try {			
			statement.setString(1, email);
			statement.setString(2,  password);

			int result = statement.executeUpdate();
			res.setContentType("text/html");
			PrintWriter out = res.getWriter();
			out.print("<b> " + result + " account created");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
