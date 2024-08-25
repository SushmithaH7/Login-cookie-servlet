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
@WebServlet("/CookieLogin")
public class LoginServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		
		String name=req.getParameter("name");
		String password=req.getParameter("password");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/class","root","root");
			PreparedStatement ps=con.prepareStatement("select * from cookie where name=? and password=?;");
			ps.setString(1, name);
			ps.setString(2, password);
			
			ResultSet set=ps.executeQuery();
			
			
			if(set.next()) {
				out.println("You've successfully logged in !!");
				Cookie c1=new Cookie("name", name);
				resp.addCookie(c1);
				out.println("<br><br>");
//				out.println("<a href='profile'>Profile</a>");
//				RequestDispatcher rd=req.getRequestDispatcher("CookieProfile");
//				rd.include(req, resp);
				resp.sendRedirect("CookieProfile");
			}
			else {
				out.println("wrong credentials ");
				RequestDispatcher rd=req.getRequestDispatcher("LoginFile.html");
				rd.include(req, resp);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}
}
