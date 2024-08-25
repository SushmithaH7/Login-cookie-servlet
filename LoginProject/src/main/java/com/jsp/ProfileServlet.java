package com.jsp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/CookieProfile")
public class ProfileServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		
		Cookie c1[]=req.getCookies();
		String Username=null;
		
		if(c1 != null) {
			for(Cookie ck:c1) {
				if(ck.getName().equals("name")) {
					Username=ck.getValue();
				}
			}
		}
		
		if (Username == null || Username.isEmpty()) {
			out.println("Please login first");
			RequestDispatcher rd=req.getRequestDispatcher("LoginFile.html");
			rd.include(req, resp);
//		    resp.sendRedirect("LoginFile.html");
		    return; 
		}
		
		if(Username != null && !Username.isEmpty()) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/class","root","root");
				PreparedStatement ps=con.prepareStatement("select * from cookie where name=?;");
				ps.setString(1, Username);
				
				ResultSet set= ps.executeQuery();
				if(set.next()) {
					out.println("Welcome "+set.getString(1));
					out.println("Your Age is "+set.getInt(2));
					out.println("<br><br>");
					out.println("<a href='CookieLogout'>Logout</a>");
//					out.println("<input type='submit' value='Logout'>");
//					resp.sendRedirect("CookieLogout");
//					RequestDispatcher rd=req.getRequestDispatcher("CookieLogout");
//					rd.include(req, resp);
				}
				else {
					out.println("Please Log in First !!");
					RequestDispatcher rd=req.getRequestDispatcher("LoginFile.html");
					rd.include(req, resp);
				}				
			} 									
			catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}						
		}								
	}
}
