package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.dao.UserDao;
import com.entity.User;
import com.util.Connect;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

@SuppressWarnings("serial")
@WebServlet("/Login")
public class Login extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);  
	}
    public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Type","text/html;character=utf-8");
    	PrintWriter out = response.getWriter();
    	String username=request.getParameter("username").trim();
    	String password=request.getParameter("password").trim();
		User usr=new User(username,password);
		UserDao ud=new UserDao();
		Connect cn=new Connect();
		Connection con=cn.getConnection();
		Map<String,String> params = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        String str=null;
		try {
			str=ud.login_check(con, usr);
			cn.dis_connect();
			params.put("result", str);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		jsonObject.put("params", params);
		out.print(jsonObject.toString());
    }

}
