<%@ page
    import="java.sql.*" 
    language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<%!
   Connection conn;
   PreparedStatement ps;
   
   public void jspInit() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://mariadb.vamk.fi/e2101942_java_demo", "e2101942", "DTsVzGkhANe");
			ps = conn.prepareStatement("INSERT INTO product (Name, ID, Price) VALUES (?, ?, ?)");
		} catch(Exception e) {
			e.printStackTrace();
		}
   }

   public void jspDestroy() {
	   try {
		   ps.close();
		   conn.close();
	   } catch(Exception e) {
		   e.printStackTrace();
	   }
   }
%>

<%
   String name = request.getParameter("name");
   float price = Float.parseFloat(request.getParameter("price"));
   int id = Integer.parseInt(request.getParameter("id"));
   ps.setString(1, name);
   ps.setInt(2, id);
   ps.setFloat(3, price);
   ps.executeUpdate();
%>

<%@ include file="addProduct.html" %>
<html>
	<body>
		<h1> Updated </h1>
	</body>
</html>