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
			conn = DriverManager.getConnection("jdbc:mariadb://mariadb.vamk.fi/e2101942_java_demo", "root", "root");
			ps = conn.prepareStatement("SELECT * FROM product");
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
   ResultSet rs = ps.executeQuery();
%>

<%@ include file="addProduct.html" %>
<html>
	<body>
		<h1> Users </h1>
		<table>
		<tr>
		<th>Name</th>
		<th>Id</th>
		<th>Price</th>
		</tr>
<% while (rs.next()) { %>
    <tr>
        <td><%= rs.getString(1) %></td>
        <td><%= rs.getInt(2) %></td>
        <td><%= rs.getFloat(3) %></td>
    </tr>
<% } %>
		</table>
	</body>
</html>