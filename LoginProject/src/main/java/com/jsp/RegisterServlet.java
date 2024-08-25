package com.jsp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/CookieRegister")
public class RegisterServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher rd=req.getRequestDispatcher("RegisterFile.html");
		rd.forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		
		String name=req.getParameter("name");
		int age=Integer.parseInt(req.getParameter("age"));
		String password=req.getParameter("password");
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/class","root","root");
			PreparedStatement ps=con.prepareStatement("insert into cookie values(?,?,?)");
			ps.setString(1, name);
			ps.setInt(2, age);
			ps.setString(3, password);
			
			int i=ps.executeUpdate();
			if(i>0) {
				RequestDispatcher rd=req.getRequestDispatcher("LoginFile.html");
				rd.forward(req, resp);
			}
			else {
				out.println("theres some error please try again later");
				RequestDispatcher rd=req.getRequestDispatcher("RegisterFile.html");
				rd.include(req, resp);
			}	
			
		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
		
	}
}
