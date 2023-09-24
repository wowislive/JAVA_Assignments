

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class Numbers
 */
public class Numbers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String firstNumber = request.getParameter("firstNumber");
		String secondNumber = request.getParameter("secondNumber");
		int result = Integer.parseInt(firstNumber) + Integer.parseInt(secondNumber);
		
		out.println("<html>");
		out.println("<body>");
		out.println("<h1>" + result + "</h1>");
		out.println("</body>");
		out.println("</html>");
		
	}

}
