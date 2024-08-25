package com.jsp;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/CookieLogout")
public class LogoutServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		
		Cookie[] c1 = req.getCookies();
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
        if (c1 != null) {
            for (Cookie cook : c1) {
                if (cook.getName().equals("name")) {
                	cook.setValue("");
                    cook.setMaxAge(0); 
                    resp.addCookie(cook);
                }
            }
        }
        out.println("You've successfully logged out.");
//        resp.sendRedirect("CookieLogin");
//        resp.sendRedirect("LoginFile.html");
        out.println("<br><br>")
;        out.println("If you want to login back");
        out.println("<a href='CookieLogin'>LogIn</a>");
//        RequestDispatcher rd=req.getRequestDispatcher("LoginFile.html");
//        rd.include(req, resp);
	}
}
