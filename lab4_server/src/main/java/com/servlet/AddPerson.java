package com.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.dao.PersonDao;
import com.entity.Person;
import com.entity.User;
import com.util.Connect;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

@SuppressWarnings("serial")
@WebServlet("/AddPerson")
public class AddPerson extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);  
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Type","text/html;character=utf-8");
		String username=request.getParameter("username");
		String name=request.getParameter("name");
		String age=request.getParameter("age");
		String telenumber=request.getParameter("telenumber");
		String password=request.getParameter("password");
		User usr=new User(username,password);
		Person person=new Person(username,name,age,telenumber);
		PersonDao pd = new PersonDao();
		Connect cn = new Connect();
		Connection con = cn.getConnection();
		Map<String,String> params = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
		try {
			String str=pd.Person_add(con, person, usr);
			cn.dis_connect();
			params.put("result", str);
		} catch (SQLException e) {
			e.printStackTrace(); 
		}
		jsonObject.put("params", params);
		response.getWriter().print(jsonObject.toString());
	}
}
