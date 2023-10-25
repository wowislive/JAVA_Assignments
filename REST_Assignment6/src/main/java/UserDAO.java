import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
	private String jdbcURL = "jdbc:mariadb://mariadb.vamk.fi/e2101942_java_demo";
	private String jdbcUserName = "e2101942";
	private String jdbcPassword = "DTsVzGkhANe";

	// Constructors
	public UserDAO(String url, String userName, String password) {
		this.jdbcURL = url;
		this.jdbcUserName = userName;
		this.jdbcPassword = password;
	}

	public UserDAO() {
	}

	private static final String SELECT_ALL_USERS_QUERY = "SELECT * FROM student";
	private static final String SELECT_USER_BY_ID = "SELECT * FROM student WHERE id=?";
	private static final String INSERT_USER_QUERY = "INSERT INTO student (id, firstName, lastName) VALUES (?, ?, ?)";
	private static final String DELETE_USER_QUERY = "DELETE FROM student WHERE id=?";

	protected Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcURL, jdbcUserName, jdbcPassword);

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void insertUser(User user) {
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_USER_QUERY);) {
			ps.setString(1, user.getUserID());
			ps.setString(2, user.getFirstName());
			ps.setString(3, user.getLastName());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<User> selectAllUsers() {
		List<User> users = new ArrayList<User>();

		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ALL_USERS_QUERY);) {

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String id = rs.getString(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);

				users.add(new User(id, firstName, lastName));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}

	public User selectUserByID(String id) {
		User user = null;

		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_USER_BY_ID);) {

			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String userID = rs.getString("id");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				

				user = new User(userID, firstName, lastName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	public boolean deleteUser(String id) {
		boolean rowDeleted = false;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_USER_QUERY);) {
			ps.setString(1, id);
			rowDeleted = ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowDeleted;
	}
}