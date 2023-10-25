import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/students/*")
public class UserController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private UserDAO userDAO;
	
	private Gson gson;
	
	public void init() {
		userDAO = new UserDAO();
		gson = new Gson();
	}
	
	private void sendAsJSON(HttpServletResponse response, Object obj) throws ServletException, IOException {
		response.setContentType("application/json");
		String result = gson.toJson(obj);
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
	}
	
	// Get users
	// GET/REST_Assignment6/students/
	// GET/REST_Assignment6/students/id 
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		
		res.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
		
		// Return all users
		if(pathInfo == null || pathInfo.equals("/")) {
			List<User> users = userDAO.selectAllUsers();
			sendAsJSON(res, users);
			return;
		}
		
		String splits[] = pathInfo.split("/");
		if(splits.length != 2) {
			res.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		String userID = splits[1];
		User user = userDAO.selectUserByID(userID);
		if(user == null) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		} else {
			sendAsJSON(res, user);
			return;
		}
	}
	
	// Post new user
	// POST REST_Assignment6/students
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
		
		String pathInfo = req.getPathInfo();
		System.out.println(pathInfo);
		if(pathInfo == null | pathInfo == "/") {
			StringBuilder buffer = new StringBuilder();
			BufferedReader reader = req.getReader();
			
			String line;
			while((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			String payload = buffer.toString();
			User user = gson.fromJson(payload, User.class);
			userDAO.insertUser(user);
		} else {
			res.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
	}
	
	// Delete new user
	// DELETE REST_Assignment6/students/id
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	    String pathInfo = req.getPathInfo();
	    
	    res.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
	    
	    if(pathInfo == null || pathInfo.equals("/")) {
	        res.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required");
	        return;
	    }
	    
	    String splits[] = pathInfo.split("/");
	    if(splits.length != 2) {
	        res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
	        return;
	    }
	    
	    String userID = splits[1];
	    
	    boolean isDeleted = userDAO.deleteUser(userID);
	    if(!isDeleted) {
	        res.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found or failed to delete");
	        return;
	    }
	    
	    res.setStatus(HttpServletResponse.SC_NO_CONTENT);
	}

}